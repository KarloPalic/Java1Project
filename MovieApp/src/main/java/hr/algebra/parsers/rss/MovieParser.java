/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import hr.algebra.utilities.FileUtils;

/**
 *
 * @author wExzEk
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static Repository repository;
    private static List<Movie> movies = new ArrayList<>();

    static {
        repository = RepositoryFactory.getRepository();
    }

    private static List<Actor> parseActors(String data) {
        List<Actor> actors = new ArrayList<>();
        String[] crewMembers = data.split(",");
        for (String member : crewMembers) {
            actors.add(new Actor(member.trim()));
        }
        return actors;
    }

    private static List<Director> parseDirectors(String data) {
        List<Director> directors = new ArrayList<>();
        String[] crewMembers = data.split(",");
        for (String member : crewMembers) {
            directors.add(new Director(member.trim()));
        }
        return directors;
    }

    private MovieParser() {

    }

    private enum Tag {
        ITEM("item"),
        TITLE("title"),
        LINK("link"),
        DESC("description"),
        PUB_DATE("pubDate"),
        GLUMCI("glumci"),
        REDATELJ("redatelj"),
        PLAKAT("plakat"),;

        private Tag(String name) {
            this.name = name;
        }
        private final String name;

        private static Optional<Tag> from(String name) {
            for (Tag value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

    public static List<Movie> parse() throws IOException, XMLStreamException, Exception {

        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<Tag> tag = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tag = Tag.from(qName);
                        if (tag.isPresent() && tag.get().equals(Tag.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS, XMLStreamConstants.CDATA -> {
                        if (tag.isPresent() && movie != null) {
                            String data = event.asCharacters().getData().trim();
                            switch (tag.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (!data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublishedDate(publishedDate);
                                    }
                                    break;

                                case DESC:
                                    if (!data.isEmpty()) {
                                        movie.setDescription(data);
                                    }
                                    break;

                                case GLUMCI:
                                    if (!data.isEmpty()) {
                                        List<Actor> actors = parseActors(data);
                                        movie.setActors(actors);
                                    }
                                    break;

                                case LINK:
                                    if (!data.isEmpty()) {
                                        movie.setLink(data.trim());
                                    }
                                    break;
                                case PLAKAT:
                                    if (startElement != null && movie.getPicturePath() == null) {
                                        if (!data.isEmpty()) {
                                            handlePicture(movie, data);
                                        }
                                    }
                                    break;
                                case REDATELJ:
                                    if (!data.isEmpty()) {
                                        List<Director> directors = parseDirectors(data);
                                        movie.setDirectors(directors);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }

        }

        return movies;
    }

    private static void handlePicture(Movie movie, String url) throws IOException {
        String ext = url.substring(url.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String name = UUID.randomUUID() + ext;
        String localPath = DIR + File.separator + name;

        FileUtils.copyFromUrl(url, localPath);
        movie.setPicturePath(localPath);
    }
}
