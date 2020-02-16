/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.EmailconfigDao;
import dao.EmailconfigDaoImplement;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Emailconfig;



/**
 *
 * @author frodriguez
 */
@ManagedBean
@SessionScoped
public class MailBean implements Serializable{

    /**
     * Creates a new instance of MailBean
     */
    private Emailconfig emailconfig;

    public Emailconfig getEmailconfig() {
        
        return emailconfig;
    }

    public void setEmailconfig(Emailconfig emailconfig) {
        this.emailconfig = emailconfig;
    }

    public MailBean() {
        emailconfig = obtenerDatos();
    }
    
    public void actualizar(){
        EmailconfigDao linkDao = new EmailconfigDaoImplement();
        linkDao.updateDatos(emailconfig);
    }
    
    public Emailconfig obtenerDatos(){
        EmailconfigDao linkDao = new EmailconfigDaoImplement();
        return linkDao.obtenerDatosCorreo();
    }
    
    
}
