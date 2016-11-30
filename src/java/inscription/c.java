/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscription;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.net.URLConnection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.*;

@ManagedBean
@SessionScoped
public class c implements Serializable{
    
    private String userEmail = "";
    private String userCodePostal = "";
    private String userPasse = "";
    
    private String userNewEmail = "";
    private String userNewCodePostal = "";
    private String userNewPasse1 = "";
    private String userNewPasse2 = "";
    
    private String userTelephone = "";
    private String userPseudo = "";
    
    private String itemImgURL = "";
    private String itemDateMax = "";
    private String itemRamassage = "";
    private String itemDispos = "";
            
    private String userZoom = "12";
    private String userGPSlat = "46.8129233";
    private String userGPSlon = "-71.2044023";
    private String visitorCodePostal = "";
    
//    private ArrayList mapMembreId;
//    private ArrayList mapMembrePseudo;
//    private ArrayList mapMembreGPSlat;
//    private ArrayList mapMembreGPSlon;
    
    private List<markerItems> mapItems;
    private List<items> items;
    
    private int dbIdCompte = 0;
    private String dbEmail = "";
    private String dbCodePostal = "";
    private String dbPasse = "";
    private String dbGPSlat = "";
    private String dbGPSlon = "";
    private String dbTelephone = "";
    private String dbPseudo = "";
    
    private boolean connecter_succes = false;

    public c() throws ClassNotFoundException, SQLException 
    {
        setMarkers();
    }
    
    public Connection getConnexion()throws ClassNotFoundException, SQLException
    {      
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/dbunited";
            String user = "root";
            String password = "";        
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection completed.");
        } catch (SQLException ex) {
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        finally{
        }        
        return con;        
    }
    
    public String setDeconnexion()
    {
        userEmail = "";
        userCodePostal = "";
        userPasse = "";
        userZoom = "12";
        visitorCodePostal = "";
        userGPSlon = "-71.2044023";
        userGPSlat = "46.8129233";
        userTelephone = "";
        connecter_succes=false;
        return "index.xhtml";
    }

    public String getItemImgURL()
    {
        return itemImgURL;
    }
    public String getItemDateMax()
    {
        return itemDateMax;
    }
    public String getItemRamassage()
    {
        return itemRamassage;
    }
    public String getItemDispos()
    {
        return itemDispos;
    }
    public void setItemImgURL(String e)
    {
        this.itemImgURL = e;
    }
    public void setItemDateMax(String e)
    {
        this.itemDateMax = e;
    }
    public void setItemRamassage(String e)
    {
        this.itemRamassage = e;
    }
    public void setItemDispos(String e)
    {
        this.itemDispos = e;
    }
    public String getDbEmail()
    {
        return dbEmail;
    }
    
    public String getDbPasse()
    {
        return dbPasse;
    }
    public boolean getConnecterStatus()
    {
        return connecter_succes;
    }
    
    public String getOublier()
    {
        String page = "passeoublie.xhtml";
        return page;
    }
    
    public String getTelephone()
    {
        return userTelephone;
    }
    
        public void setTelephone(String e)
    {
        this.userTelephone = e;
    }    
    
    public String getEmail()
    {
        return userEmail;
    }
    
    public void setEmail(String e)
    {
        this.userEmail = e;
    }
    
    public String getPasse()
    {
        return userPasse;
    }
    
    public void setPasse(String p1)
    {
        this.userPasse = p1;
    }

    public String getCodePostal(){
        return userCodePostal;
    }
    
    public void setCodePostal(String e){
        this.userCodePostal = e;
    }
    
    public String getTempCodePostal(){
        return visitorCodePostal;
    }
    
    public void setTempCodePostal(String e){
        this.visitorCodePostal = e;
    }
    
    public String getZoom(){
        return userZoom;
    }
    public String getGPSlat(){
        return userGPSlat;
    }
    
    public String getGPSlon(){
        return userGPSlon;
    }
    
