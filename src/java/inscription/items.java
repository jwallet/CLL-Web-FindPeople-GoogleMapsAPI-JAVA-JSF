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
public class items implements Serializable{
    private int idItem;
    private int idCompte;
    private String ramassage;
    private String img;
    private Date date;
    private Date dateMax;
    private String dispos;
    public items()
    {
        
    }
    public items(int idItem, int idCompte, String ramassage, String img, Date date, Date dateMax, String disp)
    {
        this.idItem = idItem;
        this.idCompte = idCompte;
        this.ramassage = ramassage;
        this.img = img;
        this.date = date;
        this.dateMax = dateMax;
        this.dispos = disp;
    }
    
    public int getIdCompte(){
        return idCompte;
    }
   
    public int getIdItem(){
        return idItem;
    }
    public String getRamassage()
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

    public void setIdItem(int idItem){
        this.idItem = idItem;
    }
    public void setRamassage(String ram)
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
