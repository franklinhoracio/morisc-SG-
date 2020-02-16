/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



/**
 *
 * @author frodriguez
 */
@XmlRootElement(name = "email")
@XmlType(propOrder = {"username", "password", "server", "puerto"})
public class EmailSource implements java.io.Serializable{
    

    private String username;
    private String password; 
    private String server;
    private String puerto;
    
    
    @XmlAttribute 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }


    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    
    
    
    
}
