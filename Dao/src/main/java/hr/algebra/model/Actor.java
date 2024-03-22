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
public class Actor implements Comparable<Actor>{

    @XmlElement(name = "id")
    private int id;
    
    @XmlElement(name = "fullname")
    private String fullName;

    public Actor() {
    }

    public Actor(String fullName) {
        this.fullName = fullName;
    }

    public Actor(int id, String fullName) {
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
        Actor actor = (Actor) obj;
        return id == actor.id
                && Objects.equals(fullName, actor.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public int compareTo(Actor actor) {
        return fullName.compareToIgnoreCase(actor.fullName);
    }

}
