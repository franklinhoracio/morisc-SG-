/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.UsuariosDao;
import dao.UsuariosDaoImplement;
import java.util.List;
import model.Usuarios;

/**
 *
 * @author Administrator
 */
public class UserDao {

    public static boolean login(String user, String password) {

        List<Usuarios> users;

        try {
            //ClientesDao linkDao = new ClientesDaoImplement();
            //linkDao.insertarCiente(cliente);
            UsuariosDao linkDao = new UsuariosDaoImplement();
            users = linkDao.login(user, password);
            System.out.println("EL ROL: "+users.get(0).getRol());

            //ResultSet rs = ps.executeQuery();
            if (users.isEmpty()) // found
            {
                //System.out.println(rs.getString("user"));
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        }
    }

    public static String getUserole(String user, String password) {

        List<Usuarios> users;
        String role;

        UsuariosDao linkDao = new UsuariosDaoImplement();
        users = linkDao.login(user, password);
        //System.out.println(users.get(0).getRol());
        role = users.get(0).getRol();

        return role;
    }
    


}
