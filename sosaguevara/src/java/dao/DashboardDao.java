/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import model.Aseguradoras;

/**
 *
 * @author franklin
 */
public interface DashboardDao {
    public List<Aseguradoras> mostrarAseguradoras();
    public int cantidadEstudiosDia(String status, Date date);
    public int cantidadEstudiosMod(String modalidad, Date date);
    public int cantidadEstudiosAseg(int aseguradora, Date date);
    public int cantidadEstudios(Date date);
    public int cantidadPacientesDia(Date date);
    public int cantidadEstudios();
    public int cantidadPacientes();
    public int cantidadMedicos();
    //CANTIDAD PACIENTES POR MODALIDAD
    public int cantidadPacientesModalidad(String modalidad, String genero, Date date);
    public int cantidadPacientesModalidad(String modalidad, Date date);
    public int cantidadPacientesAseguradora(int aseguradora, Date date);
    public int cantidadPacientesGenero(String genero, Date date);
    //METODOS PARA SACAR DATOS POR RANGO DE FECHAS PARA EL DASHBOARD1
    public int cantidadEstudiosDia(String status, Date date, Date endDate);
    public int cantidadEstudiosMod(String modalidad, Date date, Date endDate);
    public int cantidadEstudiosAseg(int aseguradora, Date date, Date endDate);
    public int cantidadEstudios(Date date, Date endDate);
    public int cantidadPacientesDia(Date date, Date endDate);
    
    
    
}
