/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author wExzEk
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Director implements Comparable<Director> {

    @XmlElement(name = "id")
    private int id;
    
    @XmlElement(name = "fullname")
    private String fullName;

    public Director() {
    }

    public Director(String fullName) {
        this.fullName = fullName;
    }

    public Director(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Director director = (Director) obj;
        return id == director.id
                && Objects.equals(fullName, director.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public int compareTo(Director director) {
        return fullName.compareToIgnoreCase(director.fullName);
    }
}
