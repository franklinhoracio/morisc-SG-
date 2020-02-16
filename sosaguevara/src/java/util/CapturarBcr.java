/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Scanner;
import model.Paciente;

/**
 *
 * @author franklin
 */
public class CapturarBcr {

    public static Paciente leerBcr() {
        Paciente paciente = new Paciente(); 
        //LEER BCR
        Scanner sc = new Scanner(System.in);
        String s;
        System.out.print("Introduzca texto: ");
        s = sc.nextLine();

        //OBTENER ARREGLO DE DATOS
        if (s != null) {
            System.out.println("PDF417: " + s);
            String[] arrayDatos = s.split("]]");

            for (int i = 0; i < arrayDatos.length; i++) {
                System.out.println(arrayDatos[i]);
            }
            
            paciente.setExtid(arrayDatos[0]);
            paciente.setLastnamepatient(arrayDatos[1]);
            paciente.setNamepatient(arrayDatos[2]);
            paciente.setGender(arrayDatos[8]);
            //paciente.setDatebirth(arrayDatos[9]);
            
        }

        return paciente;
    }
}
