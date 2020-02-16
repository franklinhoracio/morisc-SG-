/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;

/**
 *
 * @author franklin
 */
public class Reporte4 {
    
    private final String logotipo = "/reports/logor.jpg";

     public void getReporte(Long accnum, String nombrestudios) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
   
	Connection conn;
        Class.forName("org.postgresql.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/morisc", "postgres", "Simed2018$");

            Map parameters = new HashMap();
            parameters.put("access", accnum);
            parameters.put("estudios", nombrestudios);
            parameters.put("logo", FacesContext.getCurrentInstance().getExternalContext().getRealPath(logotipo));

	    String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/repopdfmul.jasper");
	    File reportFile= new File(relativeWebPath);
	    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
	    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();	
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream ouputStream = response.getOutputStream();
	    ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();

          
    }

}
