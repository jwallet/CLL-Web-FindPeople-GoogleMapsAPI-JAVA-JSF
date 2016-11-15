/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscription;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class markerMembre implements Serializable{
    private int id;
    private String pseudo;
    private String lat;
    private String lon;
    
    public markerMembre(int id, String pseudo, String lat, String lon)
    {
        this.id = id;
        this.pseudo = pseudo;
        this.lat = lat;
        this.lon = lon;
    }
    
    public int getId(){
        return id;
    }
    public String getPseudo(){
        return pseudo;
    }
    public String getLat(){
        return lat;
    }
    public String getLon(){
        return lon;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public void setLon(String lon){
        this.lon = lon;
    }
}
