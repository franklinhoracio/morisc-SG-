/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.CitasDao;
import dao.CitasDaoImplement;
import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import model.Citas;
import model.Usuarios;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import util.MyScheduledEvent;

/**
 *
 * @author frodriguez
 */
@ManagedBean
@SessionScoped
public class CitasBean implements Serializable {

    private ScheduleModel eventModel;
    private Citas cita;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private List<SelectItem> listaRadiologos;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        CitasDao linkDao = new CitasDaoImplement();
        List<Citas> citas = linkDao.mostrarCitas();

        if (citas != null) {
            for (int i = 0; i < citas.size(); i++) {
                String theme = "default";
                String descriptor = "";
                //Seleccion de tema por medico
                switch (citas.get(i).getIduser().intValue()) {
                    case 5:
                        theme = "beatriz";
                        descriptor = "Dra Guevara";
                        break;
                    case 6:
                        theme = "sosa";
                        descriptor = "Dra Sosa";
                        break;
                    case 7:
                        theme = "fernando";
                        descriptor = "Dr Fernando";
                        break;
                    case 8:
                        theme = "montalvo";
                        descriptor = "Dr Montalvo";
                        break;
                    case 15:
                        theme = "alfaro";
                        descriptor = "Dra Alfaro";
                        break;
                        
                    case 99:
                        theme = "disabled";
                        descriptor = "CANCELADO";
                        break;    
                }

                ScheduleEvent test = new MyScheduledEvent(citas.get(i).getDescripcion()+"\n"+descriptor, citas.get(i).getHorainicio(), citas.get(i).getHorafin(), citas.get(i), theme);
                //eventModel.addEvent(new DefaultScheduleEvent(citas.get(i).getDescripcion(), citas.get(i).getHorainicio(), citas.get(i).getHorafin(), citas.get(i)));
                eventModel.addEvent(test);

            }
        }
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public Citas getCita() {
        return cita;
    }

    public void setCita(Citas cita) {
        this.cita = cita;
    }

    public void addEvent() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cita creada!", "");
        if (event.getId() == null) {
            System.out.println("\n \n ID DEL EVENTO NUEVA CITA");
            System.out.println("\n" + event.getId());
            eventModel.addEvent(event);
            cita = (Citas) event.getData();
            CitasDao linkDao = new CitasDaoImplement();
            //.setDescripcion(event.getTitle());
            cita.setDescripcion(cita.getDescripcion());
            cita.setHorainicio(event.getStartDate());
            cita.setHorafin(event.getEndDate());
            linkDao.insertarCita(cita);
            cita = new Citas();
            init();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cita creada!", "");
            //addMessage(message);
        } else {
            eventModel.updateEvent(event);
            cita = (Citas) event.getData();
            CitasDao linkDao = new CitasDaoImplement();
            //cita.setDescripcion(event.getTitle());
            cita.setDescripcion(cita.getDescripcion());
            cita.setHorainicio(event.getStartDate());
            cita.setHorafin(event.getEndDate());
            linkDao.modificarCita(cita);
            //cita = new Citas();
            init();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico la cita", "");
            //addMessage(message);
        }
        event = new DefaultScheduleEvent();
        addMessage(message);
    }

    public void onEventSelect(SelectEvent selectEvent) {
        cita = (Citas) event.getData();
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        //agregar 30 minutos a la fecha final
        Calendar c = Calendar.getInstance();
        c.setTime((Date) selectEvent.getObject());
        c.add(Calendar.MINUTE, 30);
        Date fechaFinal = c.getTime();

        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), fechaFinal, new Citas());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {

        System.out.println("\n MOVIMIENTO DE EVENTO");
        cita = (Citas) event.getScheduleEvent().getData();
        CitasDao linkDao = new CitasDaoImplement();
        cita.setDescripcion(event.getScheduleEvent().getTitle());
        System.out.println("TITULO: " + event.getScheduleEvent().getTitle());
        cita.setHorainicio(event.getScheduleEvent().getStartDate());
        System.out.println("HORA INICIO: " + event.getScheduleEvent().getStartDate());
        cita.setHorafin(event.getScheduleEvent().getEndDate());
        System.out.println("HORA FINAL: " + event.getScheduleEvent().getEndDate());
        linkDao.modificarCita(cita);

    }

    public void onEventResize(ScheduleEntryResizeEvent event) {

        cita = (Citas) event.getScheduleEvent().getData();
        CitasDao linkDao = new CitasDaoImplement();
        System.out.println("\n RESIZE EVENT: ");
        cita.setDescripcion(event.getScheduleEvent().getTitle());
        System.out.println("TITULO: " + event.getScheduleEvent().getTitle());
        cita.setHorainicio(event.getScheduleEvent().getStartDate());
        System.out.println("HORA INICIO: " + event.getScheduleEvent().getStartDate());
        cita.setHorafin(event.getScheduleEvent().getEndDate());
        System.out.println("HORA FINAL: " + event.getScheduleEvent().getEndDate());
        linkDao.modificarCita(cita);

    }

    public void arribar() throws IOException {
        cita = (Citas) event.getData();
        CitasDao linkDao = new CitasDaoImplement();
        linkDao.arrivarCita(cita);
        //eventModel.deleteEvent(event);
        FacesContext.getCurrentInstance().getExternalContext().redirect("smEstudiosWk.xhtml");
    }

    public void cancelar() {
        cita = (Citas) event.getData();
        CitasDao linkDao = new CitasDaoImplement();
        linkDao.desactivarCita(cita);
        eventModel.deleteEvent(event);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<SelectItem> getListaRadiologos() {
        this.listaRadiologos = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Usuarios> m = linkDao.mostrarRadiologos();
        listaRadiologos.clear();

        for (Usuarios medicos : m) {
            Long i = medicos.getIduser();
            SelectItem medicoItem = new SelectItem(i.intValue(), medicos.getLastnameuser() + " , " + medicos.getFirstnameuser());
            this.listaRadiologos.add(medicoItem);
        }
        return listaRadiologos;
    }

}