    public List<markerItems> getMapItems(){
        return mapItems;
    }
    
    public List<items> getItems(){
        return items;
    }
    
    public String getNewEmail()
    {
        return userNewEmail;
    }
    
    public void setNewEmail(String e)
    {
        this.userNewEmail = e;
    }
    
    public String getNewCPostal()
    {
        if(!visitorCodePostal.isEmpty())
            userNewCodePostal = visitorCodePostal;
        return userNewCodePostal;
    }
    
    public void setNewCPostal(String g)
    {
        this.userNewCodePostal = g;
    }
    
    public String getNewPasse1()
    {
        return userNewPasse1;
    }
    
    public void setNewPasse1(String p1)
    {
        this.userNewPasse1 = p1;
    }
    
    public String getNewPasse2()
    {
        return userNewPasse2;
    }
    
    public void setNewPasse2(String p2)
    {        
        this.userNewPasse2 = p2;
    }
    
    public String register() throws ClassNotFoundException, SQLException, JSONException, MalformedURLException, IOException
    {        
        boolean passeMatches = userNewPasse1.equals(userNewPasse2);
        boolean codePostalValide = userNewCodePostal.matches("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z][0-9][A-Z][0-9]$");

        GetLocalisation(userNewCodePostal);
        
        if(passeMatches) //&& codePostalValide)
        {
            try
            {
                PreparedStatement prepareStatementSQLFormat;
                Connection connectionToBD = this.getConnexion();

                String statementSQL = "INSERT INTO compte(email,cpostal,passe,gpslat,gpslon) VALUES(?,?,?,?,?)"; //insert
            
                prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
                prepareStatementSQLFormat.setString(1, userNewEmail);
                prepareStatementSQLFormat.setString(2, userNewCodePostal);           
                prepareStatementSQLFormat.setString(3, userNewPasse1);
                prepareStatementSQLFormat.setString(4, userGPSlat);
                prepareStatementSQLFormat.setString(5, userGPSlon);
                prepareStatementSQLFormat.executeUpdate(); 
                
                connectionToBD.close();
                
            }
            catch (SQLException ex){
                System.out.println("in exec");
                System.out.println(ex.getMessage());
            }
            
            userEmail = userNewEmail;
            userPasse = userNewPasse1;
            userCodePostal = userNewCodePostal;
            userNewEmail = "";
            userNewPasse1= "";
            userNewPasse2 = "";
            userNewCodePostal = "";
            
            connect();          
        }   
        return "index";
    }     
    
    public String connect() throws ClassNotFoundException, SQLException
    {
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();
        
        String stm = "select * from compte where compte.email=? and compte.passe=?";
      
        try
        {
            pst = con.prepareStatement(stm);
            pst.setString(1, userEmail);
            pst.setString(2, userPasse);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next()){
                dbIdCompte = rs.getInt(1);//idcompte                
                dbEmail = rs.getString(2);//email
                dbCodePostal = rs.getString(3);//cpostal
                dbPasse = rs.getString(4);
                dbGPSlat = rs.getString(5);//gsplat
                dbGPSlon = rs.getString(6);//gpslon
                dbTelephone = rs.getString(7);//telephone
                dbPseudo = rs.getString(8);//pseudo
            }
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        con.close();
        connecter_succes = dbEmail.equals(userEmail) && dbPasse.equals(userPasse);
        if(connecter_succes){
            setItemsFromAccount();
            userTelephone = dbTelephone;
            userCodePostal = dbCodePostal;
            userPasse = dbPasse;
            userPseudo = dbPseudo;
            //itemIdItem = dbIdItem;
            
            if(dbGPSlon.isEmpty() || dbGPSlat.isEmpty()){
                updateDbLocation_ActuallyEmpty();
            }
            else{
                userGPSlat = dbGPSlat;
                userGPSlon = dbGPSlon;  
            }
        }       
        userZoom = "15";
        con.close();
        return "index";
        
//        this.mapMembre = new ArrayList<markerMembre>();
//         this.mapMembre.add( new markerMembre(
//                    1, "pseudo", "11.1111","22.2222"));
//         this.mapMembre.add( new markerMembre(
//                    2, "pseudo2", "21.1111","32.2222"));
    }       
    
