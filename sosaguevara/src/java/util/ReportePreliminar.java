/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author franklin
 */
public class ReportePreliminar {
    
    public String sepador = "<p><br></p><p><br></p><p> ---------------------------------------------------------- NUEVA PAGINA ------------------------------------------------------------ </p><p><br></p><p><br></p>";
    public String espacios = "<p><br></p><p><br></p><p><br></p><p><br></p><p><br></p>";


    public int conteoRenglones(String sTexto) {

        String sTextoBuscado = "</p>";
        int contador = 0;
        while (sTexto.indexOf(sTextoBuscado) > -1) {
            sTexto = sTexto.substring(sTexto.indexOf(
                    sTextoBuscado) + sTextoBuscado.length(), sTexto.length());
            contador++;
        }

        return contador;
    }

}
