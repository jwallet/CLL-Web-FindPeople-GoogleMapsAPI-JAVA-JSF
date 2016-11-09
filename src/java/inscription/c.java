/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

@ManagedBean
@SessionScoped
public class c {
    
    private String userEmail = "";
    private String userCodePostal = "";
    private String userPasse = "";
    
    private String userZoom = "12";
    private String userGPSlon = "";
    private String userGPSlat = "";
    
    private int dbIdCompte = 0;
    private String dbEmail = "";
    private String dbCodePostal = "";
    private String dbPasse = "";
    private int dbGPSlat = 0;
    private int dbGPSlon = 0;
    private int dbNoRue = 0;
    private String dbNomRue = "";
    private String dbVoisinage = "";
    private String dbVille = "";
    private String dbProvince = "";
    private String dbPays = "";
    private int dbTelephone = 0;
    private int dbCellulaire = 0;
    private String dbPseudo = "";
    
    private boolean connecter_succes = false;
    /**
     * Creates a new instance of i
     */
    public c() 
    {
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
    
    public boolean getConnecterStatus()
    {
        return connecter_succes;
    }
    
    public String setDeconnexion()
    {
        userEmail = "";
        userCodePostal = "";
        userPasse = "";
        connecter_succes=false;
        return "index.xhtml";
    }
    
    public String getOublier()
    {
        String page = "passeoublie.xhtml";
        return page;
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
   
    public void save() throws ClassNotFoundException, SQLException
    {
        ResultSet rs;
        PreparedStatement pst;
        Connection con = this.getConnexion();
        
        String stm = "select * from compte";
      
        try
        {
            pst = con.prepareStatement(stm);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next()){
                dbIdCompte = rs.getInt(1);
                dbEmail = rs.getString(2);
                dbCodePostal = rs.getString(3);
                dbPasse = rs.getString(4);
                dbGPSlat = rs.getInt(5);
                dbGPSlon = rs.getInt(6);
                dbNoRue = rs.getInt(7);
                dbNomRue = rs.getString(8);
                dbVoisinage = rs.getString(9);
                dbVille = rs.getString(10);
                dbProvince = rs.getString(11);
                dbPays = rs.getString(12);
                dbTelephone = rs.getInt(13);
                dbCellulaire = rs.getInt(14);
                dbPseudo = rs.getString(15);
            }
        }
        catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        
        connecter_succes = dbEmail.equals(userEmail) && dbPasse.equals(userPasse);

    }   
    
    public void recentrerSurCPostal() {
        //sassurer de recentrer selon les donnees recueillis dans classe G et classe C
        //trouver moyen d importer la classe
    }
    
}
