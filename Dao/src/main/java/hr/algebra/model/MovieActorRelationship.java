/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;

/**
 *
 * @author wExzEk
 */
public class MovieActorRelationship {

    private int movieId;
    private int actorId;

    public MovieActorRelationship(int movieId, int actorId) {
        this.movieId = movieId;
    }
    
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getActorId() {
        return actorId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    
    
}
