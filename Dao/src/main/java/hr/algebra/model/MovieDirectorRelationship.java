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
public class MovieDirectorRelationship {
    private int movieId;
    private int directorId;

    public MovieDirectorRelationship(int movieId, int directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }
    
    
}
