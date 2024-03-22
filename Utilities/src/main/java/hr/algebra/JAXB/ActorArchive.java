/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.JAXB;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import hr.algebra.model.Actor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author wExzEk
 */

@XmlRootElement(name = "actorarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorArchive {
    
    @XmlElementWrapper
    @XmlElement(name = "actor")
    List<Actor> actors;

    public ActorArchive() {
    
    }

    public ActorArchive(List<Actor> actors) {
        this.actors = actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

}
