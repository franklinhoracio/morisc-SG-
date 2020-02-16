/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author frodriguez
 */
public class DatosDui {

    private String nombre;
    private String apellido;
    private String dui;
    private String genero;
    private Date dob;

    public DatosDui(String datos) throws ParseException {
        //ArregloDatos arreglo = null;
        //String datos = "01256780]]RODRIGUEZ LOPEZ]]FRANKLIN HORACIO]]2]]g0EBsPJCbAHODINbAgF4QTsC�h5CzAMEAENBA0F4QswDVfhEDAOxUEL1A7Z�QlcDxQpBvwPfHULRBAeGQeMEXhRAqASlE0HtBKoKQ8UEzVaBxAUUAoENBSkOQrMFM2yEYwV-Y0OiBcFnQ2oGgnJCjwaceENvBqH4]]LA LIBERTAD]]SANTA TECLA]]O RH �]]M]]21-04-1983]]12-03-2022]]104010520]]0614'210483'125'8]]SO]]7]]QgsBSvxCZwFufEKKAgvvQv8CGm9B2AJIAkCEApmqQH8DMipCYgM8cEQhA6fZQbUDrAxBIgQCLEILBBGXQp8EMNpB8gRJ9EN0BG3bQ9QEgVdDEwTIWkPKBN1aQr0FH2CCZwUpckD0BWYqQq4FenxBRQWJI0NBBdty]]1E591975 {))1NKVibok1C�Lo-WYeymZ6CQSZ2zTXTOVgMhtg-lV1z-jAGu5cfmS85HiiedzjrY1�yxD5wGSoul4gDweMRlLYuNYp424XDsXexHoBBcXO�MXXQbXQTUBuEUd2-kKeUkt8FyfEM0BagROesOxetQS8F7U�F";
        String[] arrayDatos = datos.split("\\|\\|");
        System.out.println("Array sin datos");
        System.out.println(arrayDatos[0]);
        System.out.println("tamano del array "+arrayDatos.length);
        if (arrayDatos.length < 2) {
            arrayDatos = datos.split("]]");
            System.out.println("SI HICE EL OTRO LOOP");
        }

        for (int i = 0; i < arrayDatos.length; i++) {
            //System.out.println(arrayDatos[i]);
        }
        //DUI DE PACIENTE
        this.setDui(calcularDui(arrayDatos[0]));
        //NOMBRE DE PACIENTE
        this.setNombre(arrayDatos[2].toUpperCase());
        //APELLIDO DE PACIENTE
        this.setApellido(arrayDatos[1].toUpperCase());
        //GENERO DE PACIENTE
        this.setGenero(arrayDatos[8]);
        //FECHA DE NACIMIENTO

        Date dobDate;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            dobDate = df.parse(arrayDatos[9]);
        } catch (ParseException e) {
            System.out.println("EXCEPCION DE PARSEO DE FECHA "+e);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            dobDate = df.parse(arrayDatos[9]);

        }
        this.setDob(dobDate);

    }

    private static String calcularDui(String duiraw) {

        int suma = 0;
        int multiplicador = 9;
        int digito;
        for (int i = 0; i < 8; i++) {
            suma = suma + (Character.getNumericValue(duiraw.charAt(i))) * multiplicador;
            multiplicador--;
        }
        if((suma % 10)!=0){
         digito = 10 - (suma % 10);   
        }else digito = 0;
        
        return duiraw + "-" + String.valueOf(digito);

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
