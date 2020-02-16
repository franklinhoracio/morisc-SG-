/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.edadUtil;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class edadBean implements Serializable{

    /**
     * Creates a new instance of edadBean
     */
    String dato1,dato2;
    
    public edadBean() {
    }

    public String getDato1() {
        return dato1;
    }

    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    public String getDato2() {
        return dato2;
    }

    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }
    
    public int magic(Date dob){
        //String dato=null;
        edadUtil edad = new edadUtil();
        int dato = edad.calcularEdad(dob);
        
        
        return dato;
    }
    
}
