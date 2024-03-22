/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import hr.algebra.JAXB.PublishedDateAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author wExzEk
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "title", "link", "description", "picturePath", "publishedDate", "actors", "directors"})
public class Movie {

    private int id;
    private String title;
    private String link;
    private String description;
    private String picturePath;
    
    @XmlJavaTypeAdapter(PublishedDateAdapter.class)
    private LocalDateTime publishedDate;

    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Actor> actors;
    
    @XmlElementWrapper
    @XmlElement(name = "director")
    private List<Director> directors;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Movie() {
    }

    public Movie(String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.picturePath = picturePath;
        this.publishedDate = publishedDate;
    }

    public Movie(int id, String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this(title , link, description, picturePath, publishedDate);
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;

    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "Movie- " + id + ":" + title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Movie movie = (Movie) obj;
        return id == movie.id
                && Objects.equals(title, movie.title)
                && Objects.equals(link, movie.link)
                && Objects.equals(description, movie.description)
                && Objects.equals(picturePath, movie.picturePath)
                && Objects.equals(publishedDate, movie.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link, description, picturePath, publishedDate);
    }

}
