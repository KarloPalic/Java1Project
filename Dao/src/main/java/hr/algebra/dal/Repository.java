/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.MovieActorRelationship;
import hr.algebra.model.MovieDirectorRelationship;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author dnlbe
 */
public interface Repository {
    //Movies
    int createMovie(Movie model) throws Exception;
    void createMovies(List<Movie> models) throws Exception;
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int id) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;
    
    //Actors
    int createActor(Actor actor) throws Exception;
    void createActors(List<Actor> actors) throws Exception;
    void updateActor(int id, Actor actor) throws Exception;
    void deleteActor(int id) throws Exception;
    Optional<Actor> selectActor(int id) throws Exception;
    List<Actor> selectActors() throws Exception;
    void insertActorToMovie(int idMovie, int idActor) throws Exception;
    void insertActorsToMovie(int id, List<Actor> actors) throws Exception;
    public List<Actor> getActorsFromMovie(int id) throws Exception;
    void deleteActorFromMovie(int movieId) throws Exception;
    void updateMovieActors(int movieId, int actorId, int newActorId) throws Exception;
    List<MovieActorRelationship> selectMovieActors() throws Exception;
    List<Actor> getActorsForMovie(int movieID) throws Exception;
    Optional<MovieActorRelationship> selectMovieActorRelationship(int movieId, int actorId) throws Exception;
    
    //Directors
    int createDirector(Director director) throws Exception;
    void createDirectors(List<Director> directors) throws Exception;
    void updateDirector(int id, Director director) throws Exception;
    void deleteDirector(int id) throws Exception;
    Optional<Director> selectDirector(int id) throws Exception;
    List<Director> selectDirectors() throws Exception;
    void addDirectorToMovie(int idMovie, int idDirector) throws Exception;
    void addDirectorsToMovie(int id, List<Director> directors) throws Exception;
    public List<Director> getDirectorsFromMovie(int id) throws Exception;
    void deleteMovieDirectorRelationship(int movieId) throws Exception;
    List<Director> getDirectorsForMovie(int movieID) throws Exception;
    List<MovieDirectorRelationship> selectMovieDirectors() throws Exception;
    Optional<MovieDirectorRelationship> selectMovieDirector(int id) throws Exception;
    public List<Movie> getMoviesForDirector(int id);
    
    //Users
    int createUser(User user) throws Exception;
    void createUsers(List<User> users) throws Exception;
    Optional<User> selectUser(int id) throws Exception;
    List<User> selectUsers() throws Exception;
    void updateUser(int id, User user) throws Exception;
    void deleteUser(int id) throws Exception;
    Optional<User> userAuthentication(String username, String password) throws Exception;
            
            
            
    void deleteAllData() throws Exception;
    
    
    
}
