/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.DashboardDao;
import dao.DashboardDaoImplement;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import model.Aseguradoras;
import org.primefaces.model.chart.DonutChartModel;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class NewDashboardBean implements Serializable {

    @ManagedProperty(value = "#{aseguradorasBean}")
    private AseguradorasBean aseguradorasBean;
    private DonutChartModel donutModel1;
    private DonutChartModel donutModel2;
    private DonutChartModel donutModel3;
    private Date today;
    private Date workdate;
    private Date endDate;

    @PostConstruct
    public void init() {
        createDonutModels();
    }

    public NewDashboardBean() {
        this.today = new Date();
        System.out.println("INSTANCIANDO EL BEAN");
        System.out.println(this.workdate);
        
        if(this.workdate==null){
            this.workdate = new Date(); 
        }
          
        System.out.println(this.workdate);
    }

    public DonutChartModel getDonutModel1() {
        return donutModel1;
    }

    public DonutChartModel getDonutModel2() {
        return donutModel2;
    }

    public DonutChartModel getDonutModel3() {
        return donutModel3;
    }

    public Date getWorkdate() {
        return workdate;
    }

    public void setWorkdate(Date workdate) {
        this.workdate = workdate;
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

    public AseguradorasBean getAseguradorasBean() {
        return aseguradorasBean;
    }

    public void setAseguradorasBean(AseguradorasBean aseguradorasBean) {
        this.aseguradorasBean = aseguradorasBean;
    }

    public void createDonutModels() {

        if(this.workdate!=null){
            this.endDate = null;
        donutModel1 = initDonutModel1();
        donutModel1.setTitle("Estudios por Status");
        donutModel1.setLegendPosition("w");
        donutModel1.setSeriesColors("477998,4674dd,408dd6,82b4e5,cce4fc");
        donutModel1.setShowDataLabels(true);
        //donutModel1.setLegendCols(3);
        //donutModel1.setLegendRows(3);
        donutModel1.setShowDatatip(true);

        donutModel2 = initDonutModel2();
        donutModel2.setTitle("Estudios por Modalidad");
        donutModel2.setLegendPosition("w");
        //donutModel2.setSliceMargin(5);
        //donutModel2.setShowDataLabels(true);
        donutModel2.setDataFormat("value");
        donutModel2.setSeriesColors("4674dd,408dd6,82b4e5,cce4fc");
        //donutModel2.setShadow(false);

        donutModel3 = initDonutModel3();
        donutModel3.setTitle("Estudios por Aseguradora");
        donutModel3.setLegendPosition("w");
        donutModel3.setSeriesColors("477998,4779c8,82b4e5,009ddc,009df0,02a9ff,02a9ea,6daeff,6daedb,cce3ff,ccdbdc,cce4fc");
    
        System.out.println("FECHA INICIAL: "+this.workdate);
        }
        
    }

    public void createRangeDonutModels() {

        //this.endDate = null;
        donutModel1 = initDonutModel4();
        donutModel1.setTitle("Estudios por Status");
        donutModel1.setLegendPosition("w");
        donutModel1.setSeriesColors("477998,4674dd,408dd6,82b4e5,cce4fc");
        donutModel1.setShowDataLabels(true);
        //donutModel1.setLegendCols(3);
        //donutModel1.setLegendRows(3);
        donutModel1.setShowDatatip(true);

        donutModel2 = initDonutModel5();
        donutModel2.setTitle("Estudios por Modalidad");
        donutModel2.setLegendPosition("w");
        //donutModel2.setSliceMargin(5);
        //donutModel2.setShowDataLabels(true);
        donutModel2.setDataFormat("value");
        donutModel2.setSeriesColors("4674dd,408dd6,82b4e5,cce4fc");
        //donutModel2.setShadow(false);

        donutModel3 = initDonutModel6();
        donutModel3.setTitle("Estudios por Aseguradora");
        donutModel3.setLegendPosition("w");
        donutModel3.setSeriesColors("477998,4779c8,82b4e5,009ddc,009df0,02a9ff,02a9ea,6daeff,6daedb,cce3ff,ccdbdc,cce4fc");
    }

    //Grafico Estudios Diarios por Estado - DONUT
    private DonutChartModel initDonutModel1() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Completados", this.cantidadE("completado", workdate));
        circle1.put("Dictados", this.cantidadE("dictado", workdate));
        circle1.put("Agendados", this.cantidadE("agendado", workdate));
        circle1.put("Cancelados", this.cantidadE("cancelado", workdate));
        circle1.put("Finalizados", this.cantidadE("finalizado", workdate));
        model.addCircle(circle1);

        return model;
    }

    //Grafico Estudios Diarios por Modalidad - DONUT
    private DonutChartModel initDonutModel2() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("CR", this.cantidadM("CR", workdate));
        circle1.put("CT", this.cantidadM("CT", workdate));
        circle1.put("MG", this.cantidadM("MG", workdate));
        circle1.put("US", this.cantidadM("US", workdate));
        model.addCircle(circle1);

        return model;
    }

    //Grafico Estudios Diarios por Aseguradora - DONUT
    private DonutChartModel initDonutModel3() {
        DonutChartModel model = new DonutChartModel();

        AseguradorasBean abean = new AseguradorasBean();
        List<Aseguradoras> aseguradoras = abean.getAseguradoras();
        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();

        for (int i = 0; i < aseguradoras.size(); i++) {
            Aseguradoras aseguradora = aseguradoras.get(i);
            circle1.put(aseguradora.getAseguradora().toString(), this.cantidadA(aseguradora.getIdaseguradora(), workdate));
        }
        model.addCircle(circle1);
        return model;
    }

    private DonutChartModel initDonutModel4() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Completados", this.cantidadE("completado", workdate, endDate));
        circle1.put("Dictados", this.cantidadE("dictado", workdate, endDate));
        circle1.put("Agendados", this.cantidadE("agendado", workdate, endDate));
        circle1.put("Cancelados", this.cantidadE("cancelado", workdate, endDate));
        circle1.put("Finalizados", this.cantidadE("finalizado", workdate, endDate));
        model.addCircle(circle1);

        return model;

    }

    private DonutChartModel initDonutModel5() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("CR", this.cantidadM("CR", workdate, endDate));
        circle1.put("CT", this.cantidadM("CT", workdate, endDate));
        circle1.put("MG", this.cantidadM("MG", workdate, endDate));
        circle1.put("US", this.cantidadM("US", workdate, endDate));
        model.addCircle(circle1);

        return model;

    }

    private DonutChartModel initDonutModel6() {
        DonutChartModel model = new DonutChartModel();

        AseguradorasBean abean = new AseguradorasBean();
        List<Aseguradoras> aseguradoras = abean.getAseguradoras();
        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();

        for (int i = 0; i < aseguradoras.size(); i++) {
            Aseguradoras aseguradora = aseguradoras.get(i);
            circle1.put(aseguradora.getAseguradora().toString(), this.cantidadA(aseguradora.getIdaseguradora(), workdate, endDate));
        }
        model.addCircle(circle1);
        return model;

    }

    public int cantidadE(String status, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudiosDia(status, date);
    }

    public int cantidadM(String modalidad, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudiosMod(modalidad, date);
    }

    public int cantidadA(int aseguradora, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudiosAseg(aseguradora, date);
    }

    public int estudiosDia(Date date) {
        if(date != null){
            DashboardDao linkDao = new DashboardDaoImplement();
            return linkDao.cantidadEstudios(date); 
        } else return 0;   
    }

    public int pacientesDia(Date date) {
        if(date != null){
         DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientesDia(date);   
        } else return 0;     
    }

    //METODOS PARA SACAR DATOS POR RANGO DE FECHA
    public int cantidadE(String status, Date date, Date endDate) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudiosDia(status, date, endDate);
    }

    public int cantidadM(String modalidad, Date date, Date endDate) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudiosMod(modalidad, date, endDate);
    }

    public int cantidadA(int aseguradora, Date date, Date endDate) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudiosAseg(aseguradora, date, endDate);
    }

    public int estudiosDia(Date date, Date endDate) {
        DashboardDao linkDao = new DashboardDaoImplement();
        if(endDate!=null){
            return linkDao.cantidadEstudios(date, endDate);
        }else return linkDao.cantidadEstudios(date);
    }

    public int pacientesDia(Date date, Date endDate) {
        DashboardDao linkDao = new DashboardDaoImplement();
        if(endDate!=null){
        return linkDao.cantidadPacientesDia(date, endDate);
        }else return linkDao.cantidadPacientesDia(date);
    }
    //-----------------------------------------------------------------------------------------------------

    public int estudiosTotal() {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudios();
    }

    public int pacientesTotal() {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientes();
    }

    public int referentesTotal() {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadMedicos();
    }

}
