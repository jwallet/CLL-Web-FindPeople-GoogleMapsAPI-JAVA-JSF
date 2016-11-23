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
    private String email;
    
    public markerMembre(int id, String pseudo, String email, String lat, String lon)
    {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.lat = lat;
        this.lon = lon;
    }
    
    public int getId(){
        return id;
    }
    public String getPseudo(){
        return pseudo;
    }
    public String getEmail(){
        return email;
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
    public void setEmail (String email){
        this.email = email;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public void setLon(String lon){
        this.lon = lon;
    }
}
