/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.PacienteDao;
import dao.PacienteDaoImplement;
import dao.RxfusionDao;
import dao.RxfusionDaoImplement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import model.Paciente;
import model.Rxfusion;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class RxfusionBean implements Serializable {

    /**
     * Creates a new instance of RxfusionBean
     */
    //@ManagedProperty(value = "#{pacienteBean}")
    //private PacienteBean pacienteBean;

    private Rxfusion rxfusion;
    private List<SelectItem> listaPacientes;
    private Paciente masterpatient;
    private Paciente mergepatient;

    public RxfusionBean() {
        rxfusion = new Rxfusion();
    }

    //public PacienteBean getPacienteBean() {
    //    return pacienteBean;
    //}

    //public void setPacienteBean(PacienteBean pacienteBean) {
    //    this.pacienteBean = pacienteBean;
    //}

    public Rxfusion getRxfusion() {
        return rxfusion;
    }

    public void setRxfusion(Rxfusion rxfusion) {
        this.rxfusion = rxfusion;
    }
    
    public Paciente getMasterpatient() {
        return masterpatient;
    }

    public void setMasterpatient(Paciente masterpatient) {
        this.masterpatient = masterpatient;
    }

    public Paciente getMergepatient() {
        return mergepatient;
    }

    public void setMergepatient(Paciente mergepatient) {
        this.mergepatient = mergepatient;
    }

    public List<SelectItem> getListaPacientes() {

        this.listaPacientes = new ArrayList<SelectItem>();
        PacienteDao linkDao = new PacienteDaoImplement();
        List<Paciente> p = linkDao.mostrarListaPacientes();
        listaPacientes.clear();

        for (Paciente pacientes : p) {
            SelectItem pacienteItem = new SelectItem(pacientes.getIdPaciente(), pacientes.getIdPaciente() + " , " + pacientes.getLastnamepatient() + " , " + pacientes.getNamepatient());
            this.listaPacientes.add(pacienteItem);
        }
        return listaPacientes;
    }

    public void setListaPacientes(List<SelectItem> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public void registrarFusionPaciente() {
        RxfusionDao linkDao = new RxfusionDaoImplement();
        if (rxfusion.getIdmasterpatient().equals(rxfusion.getIdmrg())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No es posible fusionar el mismo paciente!"));
        } else {
            linkDao.fusionarPaciente(rxfusion);//cambiar el id de los estudios del paciente a fusionar
            linkDao.borrarPaciente(rxfusion.getIdmrg());//borrar datos de paciente merge
            linkDao.insertarRxfusion(rxfusion);
        }

    }

    public void setPatients(){
        PacienteDao linkDao = new PacienteDaoImplement();
        this.masterpatient = linkDao.mostrarPacienteId(this.rxfusion.getIdmasterpatient());
        this.mergepatient = linkDao.mostrarPacienteId(this.rxfusion.getIdmrg());
    
    }

    
    
}
