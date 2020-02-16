/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.ProyeccionesDao;
import dao.ProyeccionesDaoImplement;
import model.Proyecciones;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class ProyeccionesBean implements Serializable {

    /**
     * Creates a new instance of UsuariosBean
     */
    Proyecciones cargo;
    List<Proyecciones> cargos;

    public ProyeccionesBean() {
        cargo = new Proyecciones();
    }

    public Proyecciones getCargo() {
        return cargo;
    }

    public void setCargo(Proyecciones cargo) {
        this.cargo = cargo;
    }

    public List<Proyecciones> getCargos() {
        /*if (cargos == null) {
            try {
                ProyeccionesDao linkDao = new ProyeccionesDaoImplement();
                cargos = linkDao.mostrarCargos();
            } catch (Exception e) {
                System.out.println("Error al cargar: " + e);
            }
        }*/
        ProyeccionesDao linkDao = new ProyeccionesDaoImplement();
        cargos = linkDao.mostrarCargos();
        return cargos;
    }

    public void insertarProducto(){
        ProyeccionesDao linkDao = new ProyeccionesDaoImplement();
        linkDao.insertarCargo(cargo);
        //cargos = null;
    }

    public void inicializarCargo(ActionEvent actionevent){
        cargo = new Proyecciones();

    }
    
    public void modificarProducto(){
        ProyeccionesDao linkDao = new ProyeccionesDaoImplement();
        linkDao.modificarCargo(cargo);
        //cargos = null;
    }

}
