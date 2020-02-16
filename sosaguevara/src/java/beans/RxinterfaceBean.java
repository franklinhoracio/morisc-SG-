/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.MedicosDao;
import dao.MedicosDaoImplement;
import dao.PacienteDao;
import dao.PacienteDaoImplement;
import dao.PlantillasDao;
import dao.PlantillasDaoImplement;
import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import dao.UsuariosDao;
import dao.UsuariosDaoImplement;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import model.Aseguradoras;
import model.Medicos;
import model.Paciente;
import model.Plantillas;
import model.Proyecciones;
import model.Rxinterface;
import model.Usuarios;
import net.sf.jasperreports.engine.JRException;
import org.jsoup.Jsoup;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.xml.sax.SAXException;
import util.DatosDui;
import util.ReporSobre;
import util.Reporte;
import util.Reporte1;
import util.Reporte2;
import util.Reporte3;
import util.Reporte4;
import util.ReportePreliminar;
import util.Reporteold;
import util.SendMail;
import util.Util;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class RxinterfaceBean implements Serializable {

    @ManagedProperty(value = "#{pacienteBean}")
    private PacienteBean pacienteBean;

    private Rxinterface rxinterface;
    private Rxinterface rxinterface2;
    private Plantillas plantilla;
    private List<Rxinterface> rxinterfaces;
    private List<Rxinterface> rxinformes;
    private List<Rxinterface> rxestudios;
    private List<Rxinterface> rxfinalizados;
    private List<Rxinterface> rxficha;
    private List<SelectItem> listaInformes;
    private List<SelectItem> listaFinalizados;
    private List<SelectItem> listaPacientes;
    private List<SelectItem> listaReferentes;
    private List<SelectItem> listaProyecciones;
    private List<SelectItem> listaAseguradoras;
    private List<SelectItem> listaRadiologos;
    private List<SelectItem> listaPlantillas;
    private List<String> listaProyeccionesSel;
    private List<String> listaInformeSel;
    private DualListModel<Proyecciones> estudiosPickList;
    private Date workdate;
    private Date infodate;
    private Date finadate;
    private Date today;
    private String estudiosel;
    private boolean switchboton;
    private String subject;
    private String correo;
    private String bcrDui;

    public RxinterfaceBean() {
        rxinterface = new Rxinterface();
        plantilla = new Plantillas();
        workdate = new Date();
        infodate = new Date();
        finadate = new Date();
        today = new Date();
        switchboton = true;
        estudiosPickList = fillestudiosPickList();
        bcrDui = "";
    }

    public Rxinterface getRxinterface() {
        return rxinterface;
    }

    public void setRxinterface(Rxinterface rxinterface) {
        this.rxinterface = rxinterface;
    }

    public Date getWorkdate() {
        return workdate;
    }

    public void setWorkdate(Date workdate) {
        this.workdate = workdate;
    }

    public Date getInfodate() {
        return infodate;
    }

    public void setInfodate(Date infodate) {
        this.infodate = infodate;
    }

    public Date getFinadate() {
        return finadate;
    }

    public void setFinadate(Date finadate) {
        this.finadate = finadate;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public List<Rxinterface> getRxinterfaces() {

        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
        return rxinterfaces;
    }

    public List<Rxinterface> getRxinformes() {

        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxinformes = linkDao.mostrarInformes(infodate);

        return rxinformes;
    }

    public List<SelectItem> getListaPacientes() {
        this.listaPacientes = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Paciente> p = linkDao.mostrarPacientes();
        listaPacientes.clear();

        for (Paciente pacientes : p) {
            SelectItem pacienteItem = new SelectItem(pacientes.getIdPaciente(), pacientes.getLastnamepatient() + " , " + pacientes.getNamepatient());
            this.listaPacientes.add(pacienteItem);
        }
        return listaPacientes;
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

    public List<String> listaReferentesFilter(String query) {
        List<String> results = new ArrayList<>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Medicos> m = linkDao.mostrarMedicos();
        results.clear();
        for (Medicos medicos : m) {

            if (medicos.getApellidomedico().toLowerCase().startsWith(query.toLowerCase())) {
                results.add(Long.toString(medicos.getIdmedico()));
            }
        }
        return results;
    }

    public void filtrarPaciente(String id) {
        PacienteDao linkDao = new PacienteDaoImplement();
        Paciente p = linkDao.mostrarPacienteId(id);
        if (p != null) {
            this.rxinterface.setPaciente(p);
        }
        this.rxinterface.setReport("");
    }

    public void filtrarPacienteDui(String dui) {
        PacienteDao linkDao = new PacienteDaoImplement();
        List<Paciente> p = linkDao.mostrarPacientesDui(dui);

        if (p.size() == 1) {
            this.pacienteBean.setPaciente((Paciente) p.get(0));
        } else {
            this.pacienteBean.setPaciente(new Paciente());
            this.pacienteBean.getPaciente().setExtid(dui);
        }
    }

    public void filtrarMedicoJunta(String numerojunta) {
        MedicosDao linkDao = new MedicosDaoImplement();
        Medicos referente = linkDao.obtenerNumeroJunta(numerojunta);
        if (referente != null) {
            this.rxinterface.setMedicos(referente);
        }
    }

    public void filtrarPacientePass(String passport) {
        PacienteDao linkDao = new PacienteDaoImplement();
        List<Paciente> p = linkDao.mostrarPacientesPass(passport);

        if (p.size() == 1) {
            this.pacienteBean.setPaciente((Paciente) p.get(0));
        } else {
            this.pacienteBean.setPaciente(new Paciente());
            this.pacienteBean.getPaciente().setPassport(passport);
        }
    }

    public void filtrarReferente(Long idreferente) {
        MedicosDao linkDao = new MedicosDaoImplement();
        Object referente = linkDao.mostrarReferenteId(idreferente);
        if (idreferente != 0) {
            this.rxinterface.setMedicos((Medicos) referente);
        } else {
            this.rxinterface.setMedicos(new Medicos());
        }

    }

    public List<SelectItem> getListaProyecciones() {
        this.listaProyecciones = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Proyecciones> e = linkDao.mostrarProyecciones();
        listaProyecciones.clear();

        for (Proyecciones proyecciones : e) {
            SelectItem proyeccionesItem = new SelectItem(proyecciones.getCpt(), proyecciones.getModality() + " " + proyecciones.getProc());
            this.listaProyecciones.add(proyeccionesItem);
        }
        return listaProyecciones;
    }

    public List<SelectItem> getListaInformes() {
        this.listaInformes = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Rxinterface> e = linkDao.mostrarInformesMul(this.rxinterface.getPaciente(), this.rxinterface.getDate());
        listaInformes.clear();

        for (Rxinterface informes : e) {
            SelectItem informesItem = new SelectItem(informes.getAccnumber(), informes.getProyecciones().getProc());
            this.listaInformes.add(informesItem);
        }
        return listaInformes;
    }

    public List<SelectItem> getListaFinalizados() {
        this.listaFinalizados = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Rxinterface> e = linkDao.mostrarFinalizadosMul(this.rxinterface.getPaciente(), this.rxinterface.getDate());
        listaFinalizados.clear();

        for (Rxinterface informes : e) {
            SelectItem informesItem = new SelectItem(informes.getAccnumber(), informes.getProyecciones().getProc());
            this.listaFinalizados.add(informesItem);
        }
        return listaFinalizados;
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

    public List<SelectItem> getListaRadiologos() {
        this.listaRadiologos = new ArrayList<SelectItem>();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        List<Usuarios> m = linkDao.mostrarRadiologos();
        listaRadiologos.clear();

        for (Usuarios medicos : m) {

            SelectItem medicoItem = new SelectItem(medicos.getIduser(), medicos.getLastnameuser() + " , " + medicos.getFirstnameuser());
            this.listaRadiologos.add(medicoItem);
        }
        return listaRadiologos;
    }

    public List<Rxinterface> actualizarWorklist() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
        //rxinterface = new Rxinterface();
        if (rxinterface == null) {
            rxinterface = new Rxinterface();
        }
        return rxinterfaces;
    }

    public List<Rxinterface> pollWorklist() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
        if (rxinterface == null) {
            rxinterface = new Rxinterface();
        }
        return rxinterfaces;
    }

    public String initWorklist() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
        if (rxinterface == null) {
            rxinterface = new Rxinterface();
        }
        return "mWorklist";
    }

    public List<Rxinterface> actualizarInformes() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxinformes = linkDao.mostrarInformes(infodate);
        if (rxinterface == null) {
            rxinterface = new Rxinterface();
        }
        return rxinformes;
    }

    public List<Rxinterface> actualizarFinalizados() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxfinalizados = linkDao.mostrarFinalizados(finadate);
        if (rxinterface == null) {
            rxinterface = new Rxinterface();
        }
        return rxfinalizados;
    }

    //Registrar estudio sin parametros.
    public void nuevoEstudio() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.insertarInterface(rxinterface);
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
    }

    public void nuevoGargo() {
        rxinterface.setPaciente(pacienteBean.getPaciente());
        this.nuevosEstudios();
    }

    public void inicializarBcr(ActionEvent actionevent) {
        this.bcrDui = "";
    }

    public void inicializarEstudio(ActionEvent actionevent) {
        rxinterface = new Rxinterface();
        workdate = new Date();
        infodate = new Date();
        today = new Date();
    }

    public void inicializarEstudioWk(ActionEvent actionevent) {
        rxinterface = new Rxinterface();
        this.pacienteBean.setPaciente(new Paciente());
        this.pacienteBean.setEdad(0);
        this.pacienteBean.setSwitchdob(true);
        listaProyeccionesSel = new ArrayList<String>();
        workdate = new Date();
        infodate = new Date();
        today = new Date();
        estudiosPickList.getTarget().clear();
    }

    public void inicializarTarget(ActionEvent actionevent) {
        rxinterface = new Rxinterface();
        estudiosPickList.getTarget().clear();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        estudiosPickList.setSource(linkDao.mostrarProyecciones());
        this.pacienteBean = new PacienteBean();
        this.pacienteBean.setPaciente(new Paciente());
        this.pacienteBean.setEdad(0);
        this.pacienteBean.setSwitchdob(true);
        workdate = new Date();
        infodate = new Date();
        today = new Date();

    }

    public void inicializarPick(ActionEvent actionevent) {
        rxinterface = new Rxinterface();
        estudiosPickList = fillestudiosPickList();
        this.pacienteBean.setPaciente(new Paciente());
    }

    public void modificarEstudio() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.modificarInterface(rxinterface);
    }

    public void cancelarEstudio() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.cancelarInterface(rxinterface);
    }

    public void completarEstudio() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.completarInterface(rxinterface);
    }

    public void finalizaeEstudio() {
        if (rxinterface.getStatus().equals("completado") || rxinterface.getStatus().equals("edicion")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo finalizar", "El estudio debe estar dictado!"));
        } else {
            RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
            linkDao.finalizarInterface(rxinterface);
        }
    }

    public void registrarInforme() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.insertarInforme(rxinterface);
        rxinterface.setStatus("dictado");
    }

    public void registrarFinalizado() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.insertarFinalizado(rxinterface);
        rxinterface.setStatus("finalizado");
    }

    public void pacsFinalizado() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.enviarFinalizado(rxinterface);
        rxinterface.setStatus("finalizado");
    }

    public void registrarInformes() {
        Rxinterface estudios = new Rxinterface();
        System.out.println("Numero de acc del listainformesel: " + listaInformeSel.get(0));
        rxinterface.setStatus("dictado");
        for (int i = 0; i < listaInformeSel.size(); i++) {
            estudios.setAccnumber(Long.parseLong(listaInformeSel.get(i)));
            //estudios.setStatus("dictado");
            estudios.setReport(rxinterface.getReport());
            estudios.setMedicos(rxinterface.getMedicos());
            estudios.setUsuarios(rxinterface.getUsuarios());
            estudios.setTipopago(rxinterface.getTipopago());
            estudios.setAseguradoras(rxinterface.getAseguradoras());
            RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
            linkDao.insertarInforme(estudios);
            //rxinterface.setStatus("dictado");
        }

    }

    public void autosaveInforme() {
        if (rxinterface.getStatus().equals("completado")) {

            rxinterface.setStatus("edicion");
        }
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        linkDao.edicionInforme(rxinterface, rxinterface.getStatus());

    }

    public PacienteBean getPacienteBean() {
        return pacienteBean;
    }

    public void setPacienteBean(PacienteBean pacienteBean) {
        this.pacienteBean = pacienteBean;
    }

    //Metodo para invocar el reporte y enviarle los parametros
    public String verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        System.out.println("BEAN RXINTERFACEBEAN METODO VER REPORTE");
        if (this.rxinterface != null) {
            Reporte2 informeRx = new Reporte2();
            informeRx.getReporte((int) this.rxinterface.getAccnumber());
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            System.out.println("ESTUDIO NULO");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo imprimir", "Debe seleccionar un estudio!"));
        }
        return "mLectura";

    }

    public String verReporteMemb() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        Reporte informeRx = new Reporte();
        informeRx.getReporte(this.rxinterface.getAccnumber(), "membrete.jasper");
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    public String verReporteMembrete() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        Reporte1 informeRx = new Reporte1();
        informeRx.getReporte(this.rxinterface.getAccnumber());
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    public String verReporteMembreteold() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        Reporteold informeRx = new Reporteold();
        informeRx.getReporte(this.rxinterface.getAccnumber());
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    public String verReporteMulti() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        Reporte3 informeRx = new Reporte3();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        String nombrestudios = "";
        System.out.println("tamaño de la lista seleccionada" + listaInformeSel.size());
        for (int i = 0; i < listaInformeSel.size();) {
            System.out.println("primer i " + i);
            if (i == 0) {
                nombrestudios = linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
            } else {
                nombrestudios = nombrestudios + " , " + linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
            }
            i++;
        }

        informeRx.getReporte(this.rxinterface.getAccnumber(), nombrestudios);
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    public String verReportePdfMulti() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        Reporte4 informeRx = new Reporte4();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        String nombrestudios = "";
        System.out.println("tamaño de la lista seleccionada" + listaInformeSel.size());
        for (int i = 0; i < listaInformeSel.size();) {
            System.out.println("primer i " + i);
            if (i == 0) {
                nombrestudios = linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
            } else {
                nombrestudios = nombrestudios + " , " + linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
            }
            i++;
        }

        informeRx.getReporte(this.rxinterface.getAccnumber(), nombrestudios);
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    public String verReporteSobre() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, IOException {

        ReporSobre informeRx = new ReporSobre();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        String nombrestudios = "";
        System.out.println("tamaño de la lista seleccionada" + listaInformeSel.size());
        for (int i = 0; i < listaInformeSel.size();) {
            System.out.println("primer i " + i);
            if (i == 0) {
                nombrestudios = linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
            } else {
                nombrestudios = nombrestudios + " , " + linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
            }
            i++;
        }

        informeRx.getReporte(this.rxinterface.getAccnumber(), nombrestudios);
        FacesContext.getCurrentInstance().responseComplete();
        return "mLectura";

    }

    //Registrar varios estudios sin parametros
    public void nuevosEstudios() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = listaProyeccionesSel.size();

        Proyecciones newi = new Proyecciones();

        for (int i = 0; i < conteo; i++) {
            newi.setCpt(Long.parseLong(listaProyeccionesSel.get(i)));
            rxinterface.setProyecciones(newi);
            linkDao.insertarInterface(rxinterface);
        }
        switchboton = true;
        listaProyeccionesSel.clear();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
    }

    public void nuevosEstudios2() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = listaProyeccionesSel.size();
        rxestudios = new ArrayList<Rxinterface>();

        for (int i = 0; i < conteo; i++) {
            Proyecciones newi = new Proyecciones();
            rxinterface2 = new Rxinterface();
            newi.setCpt(Long.parseLong(listaProyeccionesSel.get(i)));
            rxinterface2.setProyecciones(newi);
            rxinterface2.setCantidad(1);
            rxinterface2.setTipopago(rxinterface.getTipopago());
            rxinterface2.setPaciente(rxinterface.getPaciente());
            rxinterface2.setMedicos(rxinterface.getMedicos());
            rxinterface2.setAseguradoras(rxinterface.getAseguradoras());
            rxinterface2.setInstitucion(rxinterface.getInstitucion());
            rxinterface2.setDatestudy(rxinterface.getDatestudy());
            rxinterface2.setPagado(rxinterface.getPagado());

            rxestudios.add(rxinterface2);
        }
        switchboton = true;
        listaProyeccionesSel.clear();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
    }

    public void nuevosEstudios3() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = rxestudios.size();

        for (int i = 0; i < conteo; i++) {
            int cantidad = rxestudios.get(i).getCantidad();
            for (int j = 0; j < cantidad; j++) {
                linkDao.insertarInterface(rxestudios.get(i));
            }
        }
        switchboton = true;
        listaProyeccionesSel.clear();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void historicoEstudio() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = rxestudios.size();
        for (int i = 0; i < conteo; i++) {
            int cantidad = rxestudios.get(i).getCantidad();
            for (int j = 0; j < cantidad; j++) {
                linkDao.insertarInterfaceHist(rxestudios.get(i));
            }
        }
        switchboton = true;
        listaProyeccionesSel.clear();
        //rxinterfaces = linkDao.mostrarInterfaces(workdate);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void verificarPacientes() throws IOException {
        Paciente paciente = this.pacienteBean.getPaciente();
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        //EVALUAR SI EXISTE PACIENTE 
        if (this.pacienteBean.getPaciente().getIdPaciente() == null) {
            // SI NO HAY PACIENTE BUSCAR COINCIDENCIAS ENTRE NOMBRES Y FECHA DE NACIMIENTO, SI EXISTEN REDIRECCIONA A PAGINA DE CONFIRMACION         
            if (linkDao.mostrarPacientesTimestamp(paciente.getNamepatient(), paciente.getLastnamepatient(), paciente.getDatebirth()).isEmpty()) {
                nuevosEstudios4();
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("smConfirmarPac.xhtml");
            }
        } else {
            nuevosEstudios4();
        }
    }

    //INSERTA NUEVOS ESTUDIOS CREANDO PACIENTE Y CREANDO REFERENTE
    public void nuevosEstudios4() throws IOException {

        Paciente paciente = this.pacienteBean.getPaciente();

        //EVALUAR SI EXISTE PACIENTE 
        if (this.pacienteBean.getPaciente().getIdPaciente() == null) {
            //BUSCAR COINCIDENCIAS ENTRE NOMBRES Y FECHA DE NACIMIENTO

            PacienteDao linkDaoPac = new PacienteDaoImplement();
            if (paciente.getDatebirth() == null) {
                paciente.setDatebirth(pacienteBean.dobcalculated(this.pacienteBean.getEdad()));
            }
            paciente.setIdPaciente(linkDaoPac.insertarPaciente(paciente));

        }

        //CREAR REFERENTE
        if (rxinterface.getMedicos().getIdmedico() == 0 && !rxinterface.getMedicos().getNombremedico().equals("") && !rxinterface.getMedicos().getApellidomedico().equals("")) {
            MedicosDao linkDao = new MedicosDaoImplement();
            rxinterface.getMedicos().setTipo("referente");
            linkDao.insertarMedico(rxinterface.getMedicos());
            rxinterface.getMedicos().setIdmedico(linkDao.obtenerUltimoMedico());

        }

        //Crear lista <Rxinterface> de los elementos seleccionados:
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = listaProyeccionesSel.size();
        rxestudios = new ArrayList<Rxinterface>();

        for (int i = 0; i < conteo; i++) {
            Proyecciones newi = new Proyecciones();
            rxinterface2 = new Rxinterface();
            newi.setCpt(Long.parseLong(listaProyeccionesSel.get(i)));
            rxinterface2.setProyecciones(newi);
            rxinterface2.setCantidad(1);
            rxinterface2.setTipopago(rxinterface.getTipopago());
            rxinterface2.setPaciente(paciente);
            rxinterface2.setMedicos(rxinterface.getMedicos());
            rxinterface2.setAseguradoras(rxinterface.getAseguradoras());
            rxinterface2.setInstitucion(rxinterface.getInstitucion());
            rxestudios.add(rxinterface2);
        }

        //Loop para insertar en la base haciendo repeticiones segun se define en "cantidad"
        conteo = rxestudios.size();

        for (int i = 0; i < conteo; i++) {
            int cantidad = rxestudios.get(i).getCantidad();
            for (int j = 0; j < cantidad; j++) {
                linkDao.insertarInterface(rxestudios.get(i));
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        switchboton = true;
        listaProyeccionesSel.clear();
        paciente = new Paciente();

        FacesContext.getCurrentInstance().getExternalContext().redirect("mWorklist.xhtml");
    }

    public void nuevosEstudiosPickList() throws IOException {

        Paciente paciente = this.pacienteBean.getPaciente();

        //EVALUAR SI EXISTE ID DE PACIENTE 
        if (this.pacienteBean.getPaciente().getIdPaciente() == null) {
            //SI NO TIENE FECHA DE NACIMIENTO LA CALCULA SEGUN LA CANTIDAD DE ANNOS DIGITADA

            PacienteDao linkDaoPac = new PacienteDaoImplement();
            if (paciente.getDatebirth() == null) {
                paciente.setDatebirth(pacienteBean.dobcalculated(this.pacienteBean.getEdad()));
            }

            //PENDIENTE VERIFICAR PACIENTES YA CREADOS PARA BCR SCANNER
            /*if(bcrDui!="" || bcrDui==null){
                
            }*/
            //CREACION DEL PACIENTE
            paciente.setIdPaciente(linkDaoPac.insertarPaciente(paciente));

        }

        //CREAR REFERENTE
        if (rxinterface.getMedicos().getIdmedico() == 0 && !rxinterface.getMedicos().getNombremedico().equals("") && !rxinterface.getMedicos().getApellidomedico().equals("")) {
            MedicosDao linkDao = new MedicosDaoImplement();
            rxinterface.getMedicos().setTipo("referente");
            linkDao.insertarMedico(rxinterface.getMedicos());
            rxinterface.getMedicos().setIdmedico(linkDao.obtenerUltimoMedico());

        }
        //rxestudios = this.estudiosPickList.getTarget();
        //Loop para insertar en la base haciendo repeticiones segun se define en "cantidad"
        //Crear lista <Rxinterface> de los elementos seleccionados:
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = this.estudiosPickList.getTarget().size();
        rxestudios = new ArrayList<Rxinterface>();

        for (int i = 0; i < conteo; i++) {
            //Proyecciones newi = new Proyecciones();
            rxinterface2 = new Rxinterface();
            //newi.setCpt(Long.parseLong(listaProyeccionesSel.get(i)));
            rxinterface2.setProyecciones(this.estudiosPickList.getTarget().get(i));
            rxinterface2.setCantidad(1);
            rxinterface2.setTipopago(rxinterface.getTipopago());
            rxinterface2.setPaciente(paciente);
            rxinterface2.setMedicos(rxinterface.getMedicos());
            rxinterface2.setAseguradoras(rxinterface.getAseguradoras());
            rxinterface2.setInstitucion(rxinterface.getInstitucion());
            rxinterface2.setPagado(rxinterface.getPagado());
            rxestudios.add(rxinterface2);
        }

        //Loop para insertar en la base haciendo repeticiones segun se define en "cantidad"
        conteo = rxestudios.size();

        for (int i = 0; i < conteo; i++) {
            int cantidad = rxestudios.get(i).getCantidad();
            for (int j = 0; j < cantidad; j++) {
                linkDao.insertarInterface(rxestudios.get(i));
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        switchboton = true;
        //listaProyeccionesSel.clear();
        paciente = new Paciente();

        FacesContext.getCurrentInstance().getExternalContext().redirect("mWorklist.xhtml");
    }

    public void nuevosEstudiosMod() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        int conteo = this.estudiosPickList.getTarget().size();
        rxestudios = new ArrayList<Rxinterface>();

        for (int i = 0; i < conteo; i++) {

            rxinterface2 = new Rxinterface();
            rxinterface2.setProyecciones(this.estudiosPickList.getTarget().get(i));
            rxinterface2.setCantidad(1);
            rxinterface2.setTipopago(rxinterface.getTipopago());
            rxinterface2.setPaciente(rxinterface.getPaciente());
            rxinterface2.setMedicos(rxinterface.getMedicos());
            rxinterface2.setAseguradoras(rxinterface.getAseguradoras());
            rxinterface2.setInstitucion(rxinterface.getInstitucion());
            rxinterface2.setDatestudy(rxinterface.getDatestudy());

            rxestudios.add(rxinterface2);
        }
        switchboton = true;
        listaProyeccionesSel.clear();
        rxinterfaces = linkDao.mostrarInterfaces(workdate);
    }

    public void redirectOnCompleteWorklist() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("mWorklist.xhtml");
    }

    public List<String> getListaProyeccionesSel() {
        return listaProyeccionesSel;
    }

    public void setListaProyeccionesSel(List<String> listaProyeccionesSel) {
        this.listaProyeccionesSel = listaProyeccionesSel;
    }

    public List<Rxinterface> getRxestudios() {
        return rxestudios;
    }

    public void setRxestudios(List<Rxinterface> rxestudios) {
        this.rxestudios = rxestudios;
    }

    public Rxinterface getRxinterface2() {
        return rxinterface2;
    }

    public void setRxinterface2(Rxinterface rxinterface2) {
        this.rxinterface2 = rxinterface2;
    }

    public boolean isSwitchboton() {
        return switchboton;
    }

    public void setSwitchboton(boolean switchboton) {
        this.switchboton = switchboton;
    }

    public void enableBtn() {
        switchboton = false;
    }

    public void disableBtn() {
        switchboton = true;
    }

    public String colorworklist(String status) {
        switch (status) {
            case "completado":
                return "rgb(30, 62, 142)";
            case "agendado":
                return "rgb(233, 174, 56)";

            case "cancelado":
                return "rgb(188, 35, 35)";
            case "edicion":
                return "rgb(30, 142, 62)";
            default:
                //return "rgb(255, 255, 255)";
                return "rgb(99, 255, 20)";
        }
    }

    public String colorinfolist(String status) {
        switch (status) {
            case "dictado":
                return "rgb(30, 142, 62)";
            case "edicion":
                return "rgb(30, 62, 142)";
            case "completado":
                return "rgb(233, 174, 56)";
            default:
                return "rgb(182, 173, 173)";
        }
    }

    public String edad(java.sql.Date dob) {

        LocalDate fechactual = LocalDate.now();
        //LocalDate fechanacimiento = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechanacimiento = dob.toLocalDate();
        Period periodo = Period.between(fechanacimiento, fechactual);

        //return periodo.getYears() + "A, " + periodo.getMonths() + "M, " + periodo.getDays() + "D";
        return periodo.getYears() + "A";
    }

    public List<String> getListaInformeSel() {
        return listaInformeSel;
    }

    public void setListaInformeSel(List<String> listaInformeSel) {
        this.listaInformeSel = listaInformeSel;
    }

    public void setListaInformes(List<SelectItem> listaInformes) {
        this.listaInformes = listaInformes;
    }

    public void setListaFinalizados(List<SelectItem> listaFinalizados) {
        this.listaFinalizados = listaFinalizados;
    }

    public String getEstudiosel() {
        return estudiosel;
    }

    public void setEstudiosel(String estudiosel) {
        this.estudiosel = estudiosel;
    }

    public List<Rxinterface> getRxfinalizados() {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        rxfinalizados = linkDao.mostrarFinalizados(finadate);
        return rxfinalizados;
    }

    public void setRxfinalizados(List<Rxinterface> rxfinalizados) {
        this.rxfinalizados = rxfinalizados;
    }

    public Rxinterface limpiarinterface() {
        rxinterface = new Rxinterface();
        return rxinterface;
    }

    public void limpiarListaInformeSel() {
        //listaInformeSel.clear();
    }

    public void selecRadiologo(ComponentSystemEvent event) {
        //System.out.println("DATOS EVENT EN LA SESION: "+event.toString());
        UsuariosDao linkDao = new UsuariosDaoImplement();
        HttpSession session = Util.getSession();
        //HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);;
        System.out.println("Session: " + session.getAttributeNames().toString());

        System.out.println("ACCNUMBER :" + this.rxinterface.getAccnumber());
        //Obtener nombre de usuario logeado en la sesion
        String radiologo = (String) session.getAttribute("username");
        //Usuarios raduser = null;
        System.out.println("usuario de la sesion: " + radiologo);
        List<Usuarios> users = linkDao.radiologo(radiologo);

        try {
            if (users.get(0).getRadiologo()) {
                rxinterface.setUsuarios(users.get(0));
                System.out.println("raduser: " + users.get(0).getFirstnameuser());
            }
        } catch (NullPointerException e) {
            System.out.println("Not a radiologist user");
        }

    }

    public List<Rxinterface> getRxficha(String idpaciente) {
        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        try {
            rxficha = linkDao.mostrarEstudiosPaciente(idpaciente);
        } catch (NullPointerException e) {
            System.out.println("valor nulo!");
        }

        return rxficha;
    }

    public void setRxficha(List<Rxinterface> rxficha) {
        this.rxficha = rxficha;
    }

    public void estudioSeleccionado(Rxinterface rxinterface) {
        this.rxinterface = rxinterface;
    }

    public void clearestudioSeleccionado(Paciente paciente) {
        this.rxinterface = new Rxinterface();
        this.pacienteBean.setPaciente(paciente);
    }

    public void enviarCorreo() throws ParserConfigurationException, SAXException, IOException {
        System.out.println("enviarCorreo() paciente: " + rxinterface.getPaciente().getLastnamepatient());
        System.out.println("enviarCorreo() reporte: " + rxinterface.getReport());
        RequestContext rc = RequestContext.getCurrentInstance();
        String titulo = "PACIENTE: " + rxinterface.getPaciente().getLastnamepatient() + ", " + rxinterface.getPaciente().getNamepatient() + " " + this.subject;
        String reporte = Jsoup.parse(rxinterface.getReport()).text() + "\n\nInformado: " + rxinterface.getUsuarios().getLastnameuser() + ", " + rxinterface.getUsuarios().getFirstnameuser() + "\nCLINICA DE RADIOLOGIA Y ULTRASONOGRAFIA SOSA - GUEVARA ";
        SendMail.enviarCorreo(this.correo, reporte, titulo);
        rc.execute("PF('pbClient').setValue(100)");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "E-mail enviado!"));

        //Cerrar Dialogo
        rc.execute("PF('correodlg').hide()");
    }

    public String getSubject() {
        try {
            subject = "ESTUDIO: " + this.rxinterface.getProyecciones().getProc();
        } catch (NullPointerException e) {
            subject = "";
        }

        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCorreo() {
        try {
            correo = this.rxinterface.getMedicos().getEmail();
        } catch (NullPointerException e) {
            correo = "";
        }

        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void preliminarReporte(String reporte) {
        ReportePreliminar reportePreliminar = new ReportePreliminar();
        if (reportePreliminar.conteoRenglones(reporte) >= 20) {
            rxinterface.setReport(reporte + reportePreliminar.sepador);
        }
    }

    public String getBcrDui() {
        return bcrDui;
    }

    public void setBcrDui(String bcrDui) {
        this.bcrDui = bcrDui;
    }

    public void scanDui() throws ParseException {

        DatosDui datosDui = new DatosDui(this.bcrDui);
        if (this.bcrDui != null) {
            this.pacienteBean.getPaciente().setExtid(datosDui.getDui());
            this.pacienteBean.getPaciente().setNamepatient(datosDui.getNombre());
            this.pacienteBean.getPaciente().setLastnamepatient(datosDui.getApellido());
            this.pacienteBean.getPaciente().setGender(datosDui.getGenero());
            this.pacienteBean.getPaciente().setDatebirth(datosDui.getDob());
        }
        this.bcrDui = "";
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgregistrardui').hide();");

    }

    public DualListModel<Proyecciones> getEstudiosPickList() {
        return estudiosPickList;
    }

    public void setEstudiosPickList(DualListModel<Proyecciones> estudiosPickList) {
        this.estudiosPickList = estudiosPickList;
    }

    public DualListModel<Proyecciones> fillestudiosPickList() {

        List<Proyecciones> estudiosSource = new ArrayList<Proyecciones>();
        List<Proyecciones> estudiosTarget = new ArrayList<Proyecciones>();

        RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
        estudiosSource = linkDao.mostrarProyecciones();

        return estudiosPickList = new DualListModel<Proyecciones>(estudiosSource, estudiosTarget);
    }

    public void saltosPaginas() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "POLL", rxinterface.getReport()));

    }

    public Plantillas getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Plantillas plantilla) {
        this.plantilla = plantilla;
    }

    public List<SelectItem> getListaPlantillas() {

        this.listaPlantillas = new ArrayList<SelectItem>();
        PlantillasDao linkDao = new PlantillasDaoImplement();
        List<Plantillas> p = linkDao.mostrarPlantillas();
        listaPlantillas.clear();

        for (Plantillas plantillas : p) {
            SelectItem plantillaItem = new SelectItem(plantillas.getIdplantilla(), plantillas.getNombre());
            this.listaPlantillas.add(plantillaItem);
        }
        return listaPlantillas;
    }

    public void setListaPlantillas(List<SelectItem> listaPlantillas) {
        this.listaPlantillas = listaPlantillas;
    }

    public List<Plantillas> obtenerPlantillas() {
        PlantillasDao linkDao = new PlantillasDaoImplement();
        return linkDao.mostrarPlantillas();
    }

    public void agregarPlantilla(String plantilla) {
        if (plantilla != null) {
            this.rxinterface.setReport(this.rxinterface.getReport() + plantilla);
        }
        this.plantilla = new Plantillas();
    }

}
