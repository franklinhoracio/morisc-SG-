/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.AseguradorasDao;
import dao.AseguradorasDaoImplement;
import dao.DashboardDao;
import dao.DashboardDaoImplement;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import model.Aseguradoras;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author franklin
 */
@ManagedBean
public class ChartViewBar implements Serializable {

    private BarChartModel barModel;
    private BarChartModel cantidadModalidades;
    private HorizontalBarChartModel horizontalBarModel;
    private HorizontalBarChartModel horizontalAseguradoras;
    private Date today;
    private Date workdate;

    @PostConstruct
    public void init() {
        createBarModels();
    }

    public ChartViewBar() {
        this.today = new Date();
        this.workdate = new Date();
    }
    
    

    public BarChartModel getBarModel() {
        return barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    public BarChartModel getCantidadModalidades() {
        return cantidadModalidades;
    }

    public HorizontalBarChartModel getHorizontalAseguradoras() {
        return horizontalAseguradoras;
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

    public void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
        createCantidadModalidades();
        createCantidadAseguradoras();
    }

    private void createBarModel() {
        barModel = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("M");
        boys.set(" ", this.cantidadPacientesGenero("M", workdate));

        ChartSeries girls = new ChartSeries();
        girls.setLabel("F");
        girls.set(" ", this.cantidadPacientesGenero("F", workdate));
        
        ChartSeries others = new ChartSeries();
        others.setLabel("O");
        others.set(" ", this.cantidadPacientesGenero("O", workdate));

        barModel.addSeries(boys);
        barModel.addSeries(girls);
        barModel.addSeries(others);

        barModel.setTitle("TOTAL DE PACIENTES");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Genero");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("");
        yAxis.setMin(0);
        yAxis.setMax(25);
    }

    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);

        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);

        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Births");
        xAxis.setMin(0);
        xAxis.setMax(200);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Gender");
    }

    private void createCantidadModalidades() {
        cantidadModalidades = new BarChartModel();

        ChartSeries girls = new ChartSeries();
        girls.setLabel("F");
        girls.set("CR", this.cantidadPacientesModalidad("CR", "F", workdate));
        girls.set("CT", this.cantidadPacientesModalidad("CT", "F", workdate));
        girls.set("MG", this.cantidadPacientesModalidad("MG", "F", workdate));
        girls.set("RF", this.cantidadPacientesModalidad("RF", "F", workdate));
        girls.set("US", this.cantidadPacientesModalidad("US", "F", workdate));

        ChartSeries boys = new ChartSeries();
        boys.setLabel("M");
        boys.set("CR", this.cantidadPacientesModalidad("CR", "M", workdate));
        boys.set("CT", this.cantidadPacientesModalidad("CT", "M", workdate));
        boys.set("MG", this.cantidadPacientesModalidad("MG", "M", workdate));
        boys.set("RF", this.cantidadPacientesModalidad("RF", "M", workdate));
        boys.set("US", this.cantidadPacientesModalidad("US", "M", workdate));

        ChartSeries total = new ChartSeries();
        total.setLabel("Σ");
        total.set("CR", this.cantidadPacientesModalidadTotal("CR", workdate));
        total.set("CT", this.cantidadPacientesModalidadTotal("CT", workdate));
        total.set("MG", this.cantidadPacientesModalidadTotal("MG", workdate));
        total.set("RF", this.cantidadPacientesModalidadTotal("RF", workdate));
        total.set("US", this.cantidadPacientesModalidadTotal("US", workdate));

        cantidadModalidades.addSeries(boys);
        cantidadModalidades.addSeries(girls);
        cantidadModalidades.addSeries(total);

        cantidadModalidades.setTitle("MODALIDADES");
        cantidadModalidades.setLegendPosition("ne");

        Axis xAxis = cantidadModalidades.getAxis(AxisType.X);
        xAxis.setLabel("Modalidades");

        Axis yAxis = cantidadModalidades.getAxis(AxisType.Y);
        yAxis.setLabel("Pacientes");
        yAxis.setMin(0);
        yAxis.setMax(25);

    }

    private void createCantidadAseguradoras() {
        horizontalAseguradoras = new HorizontalBarChartModel();

        ChartSeries porcentajes = new ChartSeries();
        //girls.setLabel("Girls");
        
        List<Aseguradoras> lista = listaAseguradoras();
        System.out.println("SE ADQUIERE LISTA DE ASEGURADORAS");
        System.out.println("size: "+lista.size());
        
        int total = cantidadEstudiosTotal(workdate);
        System.out.println("TOTAL DE ESTUDIOS: "+total);
        for (int i = 0; i < lista.size(); i++) {
            //porcentajes.set("2004", 52);
            if(total==0){
                porcentajes.set(lista.get(i).getAseguradora(), 0);
            }else {
                double estudiosAseguradora = (cantidadPacientesAseguradoras(lista.get(i).getIdaseguradora(), workdate));
                System.out.println("PORCENTAJE CALCULADO: "+estudiosAseguradora/total*100);
                porcentajes.set(lista.get(i).getAseguradora(), estudiosAseguradora/total*100);
            }
        }

        horizontalAseguradoras.addSeries(porcentajes);

        horizontalAseguradoras.setTitle("Porcentajes por Aseguradora");
        //horizontalBarModel.setLegendPosition("e");
        horizontalAseguradoras.setStacked(true);

        Axis xAxis = horizontalAseguradoras.getAxis(AxisType.X);
        xAxis.setLabel("Porcentajes");
        xAxis.setMin(0);
        xAxis.setMax(100);

        Axis yAxis = horizontalAseguradoras.getAxis(AxisType.Y);
        //yAxis.setLabel("Gender");

    }

    public int cantidadPacientesModalidad(String modalidad, String genero, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientesModalidad(modalidad, genero, date);
    }

    public int cantidadPacientesModalidadTotal(String modalidad, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientesModalidad(modalidad, date);
    }
    
    public int cantidadPacientesAseguradoras(int aseguradora, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientesAseguradora(aseguradora, date);
    }
    
    public int cantidadPacientesGenero(String genero, Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadPacientesGenero(genero, date);
    }

    public int cantidadEstudiosTotal(Date date) {
        DashboardDao linkDao = new DashboardDaoImplement();
        return linkDao.cantidadEstudios(date);
    }

    public List<Aseguradoras> listaAseguradoras(){
        AseguradorasDao linkDao = new AseguradorasDaoImplement();
        return linkDao.mostrarAseguradoras();
    }

}
