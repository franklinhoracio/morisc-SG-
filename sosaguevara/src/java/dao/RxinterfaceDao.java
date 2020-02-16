/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import model.Aseguradoras;
import model.Medicos;
import model.Paciente;
import model.Proyecciones;
import model.Rxinterface;
import model.Usuarios;

/**
 *
 * @author Administrator
 */
public interface RxinterfaceDao {
    public List<Rxinterface> mostrarEstudiosPaciente(String idpaciente);
    public List<Rxinterface> mostrarInfarce(Long accnum);
    public List<Rxinterface> mostrarInterfaces(Date workdate);
    public List<Rxinterface> mostrarInformeDiario(Date workdate);
    public List<Rxinterface> mostrarInformes(Date workdate);
    public List<Rxinterface> mostrarFinalizados(Date workdate);
    public List<Rxinterface> mostrarInformesMul(Paciente paciente, Date workdate);
    public List<Rxinterface> mostrarFinalizadosMul(Paciente paciente, Date workdate);
    public List<Rxinterface> mostrarNombreEstudio(Long accnum);
    public List<Rxinterface> mostrarEstudiosTotal(Date initDate, Date endDate);
    
    public void insertarInterface(Rxinterface rxinterface);
    public void insertarInterfaceHist(Rxinterface rxinterface);
    public void modificarInterface(Rxinterface rxinterface);
    public void cancelarInterface(Rxinterface rxinterface);
    public void completarInterface(Rxinterface rxinterface);
    public void finalizarInterface(Rxinterface rxinterface);
    public void editarRepoDiario(Rxinterface rxinterface);
    
    public void insertarInforme(Rxinterface rxinterface);
    public void insertarFinalizado(Rxinterface rxinterface);
    public void enviarFinalizado(Rxinterface rxinterface);
    public void edicionInforme(Rxinterface rxinterface, String status);
    public void informeRadiologo(Rxinterface rxinterface);
    
    public List<Paciente> mostrarPacientes();
    public List<Medicos> mostrarMedicos();
    public List<Medicos> mostrarMedicosFilter(String query);
    public List<Usuarios> mostrarRadiologos();
    public List<Proyecciones> mostrarProyecciones();
    public List<Aseguradoras> mostrarAseguradoras();
    
    public int cantidadEstudios(String status, Date date);
    
    public List<Paciente> mostrarPacientesTimestamp(String nombre, String apellido, Date dob);
   
}
