/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.PacienteDao;
import dao.PacienteDaoImplement;
import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import model.Paciente;
import model.Rxinterface;
import net.sf.jasperreports.engine.JRException;
import util.Reporte2;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class FichaPacienteBean {

    private String idpaciente;
    private Paciente paciente;
    private List<SelectItem> listaPacientes;
    private Rxinterface estudio;
    private List<Rxinterface> estudios;

    /**
     * Creates a new instance of FichaPacienteBean
     */
    public FichaPacienteBean() {
        idpaciente = "";
        paciente = new Paciente();
        estudio = new Rxinterface();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Rxinterface getEstudio() {
        return estudio;
    }

    public void setEstudio(Rxinterface estudio) {
        this.estudio = estudio;
    }

    public List<Rxinterface> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<Rxinterface> estudios) {
        this.estudios = estudios;
    }

    public String getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(String idpaciente) {
        this.idpaciente = idpaciente;
    }

    public List<SelectItem> getListaPacientes() {

        this.listaPacientes = new ArrayList<SelectItem>();
        PacienteDao linkDao = new PacienteDaoImplement();
        List<Paciente> p = linkDao.mostrarPacientes();
        listaPacientes.clear();

        for (Paciente pacientes : p) {
            SelectItem pacienteItem = new SelectItem(pacientes.getIdPaciente(), pacientes.getLastnamepatient() + " , " + pacientes.getNamepatient());
            this.listaPacientes.add(pacienteItem);
        }
        return listaPacientes;
    }

    public void setListaPacientes(List<SelectItem> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public void estudiosPaciente() {
        if (idpaciente != null) {
            RxinterfaceDao linkEstudios = new RxinterfaceDaoImplement();
            estudios = linkEstudios.mostrarEstudiosPaciente(idpaciente);

            PacienteDao linkPaciente = new PacienteDaoImplement();
            paciente = linkPaciente.mostrarPacienteId(idpaciente);

            estudio.setReport("");
        }
    }

    public String imprimirReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        System.out.println("BEAN FICHAPACIENTE METODO IMPRIMIR REPORTE");
        if (this.estudio != null) {
            System.out.println("NUMERO DE ACCESO: " + this.estudio.getAccnumber());
            Reporte2 informeRx = new Reporte2();
            informeRx.getReporte((int) this.estudio.getAccnumber());
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            System.out.println("ESTUDIO NULO");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo imprimir", "Debe seleccionar un estudio!"));
        }
        return "mFichaPaciente";

    }

}
