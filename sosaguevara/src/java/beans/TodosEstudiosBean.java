/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Rxinterface;
import net.sf.jasperreports.engine.JRException;
import util.Reporte2;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class TodosEstudiosBean implements Serializable {

    /**
     * Creates a new instance of TodosEstudiosBean
     */
    private Rxinterface estudios;
    private List<Rxinterface> listasEstudios;
    private Date initDate;
    private Date endDate;
    private Date today;

    public Rxinterface getEstudios() {
        return estudios;
    }

    public void setEstudios(Rxinterface estudios) {
        this.estudios = estudios;
    }

    public List<Rxinterface> getListasEstudios() {
        return listasEstudios;
    }

    public void setListasEstudios(List<Rxinterface> listasEstudios) {
        this.listasEstudios = listasEstudios;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public TodosEstudiosBean() {
        estudios = new Rxinterface();
        today = new Date();
        initDate = new Date();
    }

    public void buscarEstudios(Date initDate, Date endDate) {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        
        if(endDate!=null){
            listasEstudios = linkDao.mostrarEstudiosTotal(initDate, endDate);
        }else {
            System.out.println("FECHA FINAL NULA");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontraron estudios!", "Debe seleccionar fecha final!"));
        }
        
        
        
    }
    
    public String imprimirReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        System.out.println("BEAN TODOS LOS ESTUDIOS METODO IMPRIMIR REPORTE");
        if (this.estudios != null) {
            System.out.println("NUMERO DE ACCESO: " + this.estudios.getAccnumber());
            Reporte2 informeRx = new Reporte2();
            informeRx.getReporte((int) this.estudios.getAccnumber());
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            System.out.println("ESTUDIO NULO");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo imprimir", "Debe seleccionar un estudio!"));
        }
        return "mEstudios";

    }

}
