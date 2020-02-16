/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UsuariosDao;
import dao.UsuariosDaoImplement;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import model.Usuarios;
import util.SimpleMD5;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class UsuariosBean implements Serializable {

    /**
     * Creates a new instance of UsuariosBean
     */
    Usuarios usuario;
    List<Usuarios> usuarios;
    List<Usuarios> referentes;

    public UsuariosBean() {
        usuario = new Usuarios();
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<Usuarios> getUsuarios() {
        if (usuarios == null) {
            try {
        UsuariosDao linkDao = new UsuariosDaoImplement();
        usuarios = linkDao.mostrarUsuarios();
        } catch (Exception e) {
                System.out.println("Error al cargar: " + e);
            }
        }    
        return usuarios;
    }

    public void inicializarUsuario(ActionEvent actionevent){
        usuario = new Usuarios();
    }
    
    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuarios> getReferentes() {
        UsuariosDao linkDao = new UsuariosDaoImplement();
        referentes = linkDao.mostrarReferentes();
        return referentes;
    }
    
    public void nuevoUsuario(){
        UsuariosDao linkDao = new UsuariosDaoImplement();
        SimpleMD5 md5 = new SimpleMD5();
        usuario.setPassword(md5.securePass(usuario.getPassword()));
        linkDao.insertarUsuarios(usuario);
        usuarios = null;
    }
    
    public void modificarUsuario(){
        UsuariosDao linkDao = new UsuariosDaoImplement();
        linkDao.modificarUsuarios(usuario);
        usuarios = null;
    }
    
    public void passUsuario(){
        UsuariosDao linkDao = new UsuariosDaoImplement();
        SimpleMD5 md5 = new SimpleMD5();
        usuario.setPassword(md5.securePass(usuario.getPassword()));
        linkDao.setPass(usuario);
    }
    
}
