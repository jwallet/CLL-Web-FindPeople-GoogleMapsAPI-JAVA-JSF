/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscription;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class markerItems implements Serializable{
    private int idCompte;
    private String email;
    private String cpostal;
    private String lat;
    private String lon;
    private String tel;
    private String pseudo;
    private int idItem;
    private int ramassage;
    private String img;
    private Date date;
    private Date dateMax;
    private String dispos;
    public markerItems()
    {
        
    }
    public markerItems(int idCompte, String email, String cpostal, 
            String lat, String lon, String tel, String pseudo, 
            int idItem, int ramassage, String img, Date date, Date dateMax, String disp)
    {
        this.idCompte = idCompte;
        this.email = email;
        this.cpostal = cpostal;
        this.lat = lat;
        this.lon = lon;
        this.tel = tel;
        this.pseudo = pseudo;
        this.idItem = idItem;
        this.ramassage = ramassage;
        this.img = img;
        this.date = date;
        this.dateMax = dateMax;
        this.dispos = disp;
    }
    
    public int getIdCompte(){
        return idCompte;
    }
    public String getEmail(){
        return email;
    }
    public String getCpostal(){
        return cpostal;
    }
    public String getLat(){
        return lat;
    }
    public String getLon(){
        return lon;
    }
    public String getTel(){
        return tel;
    }
    public String getPseudo(){
        return pseudo;
    }
    public int getIdItem(){
        return idItem;
    }
    public int getRamassage()
    {
        return ramassage;
    }
    public String getImg(){
        return img;
    }
    public Date getDate(){
        return date;
    }
    public Date getDateMax(){
        return dateMax;
    }
    public String getDispos(){
        return dispos;
    }
    
    
    public void setId(int idCompte){
        this.idCompte = idCompte;
    }
    public void setEmail (String email){
        this.email = email;
    }
    public void setCpostal (String cpostal){
        this.cpostal = cpostal;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public void setLon(String lon){
        this.lon = lon;
    }
    public void setTel(String tel){
        this.tel = tel;
    }
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    public void setIdItem(int idItem){
        this.idItem = idItem;
    }
    public void setRamassage(int ram)
    {
        this.ramassage = ram;
    }
    public void setImg(String img){
        this.img = img;
    }
    public void setDate (Date date){
        this.date = date;
    }
    public void setDateMax(Date dateMax){
        this.dateMax = dateMax;
    }
    public void setDispos(String disp)
    {
        this.dispos = disp;
    }
    
}