    public void setItemsFromAccount() throws ClassNotFoundException, SQLException{
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();
        String stm = "select * from item where idCompte=?";
        this.items = new ArrayList<>();
            try
            {
                pst = con.prepareStatement(stm);
                pst.setInt(1, dbIdCompte);
                pst.executeQuery();
                rs = pst.getResultSet();
                while(rs.next()){
                    this.items.add( new items(
                        rs.getInt(1),//iditem
                        dbIdCompte,
                        rs.getString(3),//ramassageType
                        rs.getString(4),//imgURL
                        new Date(rs.getDate(5).getDate()),//date
                        new Date(rs.getDate(6).getDate()),//dateMax
                        rs.getString(7)//dispos
                    ));
                }
            }
            catch (SQLException ex){
                System.out.println("in exec");
                System.out.println(ex.getMessage());
            }
    }
    
    public void updateDbLocation_ActuallyEmpty() throws ClassNotFoundException{
        try {
                    LocateGeoMe();
                } catch (JSONException | IOException ex) {
                    Logger.getLogger(c.class.getName()).log(Level.SEVERE, null, ex);
                }
                try
                {
                    PreparedStatement prepareStatementSQLFormat;
                    Connection connectionToBD = this.getConnexion();

                    String statementSQL = "UPDATE compte SET gpslat=?, gpslon=? WHERE email=? AND passe=?;"; 

                    prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
                    prepareStatementSQLFormat.setString(1, userGPSlat);
                    prepareStatementSQLFormat.setString(2, userGPSlon);
                    prepareStatementSQLFormat.setString(3, userEmail);
                    prepareStatementSQLFormat.setString(4, userPasse);
                    prepareStatementSQLFormat.executeUpdate(); 
                    
                    connectionToBD.close();

                }
                catch (SQLException ex){
                    System.out.println("in exec");
                    System.out.println(ex.getMessage());
                }
    }
    
    private JSONObject GoogleApiToJson(String CodePostal) throws JSONException, MalformedURLException, IOException
    {       
        String sUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="+ CodePostal + "&sensor=false";
        URL url = new URL(sUrl);
        URLConnection webConnectionToGoogleAPIgeocode = url.openConnection();
        BufferedReader inBuffer;
        inBuffer = new BufferedReader(new InputStreamReader(webConnectionToGoogleAPIgeocode.getInputStream()));
        String inputLine;           
        String jsonResult="";
        while ((inputLine = inBuffer.readLine()) != null){
            jsonResult += inputLine;                
        }
        inBuffer.close();
        JSONObject o = new JSONObject(jsonResult);      
        return o;
    }
    
    private boolean valideJSON(JSONObject o) throws JSONException{
        String status="";
        if(o.has("status")) status = o.getString("status");
        return status.equals("OK");
    }
    
    public void LocateGeoMe()throws JSONException, MalformedURLException, IOException
    {
        String CodePostal = userCodePostal;
        if (userCodePostal.isEmpty())
        {
            CodePostal = visitorCodePostal;
        }
        GetLocalisation(CodePostal);
    }
    
