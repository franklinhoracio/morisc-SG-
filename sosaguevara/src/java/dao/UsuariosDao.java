/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Usuarios;

/**
 *
 * @author Administrator
 */
public interface UsuariosDao {
    public List<Usuarios> mostrarUsuarios();
    public List<Usuarios> mostrarReferentes();
    public List<Usuarios> login(String user, String password);
    public void insertarUsuarios(Usuarios usuario);
    public void modificarUsuarios(Usuarios usuario);
    public void eliminarUsuarios(Usuarios usuario);
    public void setPass(Usuarios usuario);
    public List<Usuarios> radiologo(String nameuser);
    
}
