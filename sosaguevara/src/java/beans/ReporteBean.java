/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JasperReportUtil;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class ReporteBean implements Serializable {

    /**
     * Creates a new instance of ReporteBean
     */
    private static final Logger log = LoggerFactory.getLogger(ReporteBean.class);
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;

    public void generateReport(Long accnum) {
        try {
            Map parameters = new HashMap();
            parameters.put("access", accnum);
            System.out.println("REPORTE BEAN "+accnum);
            String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/membrete.jasper");
            File reportFile = new File(relativeWebPath);
            System.out.println("RELATIVEWEBPATH "+reportFile.getPath());
            media = JasperReportUtil.imprimePDFEnDocumentViewer(reportFile.getPath(), parameters, getNameFilePdf(accnum));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getNameFilePdf(Long accnum) {
        return "reporte_" + String.valueOf(accnum) + ".pdf";
    }

    public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

}