    private void GetLocalisation(String CodePostal) throws JSONException, MalformedURLException, IOException
    {
        JSONObject obj = GoogleApiToJson(CodePostal);
        boolean r = valideJSON(obj);
        while(!r && CodePostal.length()>=3)
        {
            CodePostal = CodePostal.substring(0, CodePostal.length()-3);
            obj = GoogleApiToJson(CodePostal);
            r = valideJSON(obj);  
        }
        if(CodePostal.length()==6)
            userZoom = "15";
        else
            userZoom = "12";
        userGPSlat = Double.toString(obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
        userGPSlon = Double.toString(obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
    }
    
    public String Publier() throws ClassNotFoundException, ParseException
    {
        try
            {
                PreparedStatement prepareStatementSQLFormat;
                Date dNow = new Date();
                String strFormatDateMax = itemDateMax.replaceAll(" ,", "");
                SimpleDateFormat SdFormatMax = new SimpleDateFormat("ddMMMMyyyy");
                Date dateFormatDateMax = SdFormatMax.parse(strFormatDateMax);
                Connection connectionToBD = this.getConnexion();

                String statementSQL = "INSERT INTO item(idCompte,ramassage,img,date,datemax) VALUES(?,?,?,?,?)"; //insert
            
                prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
                prepareStatementSQLFormat.setInt(1, dbIdCompte);
                prepareStatementSQLFormat.setString(2, itemRamassage);           
                prepareStatementSQLFormat.setString(3, itemImgURL);
                prepareStatementSQLFormat.setDate(4, new java.sql.Date(dNow.getYear(),dNow.getMonth(),dNow.getDay()));
                prepareStatementSQLFormat.setDate(5, new java.sql.Date(dateFormatDateMax.getTime()));
                prepareStatementSQLFormat.setString(6, itemDispos);
                prepareStatementSQLFormat.executeUpdate(); 
                
                connectionToBD.close();
                
            }
            catch (SQLException ex){
                System.out.println("in exec");
                System.out.println(ex.getMessage());
            }
        return "index.xhtml";
    }
    
    public void setMarkers() throws ClassNotFoundException, SQLException{
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();
        
        String stm = "select * from compte join item on compte.idCompte=item.idCompte";
        this.mapItems = new ArrayList<>();
        try
        {
            pst = con.prepareStatement(stm);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next()){
                this.mapItems.add( new markerItems(
                    rs.getInt(1),//idcompte                
                    rs.getString(2),//email
                    rs.getString(3),//cpostal
                    rs.getString(5),//gsplat
                    rs.getString(6),//gpslon
                    rs.getString(7),//telephone
                    rs.getString(8),//pseudo
                    rs.getInt(9),//iditem
                    rs.getInt(11),//ramassageType
                    rs.getString(12),//imgURL
                    rs.getDate(13),//date
                    rs.getDate(14),//dateMax
                    rs.getString(15)//dispos
                ));                
            }
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        con.close();
    }
//      public void setMap(Map map) throws ClassNotFoundException, SQLException{
//        //to set markers on map
//        //http://biemond.blogspot.ca/2008/09/google-maps-for-jsf-gmaps4jsf-in.html
//        this.map = map;  
//        map.setLatitude(userGPSlat);  
//        map.setLongitude(userGPSlon); 
//        
//        Marker mark = new Marker();
//        EventListener event = new EventListener();
//                
////        mark.setLatitude(userGPSlat);
////        mark.setLongitude(userGPSlon);
////        mark.setJsVariable("centerMarkjs");
////        mark.setId("centerMark");
////        event.setEventName("clickCenterMark");  
////        event.setJsFunction("clickHandlerCenterMarkjs");  
////        mark.getChildren().add(event);  
////        map.getChildren().add(mark);  
//        
//        ResultSet rs;
//        PreparedStatement pst;
//        Connection con = this.getConnexion();
//        
//        String stm = "select idCompte, gpslat, gpslon, pseudo from compte";
//      
//        try
//        {
//            pst = con.prepareStatement(stm);
//            pst.executeQuery();
//            rs = pst.getResultSet();
//            while(rs.next()){
//                dbIdCompte = rs.getInt(1);
//                dbGPSlat = rs.getString(5);
//                dbGPSlon = rs.getString(6);
//                dbPseudo = rs.getString(15);
//                
//                mapMembreId.add(dbIdCompte);
//                mapMembrePseudo.add(dbPseudo);
//                
//                //note: ici je peux calculer si on ajoute un mark pour le membre 
//                //si la distance gpsLON - usergpsLON est a l'interieur de 5 km.
//                //mark member on map
//                mark = new Marker();  
//                mark.setLatitude(dbGPSlat);  
//                mark.setLongitude(dbGPSlon);  
//                mark.setJsVariable("memberMark"+dbIdCompte+"js");  
//                mark.setId("memberMark"+dbIdCompte); 
//                event = new EventListener();  
//                event.setEventName("clickMemberMark"+dbIdCompte);  
//                event.setJsFunction("clickHandlerMemberMark"+dbIdCompte+"js"); 
//                mark.getChildren().add(event);  
//                map.getChildren().add(mark);  
//            }
//        }
//        catch (SQLException ex){
//            System.out.println("in exec");
//            System.out.println(ex.getMessage());
//        }
//    }
    
       public String setHtmlViewItem(int id)
    {
        String html;
        Date d = new Date();
        markerItems item = new markerItems();
        for(markerItems i : mapItems)
        {
            if(i.getIdItem()==id)
                item = i;
        } 
        int expiration = (int)((item.getDate().getDate() - d.getDate()));
        html = "<div class=\"card\">" +
"    <div class=\"card-image waves-effect waves-block waves-light\">" +
"      <img class=\"activator\" width=\"240px\" height=\"210px\" src=\"";
        html += item.getImg();
        html+="\"></div>" +
"    <div class=\"card-action\">" +
"            <a class=\"activator left\" href=\"#\">Informations sur l'annonce</a>" +
"            </div>" +
"    <div class=\"card-reveal\">" +
"      <span class=\"card-title grey-text text-darken-4\">Informations sur l'annonce</span>" +
"    <table class=\"table-info\">";

        html+="<tr><td><b>Date de parution:</b></td><td>";
        html+= item.getDate().toString();
        html+="</td></tr>";
        if(item.getRamassage()==1||item.getRamassage()==2)
        {
            html+="<tr><td><b>Ramassage:</b></td><td>";
            if(item.getRamassage()==1)
            {
                html+= "En personne";
            }
            else
            {
                html+= "Au bord de la rue";
            }
            html+="</td></tr>";
        }        
        html+="<tr><td><b>Code postal:</b></td><td>";
        html+= item.getCpostal();
       html+="</td></tr>";
       if(item.getPseudo()!=null)
       {
            html+="<tr><td><b>Contact:</b></td><td>";
           html+= item.getPseudo();
           html+="</td></tr>";
       }
       if(item.getEmail()!=null)
       {
            html+="<tr><td><b>Email:</b></td><td>";
             html+= item.getEmail();
            html+="</td></tr>";
       }
       if(item.getTel()!=null)
       {
            html+="<tr><td><b>Téléphone:</b></td><td>";
            long tel= Long.parseLong(item.getTel());
           html+= String.valueOf(tel).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
           html+="</td></tr>";
       }
       html+="<tr><td><b>Expire dans:</b></td><td>";
        html+= expiration+" jour(s) ("+item.getDateMax().toString()+")";
        html+="</td></tr>";
        
//        html+="<tr><td><a href=\"message.xhtml?id=";
//       html+= item.getIdCompte();
//       html+="\">Envoyez-y un message privé</a></td></tr>";

        html+="<tr><td><a href=\"mailto:";
       html+= item.getEmail();
       html+="\">Envoyez-y un courriel</a></td></tr>";

       
        html+="<tr><td><a target=\"_blank\" href=\"";
       html+= "https://maps.google.com/maps/place/"+item.getCpostal().substring(0, 3)+"+"+item.getCpostal().substring(3,6);
       html+="\">Ouvrir dans Google Maps</a></td></tr>";
       
        html+="</table></div>";
        return html;

    }
}