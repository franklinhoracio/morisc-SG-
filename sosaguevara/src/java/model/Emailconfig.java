package model;
// Generated Dec 22, 2019 8:33:35 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Emailconfig generated by hbm2java
 */
public class Emailconfig  implements java.io.Serializable {


     private String username;
     private String password;
     private String server;
     private String puerto;
     private Date auFechainsert;
     private Integer auUserinsert;
     private Date auFechaupdt;
     private Integer auUserupdt;

    public Emailconfig() {
    }

	
    public Emailconfig(String username) {
        this.username = username;
    }
    public Emailconfig(String username, String password, String server, String puerto, Date auFechainsert, Integer auUserinsert, Date auFechaupdt, Integer auUserupdt) {
       this.username = username;
       this.password = password;
       this.server = server;
       this.puerto = puerto;
       this.auFechainsert = auFechainsert;
       this.auUserinsert = auUserinsert;
       this.auFechaupdt = auFechaupdt;
       this.auUserupdt = auUserupdt;
    }
   
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getServer() {
        return this.server;
    }
    
    public void setServer(String server) {
        this.server = server;
    }
    public String getPuerto() {
        return this.puerto;
    }
    
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
    public Date getAuFechainsert() {
        return this.auFechainsert;
    }
    
    public void setAuFechainsert(Date auFechainsert) {
        this.auFechainsert = auFechainsert;
    }
    public Integer getAuUserinsert() {
        return this.auUserinsert;
    }
    
    public void setAuUserinsert(Integer auUserinsert) {
        this.auUserinsert = auUserinsert;
    }
    public Date getAuFechaupdt() {
        return this.auFechaupdt;
    }
    
    public void setAuFechaupdt(Date auFechaupdt) {
        this.auFechaupdt = auFechaupdt;
    }
    public Integer getAuUserupdt() {
        return this.auUserupdt;
    }
    
    public void setAuUserupdt(Integer auUserupdt) {
        this.auUserupdt = auUserupdt;
    }




}

