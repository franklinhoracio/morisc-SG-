/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Paciente;

/**
 *
 * @author Administrator
 */
public interface PacienteDao {
    public List<Paciente> mostrarPacientes();
    public List<Paciente> mostrarPacientesDui(String dui);
    public List<Paciente> mostrarPacientesPass(String passport);
    public String insertarPaciente(Paciente paciente);
    public void modificarPaciente(Paciente paciente, String oldid);
    public void eliminarPaciente(Paciente paciente);
    

    public Paciente mostrarPacienteId(String id);
    
    public List<Paciente> mostrarListaPacientes();
    
}
