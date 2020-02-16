/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Emailconfig;

/**
 *
 * @author franklin
 */
public interface EmailconfigDao {
    public Emailconfig obtenerDatosCorreo();
    public void updateDatos(Emailconfig emailconfig);
}
