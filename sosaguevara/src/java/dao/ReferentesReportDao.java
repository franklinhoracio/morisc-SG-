/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import model.Aseguradoras;

/**
 *
 * @author franklin
 */
public interface ReferentesReportDao {
    public int cantidadEstudiosReferente(Long idreferente);
    public int cantidadEstudiosModalidad(Long idreferente, String modalidad);

    //METODOS COPIADOS DEL DASHBOARD
    public List<Aseguradoras> mostrarAseguradoras();
    public int cantidadEstudiosDia(String status, Date date);
    public int cantidadEstudiosMod(String modalidad, Date date);
    public int cantidadEstudiosAseg(int aseguradora, Date date);
    public int cantidadEstudios(Date date);
    public int cantidadPacientesDia(Date date);
    public int cantidadEstudios();
    public int cantidadPacientes();
    public int cantidadMedicos();
    

}
