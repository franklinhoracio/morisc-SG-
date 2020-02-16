/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author franklin
 */
@WebServlet("/report.pdf")
public class PdfReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn;
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");
            
            Map parameters = new HashMap();
            parameters.put("access", 1982);
            
            String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/membrete.jasper");
            File reportFile= new File(relativeWebPath);
            byte[] content = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
            
            response.setContentType("application/pdf");
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | JRException ex) {
            Logger.getLogger(PdfReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
