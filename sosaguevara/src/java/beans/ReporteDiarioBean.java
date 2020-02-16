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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import model.Aseguradoras;
import model.Rxinterface;
import net.sf.jasperreports.engine.JRException;
import util.Reporte2;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class ReporteDiarioBean implements Serializable {

    @ManagedProperty(value = "#{pacienteBean}")
    private PacienteBean pacienteBean;
    private Rxinterface rxestudio;
    private List<Rxinterface> rxestudios;
    private List<SelectItem> listaAseguradoras;
    private Date workdate;
    private Date today;
    //private double totalPrecios;

    public ReporteDiarioBean() {
        rxestudio = new Rxinterface();
        workdate = new Date();
        today = new Date();
    }

    public Rxinterface getRxinterface() {
        return rxestudio;
    }

    public void setRxinterface(Rxinterface rxinterface) {
        this.rxestudio = rxinterface;
    }

    public Date getWorkdate() {
        return workdate;
    }

    public void setWorkdate(Date workdate) {
        this.workdate = workdate;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public List<Rxinterface> actualizarInformeDiario() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxestudios = linkDao.mostrarInformeDiario(workdate);
        //rxinterface = new Rxinterface();
        if (rxestudio == null) {
            rxestudio = new Rxinterface();
        }
        return rxestudios;
    }

    public List<Rxinterface> pollWorklist() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxestudios = linkDao.mostrarInterfaces(workdate);
        if (rxestudios == null) {
            rxestudio = new Rxinterface();
        }
        return rxestudios;
    }

    public PacienteBean getPacienteBean() {
        return pacienteBean;
    }

    public void setPacienteBean(PacienteBean pacienteBean) {
        this.pacienteBean = pacienteBean;
    }

    //Metodo para invocar el reporte y enviarle los parametros
    public String verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        Reporte2 informeRx = new Reporte2();
        informeRx.getReporte((int) this.rxestudio.getAccnumber());
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    public List<Rxinterface> getRxestudios() {

        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxestudios = linkDao.mostrarInformeDiario(workdate);
        return rxestudios;
    }

    public List<SelectItem> getListaAseguradoras() {
        this.listaAseguradoras = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Aseguradoras> e = linkDao.mostrarAseguradoras();
        listaAseguradoras.clear();

        for (Aseguradoras aseguradoras : e) {
            SelectItem aseguradorasItem = new SelectItem(aseguradoras.getIdaseguradora(), aseguradoras.getAseguradora());
            this.listaAseguradoras.add(aseguradorasItem);
        }
        return listaAseguradoras;
    }

    public void setRxestudios(List<Rxinterface> rxestudios) {
        this.rxestudios = rxestudios;
    }

    public BigDecimal totalPrecios() {
        Double sumPagado=0.0;
        for (int i = 0; i < rxestudios.size(); i++) {
                sumPagado+=rxestudios.get(i).getPagado().doubleValue();  
        }
        return new BigDecimal(sumPagado).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    
    public void guardarRegistro(){
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.editarRepoDiario(rxestudio);
    }


}
