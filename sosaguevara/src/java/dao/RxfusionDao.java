/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Paciente;
import model.Rxfusion;

/**
 *
 * @author franklin
 */
public interface RxfusionDao {
    public List<Rxfusion> mostrarRxfusion();
    public void insertarRxfusion(Rxfusion rxfusion);
    public void modificarRxfusion(Rxfusion rxfusion);

    public void fusionarPaciente(Rxfusion rxfusion);
    public void borrarPaciente(String idmrg);
    
}
