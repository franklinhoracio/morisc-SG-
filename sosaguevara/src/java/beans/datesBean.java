/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class datesBean implements Serializable{

    /**
     * Creates a new instance of datesBean
     */
    Date fechaestudios;
    
    public datesBean() {
    }

    public Date getFechaestudios() {
        return fechaestudios;
    }

    public void setFechaestudios(Date fechaestudios) {
        this.fechaestudios = fechaestudios;
    }
    
    public Date getFechaactual() {
        if(fechaestudios == null) fechaestudios = new java.util.Date();
        return fechaestudios;
    }

    public void setFechaactual(Date fechaestudios) {
        this.fechaestudios = fechaestudios;
    }
    
    public Date fechaactual(){
        fechaestudios = new java.util.Date();
        return fechaestudios;
    }
    
    public void mostrarFecha(Date dateform){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Fecha:", dateform.toString()));
    }

}
