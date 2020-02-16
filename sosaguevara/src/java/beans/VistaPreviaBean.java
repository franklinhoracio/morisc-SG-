/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.RxinterfaceDao;
import dao.RxinterfaceDaoImplement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class VistaPreviaBean implements Serializable {

    private StreamedContent streamedContent;
    Integer accnum;

    public void loadPreview(Integer accnum) throws InstantiationException, IllegalAccessException, SQLException, JRException {

        try {

            Connection conn;
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");

            String logotipo = "/reports/logor.jpg";
            Map parameters = new HashMap();
            parameters.put("access", accnum);
            parameters.put("logo", FacesContext.getCurrentInstance().getExternalContext().getRealPath(logotipo));

            String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/report2.jasper");
            File reportFile = new File(relativeWebPath);
            System.out.println("GETREPORTE " + reportFile.getPath());
            byte[] decode = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);

            ByteArrayInputStream bis = new ByteArrayInputStream(decode);
            streamedContent = new DefaultStreamedContent(bis, "application/pdf");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":formLectura:viewer");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaPreviaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printReport(Integer accnum) throws InstantiationException, IllegalAccessException, SQLException, JRException {

        try {
            Connection conn;
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");

            String logotipo = "/reports/logor.jpg";
            Map parameters = new HashMap();
            parameters.put("access", accnum);
            parameters.put("logo", FacesContext.getCurrentInstance().getExternalContext().getRealPath(logotipo));

            String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/report2.jasper");
            File reportFile = new File(relativeWebPath);
            System.out.println("GETREPORTE " + reportFile.getPath());
            byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);

            JasperPrint jPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);
            JasperPrintManager.printReport(jPrint, true);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaPreviaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadPreviewMul(Long accnum, List<String> listaInformeSel) throws InstantiationException, IllegalAccessException, SQLException, JRException {

        try {

            RxinterfaceDao linkDao = new RxinterfaceDaoImplement();
            String nombrestudios = "";

            for (int i = 0; i < listaInformeSel.size();) {
                if (i == 0) {
                    nombrestudios = linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
                } else {
                    nombrestudios = nombrestudios + " , " + linkDao.mostrarNombreEstudio(Long.parseLong(listaInformeSel.get(i))).get(0).getProyecciones().getProc();
                }
                i++;
            }

            Connection conn;
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");

            String logotipo = "/reports/logor.jpg";

            Map parameters = new HashMap();
            parameters.put("access", accnum);
            parameters.put("estudios", nombrestudios);
            parameters.put("logo", FacesContext.getCurrentInstance().getExternalContext().getRealPath(logotipo));

            String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/repopdfmul.jasper");
            File reportFile = new File(relativeWebPath);
            System.out.println("GETREPORTE " + reportFile.getPath());
            byte[] decode = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);

            ByteArrayInputStream bis = new ByteArrayInputStream(decode);
            streamedContent = new DefaultStreamedContent(bis, "application/pdf");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":formLectura:viewer");
        } catch (ClassNotFoundException | NullPointerException ex) {
            Logger.getLogger(VistaPreviaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

}
