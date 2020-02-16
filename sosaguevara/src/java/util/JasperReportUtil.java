/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author franklin
 */
public class JasperReportUtil {

    public static ByteArrayOutputStream getOutputStreamFromReport(Map map, String pathJasper) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, dataSource);
        Connection conn;
        Class.forName("org.postgresql.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");
        JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, conn);
        JasperExportManager.exportReportToPdfStream(jp, os);
        os.flush();
        os.close();
        return os;
    }

    public static StreamedContent getStreamContentFromOutputStream(ByteArrayOutputStream os, String contentType, String nameFile) throws Exception {
        StreamedContent file = null;
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        file = new DefaultStreamedContent(is, contentType, nameFile);
        return file;
    }

    public static DefaultStreamedContent imprimePDFEnDocumentViewer(String rutaReporte, Map parameters, String nombreReporte) {
        DefaultStreamedContent content = new DefaultStreamedContent();
        byte[] docPdf;
        try {
            // Obteniendo las rutas relativas de los archivos necesarios
            System.out.println("IMPRIMEPDFENDOCUMENTVIEWER");
            System.out.println("RUTAREPORTE "+rutaReporte);
            System.out.println("NOMBREREPORTE "+nombreReporte);
            ExternalContext sc = FacesContext.getCurrentInstance().getExternalContext();
            Connection conn;
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");
            //ServletInputStream
            docPdf = JasperRunManager.runReportToPdf(rutaReporte, parameters, conn);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();	
            response.setContentType("application/pdf");
            response.setContentLength(docPdf.length);
            System.out.println("docPDF length "+docPdf.length);
            
            System.out.println("ULTIMA");
            //content = new DefaultStreamedContent(new ByteArrayInputStream(docPdf), "application/pdf", nombreReporte);
            content = new DefaultStreamedContent(new ByteArrayInputStream(docPdf), "application/pdf", nombreReporte);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException | JRException e) {
            System.out.println("excepcion");
            System.out.println(e.getMessage());
        }
        return content;
    }

}
