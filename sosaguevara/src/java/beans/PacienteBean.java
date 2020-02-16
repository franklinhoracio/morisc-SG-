/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.PacienteDao;
import dao.PacienteDaoImplement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import model.Paciente;
import util.CapturarBcr;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class PacienteBean implements Serializable {

   
    private Paciente paciente;
    private List<Paciente> pacientes;
    
    private boolean switchdob;
    private String option;
    private Date today;
    private int edad;

    public PacienteBean() {
        paciente = new Paciente();
        edad = 0;
        switchdob = true;
        option = "Fecha";
        today = new Date();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public boolean isSwitchdob() {
        return switchdob;
    }

    public void setSwitchdob(boolean switchdob) {
        this.switchdob = switchdob;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Paciente> getPacientes() {
        /*if (pacientes == null) {
            try {
                PacienteDao linkDao = new PacienteDaoImplement();
                pacientes = linkDao.mostrarPacientes();
            } catch (Exception e) {
                System.out.println("Error al cargar: " + e);
            }
        }*/
        PacienteDao linkDao = new PacienteDaoImplement();
                pacientes = linkDao.mostrarPacientes();
        return pacientes;
    }

    public void inicializarPaciente(ActionEvent actionevent) {
        paciente = new Paciente();
        edad = 0;
        switchdob = true;
        option = "Fecha";
    }

    public void nuevoPaciente() {
        PacienteDao linkDao = new PacienteDaoImplement();
        if (paciente.getDatebirth() == null) {
            paciente.setDatebirth(dobcalculated(this.edad));
        }
        linkDao.insertarPaciente(paciente);
        //pacientes = null;

    }

    public void modificarPaciente() {
        PacienteDao linkDao = new PacienteDaoImplement();
        String ID = paciente.getIdPaciente();
        String DUI = paciente.getExtid();
        String PASS = paciente.getPassport();
        paciente.setAdt08(true);
        paciente.setAdt14(false);
        if (!DUI.equals("") && !DUI.equals(ID)) {
            System.out.println(ID);
            System.out.println(DUI);
            paciente.setIdPaciente(DUI);
            paciente.setAdt08(true);
            paciente.setAdt14(false);
        }
        if (DUI.equals("") && !PASS.equals("") && !PASS.equals(ID)) {
            System.out.println(ID);
            System.out.println(PASS);
            paciente.setIdPaciente(PASS);
            paciente.setAdt08(false);
            paciente.setAdt14(true);
        }
        System.out.println(ID);
        System.out.println(DUI);
        System.out.println(PASS);

        linkDao.modificarPaciente(paciente, ID);
        //pacientes = null;
    }

    public void desactivarPaciente() {
        PacienteDao linkDao = new PacienteDaoImplement();
        linkDao.eliminarPaciente(paciente);
        //pacientes = linkDao.mostrarPacientes();
        //acientes = null;
    }

    public void cambioDob() {
        if (option.equals("Edad")) {
            switchdob = false;
        } else {
            switchdob = true;
        }
    }

    public Date dobcalculated(int edad) {

        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy
        calendar.add(Calendar.YEAR, -edad); //Le resta la cantidad de años  
        return calendar.getTime();

    }

    

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }


    public void setBcrDui(){
        this.paciente = CapturarBcr.leerBcr();
    }

    

}
