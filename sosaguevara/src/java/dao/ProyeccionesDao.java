/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Proyecciones;

/**
 *
 * @author Administrator
 */
public interface ProyeccionesDao {
    public List<Proyecciones> mostrarCargos();
    public String obtenerCargo(long cpt);
    public Proyecciones obtenerCargoObj(long cpt);
    public void insertarCargo(Proyecciones cargo);
    public void modificarCargo(Proyecciones cargo);
    public void eliminarCargo(Proyecciones cargo);
}
