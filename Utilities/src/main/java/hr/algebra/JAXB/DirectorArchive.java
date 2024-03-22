/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.JAXB;

import hr.algebra.model.Director;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wExzEk
 */
@XmlRootElement(name = "directorarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class DirectorArchive {

    @XmlElementWrapper
    @XmlElement(name = "director")
    List<Director> directors;

    public DirectorArchive() {

    }

    public DirectorArchive(List<Director> directors) {
        this.directors = directors;
    }

   

    public void getDirectors(List<Director> directors) {
        this.directors = directors;
    }
}
