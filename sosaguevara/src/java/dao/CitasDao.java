/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Citas;

/**
 *
 * @author franklin
 */
public interface CitasDao {
    
    public List<Citas> mostrarCitas();
    public void insertarCita(Citas cita);
    public void modificarCita(Citas cita);
    public void desactivarCita(Citas cita);
    public void arrivarCita(Citas cita);
    
}
