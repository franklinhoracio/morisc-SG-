/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.MedicosDao;
import dao.MedicosDaoImplement;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import model.Medicos;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class MedicosBean implements Serializable {

    Medicos medico;
    List<Medicos> medicos;
    List<Medicos> topmedicos;

    public MedicosBean() {
        medico = new Medicos();
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    public List<Medicos> getMedicos() {
        /*if (medicos == null) {
            try {
                MedicosDao linkDao = new MedicosDaoImplement();
                medicos = linkDao.mostrarMedicos();
            } catch (Exception e) {
                System.out.println("Error al cargar: " + e);
            }
        }*/

        MedicosDao linkDao = new MedicosDaoImplement();
        medicos = linkDao.mostrarMedicos();

        return medicos;
    }
    
    public List<Medicos> getTopMedicos() {

        MedicosDao linkDao = new MedicosDaoImplement();
        topmedicos = linkDao.listaTopTen();

        return topmedicos;
    }

    public void insertarMedico() {
        MedicosDao linkDao = new MedicosDaoImplement();
        linkDao.insertarMedico(medico);
        medicos = null;
    }

    public void inicializarMedico(ActionEvent actionevent) {
        medico = new Medicos();

    }

    public void modificarMedico() {
        MedicosDao linkDao = new MedicosDaoImplement();
        linkDao.modificarMedico(medico);
        medicos = null;
    }

}
