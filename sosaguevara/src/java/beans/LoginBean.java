/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import util.SimpleMD5;
import util.UserDao;
import util.Util;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

private static final long serialVersionUID = 1L;
    private String password, message, uname, role;

 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        SimpleMD5 md5 = new SimpleMD5();
        String encryptpass = md5.securePass(password);
        this.password = encryptpass;
    }
 
    public String getUname() {
        return uname;
    }
 
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
    public String loginProject() {
        boolean result = UserDao.login(uname, password);
        if (result) {
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);
            session.setAttribute("role-name", UserDao.getUserole(uname, password));
            session.setAttribute("role-name", UserDao.getUserole(uname, password));
            // set user role
            this.role = UserDao.getUserole(uname, password);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Bienvenido!",
                    uname));
            return "home";
        } else {
 
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Login Incorrecto!",
                    "Intente nuevamente!"));
 
            // invalidate session, and redirect to other pages
            //message = "Invalid Login. Please Try Again!";
            return "login";
        }
    }
 
    public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return "login";
   }
    
   public String logeado(){
       if(this.uname.isEmpty()){
        return "false";   
       }
       return "true";
   }
   
   public boolean roleActivo(String role){
       if(role.equals(this.role)){
          return true; 
       }else return false;
       
   }
   
   public void radiologoActivo(){
       
   }
    
}
