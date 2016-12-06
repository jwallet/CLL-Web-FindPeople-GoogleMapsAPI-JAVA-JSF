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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

@ManagedBean
@SessionScoped
public class c implements Serializable{
    private int itemIdToDelete = 0;
    private int itemIdToEdit = 0;
    
    private Part image;
    private String tryUserPasse = "";
    private boolean tryUserPasseSuccess = false;
    
    private String userEmail = "";
    private String userCodePostal = "";
    private String userPasse = "";
    
    private int msgIdItem = 0;
    private int msgIdComptePour = 0;
    private String msgContenu = "";
    
    private String userNewEmail = "";
    private String userNewCodePostal = "";
    private String userNewPasse1 = "";
    private String userNewPasse2 = "";
    
    private String userTelephone = "";
    private boolean userIsAdmin = false;
    private int userNewMessagesCount = 0;
    
    private String itemImgURL = "";
    private String itemDateMax = "";
    private int itemRamassage = 1;
    private String itemDispos = "";
    
    private String dateNow = "";
    private Date dateNowDate = new Date();
            
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
    private List<messages> messages;
    
    private int dbIdCompte = 0;
    private String dbEmail = "";
    private String dbCodePostal = "";
    private String dbPasse = "";
    private String dbGPSlat = "";
    private String dbGPSlon = "";
    private String dbTelephone = "";
    private boolean dbIsAdmin = false;
    
    private boolean connecter_succes = false;

    public c() throws ClassNotFoundException, SQLException 
    {
        SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
        Date dN = new Date();
        itemDateMax = sdFormat.format(dN);
        dateNow = sdFormat.format(dN);
        setMarkers();
        runDeleteOldItems();
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
        itemIdToDelete = 0;
        itemIdToEdit = 0;

         tryUserPasse = "";
         tryUserPasseSuccess = false;

         userEmail = "";
         userCodePostal = "";
         userPasse = "";

         msgIdItem = 0;
         msgIdComptePour = 0;
         msgContenu = "";

         userNewEmail = "";
         userNewCodePostal = "";
         userNewPasse1 = "";
         userNewPasse2 = "";

         userTelephone = "";
         userIsAdmin = false;
         userNewMessagesCount = 0;

         itemImgURL = "";
         itemDateMax = "";
         itemRamassage = 1;
         itemDispos = "";

         dateNow = "";
         dateNowDate = new Date();
         
        dbIdCompte = 0;
        dbEmail = "";
        dbCodePostal = "";
        dbPasse = "";
        dbGPSlat = "";
        dbGPSlon = "";
        dbTelephone = "";
        dbIsAdmin = false;

         userZoom = "12";
         userGPSlat = "46.8129233";
         userGPSlon = "-71.2044023";
         visitorCodePostal = "";
         
         connecter_succes = false;
        return "index.xhtml";
    }
   
    public String getMsgContenu(){
        return msgContenu;
    }
    public void setMsgContenu(String e){
        this.msgContenu = e;
    }
    public int getMsgIdItem(){
        return msgIdItem;
    }
    public void setMsgIdItem(int e){
        this.msgIdItem = e;
    }
    public int getMsgIdComptePour(){
        return msgIdComptePour;
    }
    public void setMsgIdComptePour(int e){
        this.msgIdComptePour = e;
    }
    public int getUserNewMessagesCount(){
        return userNewMessagesCount;
    }
    public void setUserNewMessagesCount(int e){
        this.userNewMessagesCount = e;
    }
    public Part getImage()
    {
        return image;
    }
    
    public void setImage(Part e)
    {
        this.image = e;
    }
    
    public boolean getTryUserPasseSuccess()
    {
        return tryUserPasseSuccess;
    }
    public void setTryUserPasseSuccess(boolean e)
    {
        this.tryUserPasseSuccess = e;
    }

    public String getTryUserPasse()
    {
        return tryUserPasse;
    }
    public void setTryUserPasse(String e)
    {
        this.tryUserPasse = e;
    }
    public int getItemIdToDelete()
    {
        return itemIdToDelete;
    }
    public void setItemIdToDelete(int e)
    {
        this.itemIdToDelete = e;
    }
    
