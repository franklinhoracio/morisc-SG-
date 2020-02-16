/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

//package org.primefaces.examples.view;

import dao.ReferentesReportDao;
import dao.ReferentesReportDaoImplement;
import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import model.Medicos;

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
public class ReferentesReportBean implements Serializable {

    /**
     * Creates a new instance of ReferentesReportBean
     */
    
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private Date today;
    private Date initdate;
    private Date finaldate;
    private List<SelectItem> listaReferentes;
    private Medicos referente;
    private int cantidadEstudios;
    private int cantidadEstudiosRx;
    private int cantidadEstudiosMG;
    private int cantidadEstudiosUS;
    private int cantidadEstudiosCT;
    private int cantidadEstudiosRF;
    
    @PostConstruct
    public void init() {
        createBarModels();
        
    }
    
    public Date getInitdate() {
        return initdate;
    }

    public void setInitdate(Date initdate) {
        this.initdate = initdate;
    }
    
    public Date getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(Date finaldate) {
        this.finaldate = finaldate;
    }
    
    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public ReferentesReportBean() {
        this.today = new Date();
        this.initdate = new Date();
        this.finaldate = new Date();
        referente = new Medicos();
        cantidadEstudios = 0;
        cantidadEstudiosRx = 0;
        cantidadEstudiosMG = 0;
        cantidadEstudiosCT = 0;
        cantidadEstudiosUS = 0;
        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
    
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);

        model.addSeries(boys);
        model.addSeries(girls);
        
        return model;
    }
    
    public void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
    }
    
    private void createBarModel() {
        barModel = initBarModel();
        
        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
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
    
    public List<SelectItem> getListaReferentes() {
        this.listaReferentes = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Medicos> m = linkDao.mostrarMedicos();
        listaReferentes.clear();

        for (Medicos medicos : m) {

            if (medicos.getTipo().equals("referente")) {

                SelectItem medicoItem = new SelectItem(medicos.getIdmedico(), medicos.getApellidomedico() + " , " + medicos.getNombremedico());
                this.listaReferentes.add(medicoItem);
            }
        }
        return listaReferentes;
    }

    public Medicos getReferente() {
        return referente;
    }

    public void setReferente(Medicos referente) {
        this.referente = referente;
    }
    

    public int getCantidadEstudios() {
        return cantidadEstudios;
    }

    public void setCantidadEstudios(int cantidadEstudios) {
        this.cantidadEstudios = cantidadEstudios;
    }

    public int getCantidadEstudiosRx() {
        return cantidadEstudiosRx;
    }

    public void setCantidadEstudiosRx(int cantidadEstudiosRx) {
        this.cantidadEstudiosRx = cantidadEstudiosRx;
    }

    public int getCantidadEstudiosMG() {
        return cantidadEstudiosMG;
    }

    public void setCantidadEstudiosMG(int cantidadEstudiosMG) {
        this.cantidadEstudiosMG = cantidadEstudiosMG;
    }

    public int getCantidadEstudiosUS() {
        return cantidadEstudiosUS;
    }

    public void setCantidadEstudiosUS(int cantidadEstudiosUS) {
        this.cantidadEstudiosUS = cantidadEstudiosUS;
    }

    public int getCantidadEstudiosCT() {
        return cantidadEstudiosCT;
    }

    public void setCantidadEstudiosCT(int cantidadEstudiosCT) {
        this.cantidadEstudiosCT = cantidadEstudiosCT;
    }
    
    public int getCantidadEstudiosRF() {
        return cantidadEstudiosRF;
    }

    public void setCantidadEstudiosRF(int cantidadEstudiosRF) {
        this.cantidadEstudiosRF = cantidadEstudiosRF;
    }
    
    
    public void cantidadEstudiosCalc(){
        ReferentesReportDao linkDao = new ReferentesReportDaoImplement();
        cantidadEstudios = linkDao.cantidadEstudiosReferente(referente.getIdmedico());
        cantidadEstudiosRx  = linkDao.cantidadEstudiosModalidad(referente.getIdmedico(), "CR");
        cantidadEstudiosMG  = linkDao.cantidadEstudiosModalidad(referente.getIdmedico(), "MG");
        cantidadEstudiosUS  = linkDao.cantidadEstudiosModalidad(referente.getIdmedico(), "US");
        cantidadEstudiosCT = linkDao.cantidadEstudiosModalidad(referente.getIdmedico(), "CT");
        cantidadEstudiosRF = linkDao.cantidadEstudiosModalidad(referente.getIdmedico(), "RF");
    }

    
    
}
