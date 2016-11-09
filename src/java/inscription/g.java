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

import com.googlecode.gmaps4jsf.component.map.Map;  
import com.googlecode.gmaps4jsf.component.marker.Marker;  
import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;  

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class g {
    String userCodePostal = "";
    String userGPSlat = "46.8129233";
    String userGPSlon = "-71.2044023";
    String userZoom = "12";
    
    private Map map;
    
    public g(){}
    
    
    public String getCodePostal(){
        return userCodePostal;
    }
    
    public void setCodePostal(String e){
        this.userCodePostal = e;
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
    
    public Map getMap(){
        return map;
    }
    
    public void setMap(Map map){
        //to set markers on map
        //http://biemond.blogspot.ca/2008/09/google-maps-for-jsf-gmaps4jsf-in.html
    }
    public void save() throws JSONException, MalformedURLException, IOException{
        String sUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="+ userCodePostal + "&sensor=false";
        URL url = new URL(sUrl);
        URLConnection webConnectionToGoogleAPIgeocode = url.openConnection();
        BufferedReader inBuffer;
        inBuffer = new BufferedReader(new InputStreamReader(webConnectionToGoogleAPIgeocode.getInputStream()));
        String inputLine;
        String jsonResult = "";
        while ((inputLine = inBuffer.readLine()) != null){
            jsonResult += inputLine;
        }
        inBuffer.close();

        JSONObject obj = new JSONObject(jsonResult);
        userZoom = "15";
        userGPSlat = Double.toString(obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
        userGPSlon = Double.toString(obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
    }
}
