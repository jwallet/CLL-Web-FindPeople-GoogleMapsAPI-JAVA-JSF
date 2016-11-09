/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscription;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class i {
    private String userEmail = "";
    private String userCodePostal = "";
    private String userPasse1 = "";
    private String userPasse2 = "";
    private int userGPSlat = 0;
    private int userGPSlon = 0;
    
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
    
    private boolean inscrit_succes = false;
    /**
     * Creates a new instance of i
     */
    public i(){}
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
    
    public boolean getInscritStatus()
    {
        return inscrit_succes;
    }
    
    public String getEmail()
    {
        return userEmail;
    }
    
    public void setEmail(String e)
    {
        this.userEmail = e;
    }
    
    public String getCPostal()
    {
        return userCodePostal;
    }
    
    public void setCPostal(String g)
    {
        this.userCodePostal = g;
    }
    
    public String getPasse1()
    {
        return userPasse1;
    }
    
    public void setPasse1(String p1)
    {
        this.userPasse1 = p1;
    }
    
    public String getPasse2()
    {
        return userPasse2;
    }
    
    public void setPasse2(String p2)
    {        
        if(userPasse1.equals(userPasse2))
        {
            userPasse2 = p2;
        }
    }
    
    public void save() throws ClassNotFoundException, SQLException
    {
        boolean PasseMatches = false;
        boolean CodePostalValide = false;
        if(!userPasse1.equals(userPasse2))
        {
            PasseMatches = true;
        }
        if(!userCodePostal.matches("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$"))
        {
            CodePostalValide = true;
        }
        else
        {
            //use google api to find gps lat and lon (localisation)
        }
        
        if(PasseMatches && CodePostalValide)
        {
            ResultSet rs;
            PreparedStatement pst;
            Connection con = this.getConnexion();

            String stm = "INSERT INTO compte(email,cpostal,passe) values($,$,$)"; //insert

            try
            {
                pst = con.prepareStatement(stm);
                pst.setString(1, userEmail);
                pst.setString(2, userCodePostal);           
                pst.setString(3, userPasse1);
                pst.executeUpdate();            
            }
            catch (SQLException ex){
                System.out.println("in exec");
                System.out.println(ex.getMessage());
            }
        }
    }     
    
}
