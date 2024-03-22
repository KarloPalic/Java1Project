/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;

import hr.algebra.model.Movie;
import hr.algebra.model.MovieActorRelationship;
import hr.algebra.model.MovieDirectorRelationship;
import hr.algebra.model.User;
import hr.algebra.model.User.UserRole;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

/**
 *
 * @author dbele
 */
public class SqlRepository implements Repository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String LINK = "Link";
    private static final String DESCRIPTION = "Description";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String PUBLISH_DATE = "PublishDate";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
            stmt.setString(PUBLISH_DATE, movie.getPublishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString(TITLE, movie.getTitle());
                stmt.setString(LINK, movie.getLink());
                stmt.setString(DESCRIPTION, movie.getDescription());
                stmt.setString(PICTURE_PATH, movie.getPicturePath());
                stmt.setString(PUBLISH_DATE, movie.getPublishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

                stmt.executeUpdate();

            }
        }

    }

    @Override
    public void updateMovie(int id, Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
            stmt.setString(PUBLISH_DATE, movie.getPublishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            LocalDateTime.parse(rs.getString(PUBLISH_DATE), Movie.DATE_FORMATTER)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        rs.getString(LINK),
                        rs.getString(DESCRIPTION),
                        rs.getString(PICTURE_PATH),
                        LocalDateTime.parse(rs.getString(PUBLISH_DATE), Movie.DATE_FORMATTER)));
            }
        }
        return movies;
    }

    private static final String ID_ACTOR = "IDActor";
    private static final String ACTFULLNAME = "Name";
    private static final String ID_MOVIE_COLUMN = "MovieId";
    private static final String ID_ACTOR_COLUMN = "ActorId";

    private static final String CREATE_ACTOR = "{ CALL createActor (?,?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";

    @Override
    public int createActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {

            stmt.setString(ACTFULLNAME, actor.getFullName());
            stmt.registerOutParameter(ID_ACTOR, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_ACTOR);
        }
    }

    @Override
    public void createActors(List<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {

            for (Actor actor : actors) {
                stmt.setString(ACTFULLNAME, actor.getFullName());
                stmt.registerOutParameter(ID_ACTOR, Types.INTEGER);

                stmt.executeUpdate();

            }
        }
    }

    @Override
    public void updateActor(int id, Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setString(ACTFULLNAME, actor.getFullName());
            stmt.setInt(ID_ACTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            stmt.setInt(ID_ACTOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {
            stmt.setInt(ID_ACTOR, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTFULLNAME)));
                }
            }
        }
        return null;
    }

    @Override
    public List<Actor> selectActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACTORS); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(ACTFULLNAME)));
            }
        }
        return actors;
    }

    private static final String CREATE_MOVIEACTOR = "{ CALL insertIntoMovieActors (?, ?) }";
    private static final String SELECT_MOVIEACTOR = "{ CALL selectActorsFromMovie (?) }";
    private static final String DELETE_MOVIEACTOR = "{ CALL deleteMovieActor (?) }";
    private static final String UPDATE_MOVIEACTORS = "{ CALL updateMovieActors (?, ?, ?) }";

    @Override
    public void insertActorToMovie(int idMovie, int idActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIEACTOR)) {

            stmt.setInt(ID_MOVIE, idMovie);
            stmt.setInt(ID_ACTOR, idActor);

            stmt.executeUpdate();

        }
    }

    @Override
    public void insertActorsToMovie(int id, List<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIEACTOR)) {

            for (Actor actor : actors) {
                stmt.setInt(ID_MOVIE, id);
                stmt.setInt(ID_ACTOR, actor.getId());

                stmt.executeUpdate();

            }
        }
    }

    @Override
    public List<Actor> getActorsFromMovie(int id) throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIEACTOR); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Optional<Actor> actor = selectActor(rs.getInt(ID_ACTOR));
                if (actor.isPresent()) {
                    actors.add(actor.get());
                }
            }
        }
        return actors;
    }

    @Override
    public void deleteActorFromMovie(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIEACTOR)) {
            stmt.setInt(ID_MOVIE, movieId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateMovieActors(int idMovie, int idOldActor, int idNewActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIEACTORS)) {
            stmt.setInt(ID_MOVIE, idMovie);
            stmt.setInt(ID_ACTOR, idOldActor);
            stmt.setInt(3, idNewActor);

            stmt.executeUpdate();
        }
    }

    private static final String RETRIEVE_ACTORS = "{ CALL selectActorsForMovie (?) }";
    private static final String SELECT_MOVIE_ACTOR_RELATIONSHIP = "{ CALL selectMovieActorRelationship ( ? , ?) }";
    private static final String SELECT_MOVIE_ACTORS = "{ CALL selectMovieActors }";

    @Override
    public List<MovieActorRelationship> selectMovieActors() throws Exception {
        List<MovieActorRelationship> movieActor = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_ACTORS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movieActor.add(new MovieActorRelationship(
                        rs.getInt(ID_MOVIE_COLUMN),
                        rs.getInt(ID_ACTOR_COLUMN)
                ));
            }
        }
        return movieActor;
    }
    
    public Optional<MovieActorRelationship> selectMovieActorRelationship(int movieId, int actorId) throws Exception {
    DataSource dataSource = DataSourceSingleton.getInstance();
    try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_ACTOR_RELATIONSHIP)) {
        stmt.setInt(1, movieId);
        stmt.setInt(2, actorId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new MovieActorRelationship(movieId, actorId));
            }
        }
    }
    
    return Optional.empty();
}

    @Override
    public List<Actor> getActorsForMovie(int movieID) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        List<Actor> actors = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(RETRIEVE_ACTORS)) {

            stmt.setInt(1, movieID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actor actor = new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTFULLNAME)
                    );
                    actors.add(actor);
                }
            }
        }

        return actors;
    }

    private static final String ID_DIRECTOR = "IDDirector";
    private static final String DIRFULLNAME = "Fullname";

    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";

    @Override
    public int createDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {

            stmt.setString(DIRFULLNAME, director.getFullName());
            stmt.registerOutParameter(ID_DIRECTOR, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_DIRECTOR);
        }
    }

    @Override
    public void createDirectors(List<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {

            for (Director director : directors) {
                stmt.setString(DIRFULLNAME, director.getFullName());
                stmt.registerOutParameter(ID_DIRECTOR, Types.INTEGER);

                stmt.executeUpdate();

            }
        }
    }

    @Override
    public void updateDirector(int id, Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setString(DIRFULLNAME, director.getFullName());
            stmt.setInt(ID_DIRECTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            stmt.setInt(ID_DIRECTOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {
            stmt.setInt(ID_DIRECTOR, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRFULLNAME)));
                }
            }
        }
        return null;
    }

    @Override
    public List<Director> selectDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(DIRFULLNAME)));
            }
        }
        return directors;
    }

    private static final String CREATE_MOVIEDIRECTOR = "{ CALL insertIntoMovieDirector (?,?) }";
    private static final String SELECT_MOVIEDIRECTOR = "{ CALL selectDirectorsFromMovie (?,?) }";
    private static final String DELETE_MOVIEDIRECTOR = "{ CALL deleteMovieDirector (?) }";

    @Override
    public void addDirectorToMovie(int idMovie, int idDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIEDIRECTOR)) {

            stmt.setInt(ID_MOVIE, idMovie);
            stmt.setInt(ID_DIRECTOR, idDirector);

            stmt.executeUpdate();

        }
    }

    @Override
    public void addDirectorsToMovie(int id, List<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIEDIRECTOR)) {

            for (Director director : directors) {
                stmt.setInt(ID_MOVIE, id);
                stmt.setInt(ID_DIRECTOR, director.getId());

                stmt.executeUpdate();

            }
        }
    }

    @Override
    public List<Director> getDirectorsFromMovie(int id) throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIEDIRECTOR); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Optional<Director> director = selectDirector(rs.getInt(ID_DIRECTOR));
                if (director.isPresent()) {
                    directors.add(director.get());
                }
            }
        }
        return directors;
    }

    @Override
    public void deleteMovieDirectorRelationship(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIEDIRECTOR)) {
            stmt.setInt(ID_MOVIE, movieId);
            stmt.executeUpdate();
        }
    }

   

    private static final String SELECT_MOVIE_DIRECTORS = "{ CALL selectMovieDirectors }";
    private static final String RETRIEVE_DIRECTORS = "{ CALL selectDirectorsForMovie (?) }";
    private static final String GET_MOVIES_FOR_DIRECTOR = "{ CALL getMoviesForDirector (?) }";

    @Override
    public List<Director> getDirectorsForMovie(int movieID) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        List<Director> directors = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(RETRIEVE_DIRECTORS)) {

            stmt.setInt(1, movieID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Director director = new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRFULLNAME)
                    );
                    directors.add(director);
                }
            }
        }

        return directors;
    }

    @Override
    public List<MovieDirectorRelationship> selectMovieDirectors() throws Exception {
        List<MovieDirectorRelationship> movieDirector = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_DIRECTORS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movieDirector.add(new MovieDirectorRelationship(
                        rs.getInt(ID_MOVIE),
                        rs.getInt(ID_DIRECTOR)
                ));
            }
        }
        return movieDirector;
    }

    @Override
    public Optional<MovieDirectorRelationship> selectMovieDirector(int id) throws Exception {
        Optional<MovieDirectorRelationship> movieDirector = selectMovieDirectors()
                .stream()
                .filter(md -> md.getMovieId() == id)
                .findFirst();

        return movieDirector;
    }

    @Override
    public List<Movie> getMoviesForDirector(int id) {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(GET_MOVIES_FOR_DIRECTOR)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            LocalDateTime.parse(rs.getString(PUBLISH_DATE), Movie.DATE_FORMATTER)
                    ));
                }
            }
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        }

        return movies;
    }

    //DELETING ALL DATA
    private static final String DELETE_ALL_DATA = "{ CALL deleteAllData }";

    @Override
    public void deleteAllData() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ALL_DATA);) {

            stmt.execute();
        }
    }

    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String USERROLE = "UserRole";

    private static final String CREATE_USER = "{ CALL createxUser (?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL updatexUser (?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL deletexUser (?) }";
    private static final String SELECT_USER = "{ CALL selectxUser (?) }";
    private static final String SELECT_USERS = "{ CALL selectxUsers }";

    @Override
    public int createUser(User user) throws Exception {
        if (userExists(user.getUsername())) {
            throw new Exception("Username already exists, please use the login page");
        }
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setString(USERROLE, user.getRole().toString());

            stmt.registerOutParameter(ID_USER, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public void createUsers(List<User> users) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            for (User user : users) {
                stmt.setString(USERNAME, user.getUsername());
                stmt.setString(PASSWORD, user.getPassword());
                stmt.setString(USERROLE, user.getRole().toString());

                stmt.registerOutParameter(ID_USER, Types.INTEGER);

                stmt.executeUpdate();

            }
        }
    }

    @Override
    public Optional<User> selectUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER)) {
            stmt.setInt(ID_USER, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD),
                            UserRole.valueOf(rs.getString(USERROLE))
                    )
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<User> selectUsers() throws Exception {
        List<User> users = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USERS); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt(ID_USER),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD),
                        UserRole.valueOf(rs.getString(USERROLE))
                )
                );

            }
        }
        return users;
    }

    @Override
    public void updateUser(int id, User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_USER)) {
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setString(USERROLE, user.getRole().toString());
            stmt.setInt(ID_USER, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_USER)) {
            stmt.setInt(ID_USER, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<User> userAuthentication(String username, String password) throws Exception {
        List<User> users = selectUsers();

        Optional<User> matchingUser = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();

        return matchingUser;
    }

    private boolean userExists(String username) throws Exception {
        List<User> users = selectUsers();
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

}