    public Date getDateNowDate()
    {
        return dateNowDate;
    }
    public void setDateNowDate(Date e)
    {
        this.dateNowDate = e;
    }
    
    public int getItemIdToEdit()
    {
        return itemIdToEdit;
    }
    public void setItemIdToEdit(int e)
    {
        this.itemIdToEdit = e;
    }
    
    public String getDateNow()
    {
        return dateNow;
    }
    public void setDateNow(String e)
    {
        this.dateNow = e;
    }
    public boolean getIsAdmin(){
        return userIsAdmin;
    }
    public void setIsAdmin(boolean isAdmin){
        this.userIsAdmin = isAdmin;
    }
    public String getItemImgURL()
    {
        return itemImgURL;
    }
    public String getItemDateMax()
    {
        return itemDateMax;
    }
    public int getItemRamassage()
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
    public void setItemRamassage(int e)
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
    
    public List<messages> getMessages(){
        return messages;
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

                String statementSQL = "INSERT INTO compte(email,cpostal,passe,gpslat,gpslon,isadmin) VALUES(?,?,?,?,?,?)"; //insert
            
                prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
                prepareStatementSQLFormat.setString(1, userNewEmail);
                prepareStatementSQLFormat.setString(2, userNewCodePostal);           
                prepareStatementSQLFormat.setString(3, userNewPasse1);
                prepareStatementSQLFormat.setString(4, userGPSlat);
                prepareStatementSQLFormat.setString(5, userGPSlon);
                prepareStatementSQLFormat.setInt(6, 0);
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
                dbIsAdmin = rs.getBoolean(8);//isAdmin
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
            userIsAdmin = dbIsAdmin;
            //itemIdItem = dbIdItem;
            
            if(dbGPSlon.isEmpty() || dbGPSlat.isEmpty()){
                updateDbLocation_ActuallyEmpty();
            }
            else{
                userGPSlat = dbGPSlat;
                userGPSlon = dbGPSlon;  
            }
            userZoom = "15";
            con.close();
            return "index";
        } 
        else
        {
            userZoom = "15";
            con.close();
            return "connect.xhtml";
        }
    }       
    public void uploadImage() throws SQLException, ClassNotFoundException, ParseException {
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();                
        String stm = "SHOW TABLE STATUS WHERE `Name` = 'item'";
        pst = con.prepareStatement(stm);
        pst.executeQuery();
        rs = pst.getResultSet();
        rs.next();
        String nextItem;
        if(itemIdToEdit!=0)
        {    
            nextItem = rs.getString("Auto_increment");
        }
        else
        {
            nextItem = itemIdToEdit + "";
        }
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String directory = externalContext.getRealPath("upload\\items\\");
        File file = new File(directory, image.getSubmittedFileName());
        File dir = new File(directory+"\\"+ nextItem + "\\");
        if (!dir.exists())
        {
            dir.mkdir();
        }
        try (InputStream input = image.getInputStream()) {
            Files.copy(input, new File(dir.toString(), nextItem+".jpg").toPath(),StandardCopyOption.REPLACE_EXISTING);
            itemImgURL = "upload/items/"+ nextItem+ "/"+ nextItem+".jpg";
        }
        catch (IOException e) {
            // Show faces message?
        }
        rs.close();
        con.close();
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
                        rs.getInt(3),//ramassageType
                        rs.getString(4),//imgURL
                        new Date(rs.getDate(5).getTime()),//date
                        new Date(rs.getDate(6).getTime()),//dateMax
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
    
    public String publier() throws ClassNotFoundException, ParseException, SQLException
    {
        uploadImage();
        try
            {
                PreparedStatement prepareStatementSQLFormat;
                Date dNow = new Date();
                SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
                Date dateFormatDateMax = sdFormat.parse(itemDateMax);
                Connection connectionToBD = this.getConnexion();

                String statementSQL = "INSERT INTO item(idCompte,ramassage,img,date,datemax,dispos) VALUES(?,?,?,?,?,?)"; //insert
            
                prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
                prepareStatementSQLFormat.setInt(1, dbIdCompte);
                prepareStatementSQLFormat.setInt(2, itemRamassage);           
                prepareStatementSQLFormat.setString(3, itemImgURL);
                prepareStatementSQLFormat.setDate(4, new java.sql.Date(dNow.getTime()));
                prepareStatementSQLFormat.setDate(5, new java.sql.Date(dateFormatDateMax.getTime()));
                prepareStatementSQLFormat.setString(6, itemDispos);
                prepareStatementSQLFormat.executeUpdate(); 
                
                connectionToBD.close();
                
                itemImgURL = "";
                Date dN = new Date();
                itemDateMax = sdFormat.format(dN);
                itemRamassage = 0;
                itemDispos = "";
                setMarkers();
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
        boolean bGPS = false;
        int iTimes = 0;
        String stm = "select * from compte join item on compte.idCompte=item.idCompte";
        this.mapItems = new ArrayList<>();
        try
        {
            pst = con.prepareStatement(stm);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next()){
                bGPS = false;
                if (!mapItems.isEmpty())
                {
                    for(markerItems i : mapItems)
                    {
                        if(i.getCpostal() == null ? rs.getString(3) == null : i.getCpostal().equals(rs.getString(3)))
                        {
                            if(i.getLon() == null ? rs.getString(6) == null : i.getLon().equals(rs.getString(6)))
                            {
                                bGPS = true;
                                iTimes++;
                            }
                        }
                    }
                }
                if(!bGPS)
                {
                    this.mapItems.add( new markerItems(
                        rs.getInt(1),//idcompte                
                        rs.getString(2),//email
                        rs.getString(3),//cpostal
                        rs.getString(5),//gsplat
                        rs.getString(6),//gpslon
                        rs.getString(7),//telephone
                        rs.getBoolean(8),//isAdmin
                        rs.getInt(9),//iditem
                        rs.getInt(11),//ramassageType
                        rs.getString(12),//imgURL
                        rs.getDate(13),//date
                        rs.getDate(14),//dateMax
                        rs.getString(15)//dispos
                    ));   
                }
                else
                {
                    double lon = (Double.parseDouble(rs.getString(6))) + (0.0001*iTimes); 
                    this.mapItems.add( new markerItems(
                        rs.getInt(1),//idcompte                
                        rs.getString(2),//email
                        rs.getString(3),//cpostal
                        rs.getString(5),//gsplat
                        Double.toString(lon),//gpslon
                        rs.getString(7),//telephone
                        rs.getBoolean(8),//isAdmin
                        rs.getInt(9),//iditem
                        rs.getInt(11),//ramassageType
                        rs.getString(12),//imgURL
                        rs.getDate(13),//date
                        rs.getDate(14),//dateMax
                        rs.getString(15)//dispos
                    ));   
                }
            }
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        con.close();
    }

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
        int expiration = ((item.getDateMax().getDate() - dateNowDate.getDate())+((item.getDateMax().getMonth() - dateNowDate.getMonth())*30)+((item.getDateMax().getYear()-dateNowDate.getYear())*365));
        html = "<div class=\"card\">" +
"    <div class=\"card-image waves-effect waves-block waves-light\">" +
"      <img class=\"activator\" width=\"240px\" height=\"250px\" src=\"";
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
       if(item.getEmail()!=null)
       {
            html+="<tr><td><b>Email:</b></td><td>";
             html+= item.getEmail();
            html+="</td></tr>";
       }
       if(item.getTel()!=null)
       {
            html+="<tr><td><b>Téléphone:</b></td><td>";
           html+= item.getTel();
           html+="</td></tr>";
       }
       html+="<tr><td><b>Expire dans:</b></td><td>";
        html+= expiration+" jour(s) ("+item.getDateMax().toString()+")";
        html+="</td></tr>";
        
//        html+="<tr><td><a href=\"message.xhtml?id=";
//       html+= item.getIdCompte();
//       html+="\">Envoyez-y un message privé</a></td></tr>";

        html+="<tr><td><b>Communiquer avec l'annonceur par:</b></td><td><a href=\"mailto:";
       html+= item.getEmail();
       html+="\">Courriel</a><br/><a href=\"sendmessage.xhtml?msgIdItem=";
       html+= item.getIdItem();
       html += "&msgIdComptePour=";
       html += item.getIdCompte();
       html +="\">Message interne</a><td></tr>";

       
        html+="<tr><td><a target=\"_blank\" href=\"";
       html+= "https://maps.google.com/maps/place/"+item.getCpostal().substring(0, 3)+"+"+item.getCpostal().substring(3,6);
       html+="\">Ouvrir dans Google Maps</a></td></tr>";
       
        html+="</table></div>";
        return html;
    }
    
    public void runDeleteOldItems() throws ClassNotFoundException
    {
        try
        {            
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            for(markerItems i : mapItems)
            {
                int diff = (i.getDateMax().getDate() - dateNowDate.getDate())+((i.getDateMax().getMonth() - dateNowDate.getMonth())*30)+((i.getDateMax().getYear()-dateNowDate.getYear())*365);
                if(diff<0)
                {
                    String statementSQL = "DELETE FROM item WHERE idItem=?";
                    prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
                    prepareStatementSQLFormat.setInt(1, i.getIdItem());
                    prepareStatementSQLFormat.executeUpdate();
                }
            }            
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
    }
    
    public String delete() throws ClassNotFoundException, SQLException
    {
        try
        {            
            int id = itemIdToDelete;
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            String statementSQL = "DELETE FROM item WHERE idItem=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, id);
            prepareStatementSQLFormat.executeUpdate();
            setItemsFromAccount();
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        itemIdToDelete = 0;
        return "gerer.xhtml";
    }
    
    public String delete2() throws ClassNotFoundException, SQLException
    {
        try
        {            
            int id = itemIdToDelete;
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            String statementSQL = "DELETE FROM item WHERE idItem=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, id);
            prepareStatementSQLFormat.executeUpdate();
            setMarkers();
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        itemIdToDelete = 0;
        return "gerertous.xhtml";
    }
    
        public String modif() throws ClassNotFoundException, SQLException, ParseException
    {
        try
        {            
            int id = itemIdToEdit;
            SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
            Date dateFormatDateMax = sdFormat.parse(itemDateMax);
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            String statementSQL = "UPDATE item SET ramassage=?, img=?, datemax=? WHERE idItem=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, itemRamassage);
            prepareStatementSQLFormat.setString(2, itemImgURL);
            prepareStatementSQLFormat.setDate(3, new java.sql.Date(dateFormatDateMax.getTime()) );
            prepareStatementSQLFormat.setInt(4, id);
            prepareStatementSQLFormat.executeUpdate();
            setItemsFromAccount();
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        itemIdToEdit = 0;
        return "gerer.xhtml";
    }
        
    public String modif2() throws ClassNotFoundException, SQLException, ParseException
    {
        try
        {            
            int id = itemIdToEdit;
            SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
            Date dateFormatDateMax = sdFormat.parse(itemDateMax);
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            String statementSQL = "UPDATE item SET ramassage=?, img=?, datemax=? WHERE idItem=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, itemRamassage);
            prepareStatementSQLFormat.setString(2, itemImgURL);
            prepareStatementSQLFormat.setDate(3, new java.sql.Date(dateFormatDateMax.getTime()) );
            prepareStatementSQLFormat.setInt(4, id);
            prepareStatementSQLFormat.executeUpdate();
            setMarkers();
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        itemIdToEdit = 0;
        return "gerertous.xhtml";
    }
    
    public String loadmespubs() throws ClassNotFoundException, SQLException{
        setItemsFromAccount();
        dateNowDate = new Date();
        return "gerer.xhtml";
    }
    
    public String loadlespubs() throws ClassNotFoundException, SQLException{
        setMarkers();
         dateNowDate = new Date();
        return "gerertous.xhtml";
    }
    
    public String loadCompte() throws ClassNotFoundException, SQLException{
        setItemsFromAccount();
        setMarkers();
        loadMessages();
        return "compte.xhtml";
    }
    
    public String sendMessage() throws ClassNotFoundException, ParseException
    {
        String imgURL="";
        int id = dbIdCompte;
        String email = userEmail;
        try
        {            
            Date d = new Date();
            ResultSet rs;
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();           
            String statementSQL = "SELECT img FROM item WHERE idItem=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, msgIdItem);
            prepareStatementSQLFormat.executeQuery();
            rs = prepareStatementSQLFormat.getResultSet();
            while(rs.next()){
                imgURL = rs.getString(1);
            }
            rs.close();
            if(dbIdCompte==0)
            {
                id = 0;
                email = "anonyme";
            }
            statementSQL = "INSERT INTO message(idComptePar,idComptePour,contenu,date,emailPar,imgURL) VALUES(?,?,?,?,?,?)"; //insert
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, id);
            prepareStatementSQLFormat.setInt(2, msgIdComptePour);
            prepareStatementSQLFormat.setString(3, msgContenu);
            prepareStatementSQLFormat.setDate(4, new java.sql.Date(d.getTime()) );
            prepareStatementSQLFormat.setString(5, email);
            prepareStatementSQLFormat.setString(6, imgURL);
            prepareStatementSQLFormat.executeUpdate();          
            connectionToBD.close();
            msgIdItem = 0;
            msgContenu = "";
            msgIdComptePour = 0;
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        return "index.xhtml";
    }
    
    public void loadMessages() throws ClassNotFoundException, SQLException
    {
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();
        userNewMessagesCount = 0;
        String stm = "select * from message where idComptePour=?";
        this.messages = new ArrayList<>();
        try
        {
            pst = con.prepareStatement(stm);
            pst.setInt(1, dbIdCompte);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next()){
                if(!rs.getBoolean(5))
                {
                    userNewMessagesCount++;
                }
                this.messages.add( new messages(
                    rs.getInt(1),//idMessage                
                    rs.getInt(2),//idComptePar
                    rs.getInt(3),//idComptePour
                    rs.getString(4),//contenu
                    rs.getBoolean(5),//isread
                    rs.getDate(6),//date
                    rs.getString(7),//emailPar
                    rs.getString(8)//imgURL
                ));   
            }
            rs.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        con.close();
        
    }
    
    public String loadModif(int id) throws ParseException{
        itemIdToEdit = id;
        for(items i : items)
        {
            if(i.getIdItem() == id)
            {
                SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
                String dateFormatDateMax = sdFormat.format(i.getDateMax());
                itemImgURL = i.getImg();
                itemDateMax = dateFormatDateMax;
                itemRamassage = i.getRamassage();
            }
        }
        
        return "modif.xhtml?id="+id;
    }
    public String loadModif2(int id){
        itemIdToEdit = id;
        for(markerItems i : mapItems)
        {
            if(i.getIdItem() == id)
            {
                SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
                String dateFormatDateMax = sdFormat.format(i.getDateMax());
                itemImgURL = i.getImg();
                itemDateMax = dateFormatDateMax;
                itemRamassage = i.getRamassage();
            }
        }
        
        return "modif_1.xhtml?id="+id;
    }
    
    public String loadDelete(int id){
        itemIdToDelete = id;
        return "delete.xhtml?id="+id;
    }
    
    public String loadDelete2(int id){
        itemIdToDelete = id;
        return "delete_1.xhtml?id="+id;
    }
    
    public String loadGerer(){
        return "gerer.xhtml";
    }
    
    public String loadPublier(){
        itemImgURL = "";
        SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM, yyyy");
        Date dN = new Date();
        itemDateMax = sdFormat.format(dN);
        itemRamassage = 1;
        return "publier.xhtml";
    }
    public String loadreadmsg() throws ClassNotFoundException, SQLException
    {
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();
        String stm = "select * from message where idComptePour=? ORDER by date DESC, isread";
        this.messages = new ArrayList<>();
        try
        {
            pst = con.prepareStatement(stm);
            pst.setInt(1, dbIdCompte);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next()){
                    this.messages.add( new messages(
                        rs.getInt(1),//idMessage                
                        rs.getInt(2),//idComptePar
                        rs.getInt(3),//idComptePour
                        rs.getString(4),//contenu
                        rs.getBoolean(5),//isread
                        rs.getDate(6),//date
                        rs.getString(7),//emailPar
                        rs.getString(8)//imgURL
                    ));   
            }
            rs.close();
            stm = "UPDATE message SET isread=? WHERE idComptePour=?";
            pst = con.prepareStatement(stm);
            pst.setBoolean(1, true);
            pst.setInt(2, dbIdCompte);
            pst.executeUpdate();
            con.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        con.close();
        return "readmsg.xhtml";
    }
    public String loadIndex(){
        return "index.xhtml";
    }
    public String loadConnect(){
        return "connect.xhtml";
    }
    
    public String loadresetpass(){
        return "pass.xhtml";
    }
    public void changemypasscheck(ComponentSystemEvent event) throws ClassNotFoundException{
         FacesContext fc = FacesContext.getCurrentInstance();

	  UIComponent components = event.getComponent();
          
          // get password
	  UIInput uiInputPassword = (UIInput) components.findComponent("ins_passe");
	  String password = uiInputPassword.getLocalValue() == null ? ""
		: uiInputPassword.getLocalValue().toString();
          
          // get password
	  UIInput uiNewInputPassword = (UIInput) components.findComponent("ins_passe1");
	  String newpassword = uiNewInputPassword.getLocalValue() == null ? ""
		: uiNewInputPassword.getLocalValue().toString();

	  // get confirm password
	  UIInput uiNewInputConfirmPassword = (UIInput) components.findComponent("ins_passe2");
	  String newconfirmPassword = uiNewInputConfirmPassword.getLocalValue() == null ? ""
		: uiNewInputConfirmPassword.getLocalValue().toString();

        if (newpassword.isEmpty() || newconfirmPassword.isEmpty() || password.isEmpty()) {
              return;
        }
          
        if(!newpassword.equals(newconfirmPassword))
        {
            FacesMessage msg = new FacesMessage("Vous n'avez pas correctement inscrit deux fois le nouveau mot de passe");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            fc.renderResponse();
        }
        try
        {            
            int id = dbIdCompte;
            ResultSet rs;
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            String statementSQL = "SELECT passe FROM compte WHERE idCompte=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setInt(1, id);
            prepareStatementSQLFormat.executeQuery();
            rs = prepareStatementSQLFormat.getResultSet();
            rs.next();
            if(!password.equals(rs.getString(1)))
            {
                FacesMessage msg = new FacesMessage("Vous avez inscrit le mauvais mot de passe actuel du compte.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, msg);
                fc.renderResponse();
            } 
            rs.close();
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
    }
    public String changemypass() throws ClassNotFoundException{
        try
        {            
            int id = dbIdCompte;
            ResultSet rs;
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();           
            String statementSQL = "UPDATE compte SET passe=? WHERE idCompte=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setString(1, userNewPasse1);
            prepareStatementSQLFormat.setInt(2, id);
            prepareStatementSQLFormat.executeUpdate();
            setMarkers();
            setItemsFromAccount();            
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        tryUserPasse = "";
        tryUserPasseSuccess = true;

        return "compte.xhtml";
    }
    
    public String loadmesrenseignements() throws SQLException, ClassNotFoundException{
        int id = dbIdCompte;
        PreparedStatement prepareStatementSQLFormat;
        ResultSet rs;
        Connection connectionToBD = this.getConnexion();
        String statementSQL = "SELECT * FROM compte WHERE idCompte=?";
        prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
        prepareStatementSQLFormat.setInt(1, id);
        prepareStatementSQLFormat.executeQuery();
        rs = prepareStatementSQLFormat.getResultSet();
        while(rs.next()){
            userEmail = rs.getString(2);
            userCodePostal = rs.getString(3);
            userTelephone = rs.getString(7);
        }
        rs.close();
        connectionToBD.close();
        return "profil.xhtml";
    }
    
    public String changemyinfos() throws ClassNotFoundException{
        try
        {            
            int id = dbIdCompte;
            updateDbLocation_ActuallyEmpty();
            PreparedStatement prepareStatementSQLFormat;
            Connection connectionToBD = this.getConnexion();
            String statementSQL = "UPDATE compte SET email=?, cpostal=?, telephone=? WHERE idCompte=?";
            prepareStatementSQLFormat = connectionToBD.prepareStatement(statementSQL);
            prepareStatementSQLFormat.setString(1, userEmail);
            prepareStatementSQLFormat.setString(2, userCodePostal);
            prepareStatementSQLFormat.setString(3, userTelephone);
            prepareStatementSQLFormat.setInt(4, id);
            prepareStatementSQLFormat.executeUpdate();
            setMarkers();
            setItemsFromAccount();            
            connectionToBD.close();
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        itemIdToEdit = 0;
        return "compte.xhtml";
    }
}