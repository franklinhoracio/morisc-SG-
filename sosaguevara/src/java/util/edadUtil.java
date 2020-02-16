/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class edadUtil {

    public int calcularEdad(Date dob) {

        //LocalDate doblocal = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate doblocal = this.convertDateObject(dob);
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(doblocal, ahora);

        int edad = periodo.getYears();

        return edad;
    }

    public LocalDate convertDateObject(java.util.Date suspectDate) {

        try {
            // Don't do this if there is the smallest chance 
            // it could be a java.sql.Date!
            return suspectDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (UnsupportedOperationException e) {
            // BOOM!!
        }
        // do this first:
        java.util.Date safeDate = new Date(suspectDate.getTime());

        return safeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String dobcalculated(int edad) {
        String dobcalc;

        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy
        calendar.add(Calendar.YEAR, -edad); //Le resta la cantidad de años  
        dobcalc = calendar.getTime().toString();
        
        return dobcalc;
    }

}
