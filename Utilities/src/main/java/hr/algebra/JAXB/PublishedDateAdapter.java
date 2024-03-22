/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.JAXB;

import hr.algebra.model.Movie;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author wExzEk
 */
public class PublishedDateAdapter extends XmlAdapter<String, LocalDateTime>{

    @Override
    public LocalDateTime unmarshal(String vt) throws Exception {
        return LocalDateTime.parse(vt, Movie.DATE_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime bt) throws Exception {
        return bt.format(Movie.DATE_FORMATTER);
    }
    
}
