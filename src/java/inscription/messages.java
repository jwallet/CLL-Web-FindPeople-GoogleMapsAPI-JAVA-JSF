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
public class messages implements Serializable{
    private int idMessage;
    private int idComptePar;
    private int idComptePour;
    private String contenu;
    private boolean isread;
    private Date date;
    private String emailPar;
    private String imgURL;
    public messages()
    {
        
    }
    public messages(int idMessage, int idComptePar, int idComptePour, String contenu, boolean isread, Date date, String emailPar, String imgURL)
    {
        this.idMessage = idMessage;
        this.idComptePar = idComptePar;
        this.idComptePour = idComptePour;
        this.contenu = contenu;
        this.isread = isread;
        this.date = date;
        this.emailPar = emailPar;
        this.imgURL = imgURL;
    }
    public int getIdMessage(){
        return idMessage;
    }
    public int getIdComptePar(){
        return idComptePar;
    }
    public int getIdComptePour(){
        return idComptePour;
    }
    public String getContenu(){
        return contenu;
    }
    public boolean getIsRead(){
        return isread;
    }
    public Date getDate(){
        return date;
    }
    public String getEmailPar(){
        return emailPar;
    }
    public String getImgURL(){
        return imgURL;
    }
    
    public void setIdMessage(int e){
        this.idMessage = e;
    }
    public void setIdComptePar(int e){
        this.idComptePar = e;
    }
    public void setIdComptePour(int e){
        this.idComptePour = e;
    }
    public void setContenu(String e){
        this.contenu = e;
    }
    public void setIsRead(boolean e){
        this.isread = e;
    }
    public void setDate(Date e){
        this.date =e;
    }
    public void setEmailPar(String e){
        this.emailPar = e;
    }
    public void setImgURL(String e){
        this.imgURL = e;
    }
}
