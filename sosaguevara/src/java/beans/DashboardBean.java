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
import model.Aseguradoras;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author franklin
 */
@ManagedBean
public class DashboardBean implements Serializable {

    
    @ManagedProperty(value = "#{aseguradorasBean}")
    private AseguradorasBean aseguradorasBean;
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private DonutChartModel donutModel1;
    private DonutChartModel donutModel2;
    private DonutChartModel donutModel3;
    private Date today;
    private Date workdate;

    

    

    @PostConstruct
    public void init() {
        createPieModels();
        createDonutModels();
    }

    public DashboardBean() {
        this.today = new Date();
        this.workdate = new Date();
    }
    
    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
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
    
    public void createPieModels() {
        createPieModel1();
        createPieModel2();
    }
    
    public void createDonutModels() {
        donutModel1 = initDonutModel1();
        donutModel1.setTitle("Estudios Diarios por Status");
        donutModel1.setLegendPosition("w");
        donutModel1.setSeriesColors("477998,4674dd,408dd6,82b4e5,cce4fc");
        donutModel1.setShowDataLabels(true);
        //donutModel1.setLegendCols(3);
        //donutModel1.setLegendRows(3);
        donutModel1.setShowDatatip(true);
         
        donutModel2 = initDonutModel2();
        donutModel2.setTitle("Estudios Diarios por Modalidad");
        donutModel2.setLegendPosition("w");
        //donutModel2.setSliceMargin(5);
        //donutModel2.setShowDataLabels(true);
        donutModel2.setDataFormat("value");
        donutModel2.setSeriesColors("4674dd,408dd6,82b4e5,cce4fc");
        //donutModel2.setShadow(false);
        
        donutModel3 = initDonutModel3();
        donutModel3.setTitle("Estudios Diarios por Aseguradora");
        donutModel3.setLegendPosition("w");
        donutModel3.setSeriesColors("477998,4779c8,82b4e5,009ddc,009df0,02a9ff,02a9ea,6daeff,6daedb,cce3ff,ccdbdc,cce4fc");
    }

    //Grafico Estudios Diarios por Estado - PIE
    public void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel1.set("Completados", this.cantidadE("completado",workdate));
        pieModel1.set("Dictados", this.cantidadE("dictado",workdate));
        pieModel1.set("Agendados", this.cantidadE("agendado",workdate));
        pieModel1.set("Cancelados", this.cantidadE("cancelado",workdate));
        pieModel1.set("Finalizados", this.cantidadE("finalizado",workdate));

        pieModel1.setDiameter(5);
        pieModel1.setSeriesColors( "477998,0xe0184a, 0x3618e0, 0x18e0b1, 0xd5e018");
        
        
        pieModel1.setLegendPosition("w");
    }
    
    //Grafico Estudios Diarios por Modalidad - PIE
    public void createPieModel2() {
        pieModel2 = new PieChartModel();

        pieModel2.set("CR", this.cantidadM("CR",workdate));
        pieModel2.set("CT", this.cantidadM("CT",workdate));
        pieModel2.set("MG", this.cantidadM("MG",workdate));
        pieModel2.set("US", this.cantidadM("US",workdate));

        pieModel2.setLegendPosition("w");
    }
    
    //Grafico Estudios Diarios por Estado - DONUT
    private DonutChartModel initDonutModel1() {
        DonutChartModel model = new DonutChartModel();
         
        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Completados", this.cantidadE("completado",workdate));
        circle1.put("Dictados", this.cantidadE("dictado",workdate));
        circle1.put("Agendados", this.cantidadE("agendado",workdate));
        circle1.put("Cancelados", this.cantidadE("cancelado",workdate));
        circle1.put("Finalizados", this.cantidadE("finalizado",workdate));
        model.addCircle(circle1);
         
        return model;
    }
    
    //Grafico Estudios Diarios por Modalidad - DONUT
    private DonutChartModel initDonutModel2() {
        DonutChartModel model = new DonutChartModel();
         
        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("CR", this.cantidadM("CR",workdate));
        circle1.put("CT", this.cantidadM("CT",workdate));
        circle1.put("MG", this.cantidadM("MG",workdate));
        circle1.put("US", this.cantidadM("US",workdate));
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
            circle1.put(aseguradora.getAseguradora().toString(), this.cantidadA(aseguradora.getIdaseguradora(),workdate));
        }
        /*
        circle1.put("ASEGURADORA1", this.cantidadA(1,workdate));
        circle1.put("ASEGURADORA2", this.cantidadA(2,workdate));
        circle1.put("ASEGURADORA3", this.cantidadA(3,workdate));
        circle1.put("ASEGURADORA4", this.cantidadA(4,workdate));
        circle1.put("ASEGURADORA5", this.cantidadA(5,workdate));
        circle1.put("ASEGURADORA6", this.cantidadA(6,workdate));
        circle1.put("ASEGURADORA7", this.cantidadA(7,workdate));
        circle1.put("ASEGURADORA8", this.cantidadA(8,workdate));
        */
        model.addCircle(circle1);
        /*
        Map<String, Number> circle2 = new LinkedHashMap<String, Number>();
        circle2.put("CR", this.cantidadM("CR",workdate));
        circle2.put("CT", this.cantidadM("CT",workdate));
        circle2.put("MG", this.cantidadM("MG",workdate));
        circle2.put("US", this.cantidadM("US",workdate));
        model.addCircle(circle2);
        */        
      
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
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudios(date);
    }
    
    public int pacientesDia(Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientesDia(date);
    }
 
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
