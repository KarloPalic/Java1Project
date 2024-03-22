/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.Objects;

/**
 *
 * @author wExzEk
 */
public class User {

    public static enum UserRole {
        Administrator, User
    }

    private int id;
    private String username;
    private String password;
    private UserRole userRole;

    public User(String username, String password) {
        this.userRole = UserRole.User;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public User(int id, String username, String password, UserRole userRole) {
        this(username, password, userRole);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return userRole;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return id + "/" + userRole.toString() + "/" + username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User otherUser = (User) obj;
        return id == otherUser.id
                && Objects.equals(username, otherUser.username)
                && Objects.equals(password, otherUser.password)
                && userRole == otherUser.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, userRole);
    }

}
