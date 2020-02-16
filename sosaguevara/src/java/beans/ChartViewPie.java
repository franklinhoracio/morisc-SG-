/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Administrator
 */
@ManagedBean
public class ChartViewPie implements Serializable {

    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private Date today;
    private Date workdate;

    @PostConstruct
    public void init() {
        createPieModels();
    }

    public ChartViewPie() {
        this.today = new Date();
        this.workdate = new Date();
    }
    
    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
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

    private void createPieModels() {
        createPieModel1();
        //createPieModel2();
    }

    public void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel1.set("Completados", this.cantidadE("completado",workdate));
        pieModel1.set("Dictados", this.cantidadE("dictado",workdate));
        pieModel1.set("Agendados", this.cantidadE("agendado",workdate));
        pieModel1.set("Cancelados", this.cantidadE("cancelado",workdate));

        //pieModel1.setTitle("Reporte Diario");
        pieModel1.setLegendPosition("w");
    }

    /*private void createPieModel2() {
        pieModel2 = new PieChartModel();
         
        pieModel2.set("Brand 1", 540);
        pieModel2.set("Brand 2", 325);
        pieModel2.set("Brand 3", 702);
        pieModel2.set("Brand 4", 421);
         
        pieModel2.setTitle("Reporte Diario");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }*/
    public int cantidadE(String status, Date date) {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        System.out.println("EStatus en el bean:" + status);
        return linkDao.cantidadEstudios(status, date);
    }
    
    /*
    public int actualizarChart() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        /*rxinterfaces = linkDao.mostrarInterfaces(workdate);
        
        //rxinterface = new Rxinterface();
        if(rxinterface==null){
         rxinterface = new Rxinterface();   
        }
        return rxinterfaces;
        return linkDao.cantidadEstudios(status);
    }
*/

}
