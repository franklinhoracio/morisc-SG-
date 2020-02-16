/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Medicos;

/**
 *
 * @author Administrator
 */
public interface MedicosDao {
    public List<Medicos> mostrarMedicos();
    public Object mostrarReferenteId(Long idreferente);
    public long obtenerUltimoMedico();
    public Medicos obtenerNumeroJunta(String numerojunta);
    public void insertarMedico(Medicos medico);
    public void modificarMedico(Medicos medico);
    public void eliminarMedico(Medicos medico);
    public List<Medicos> listaTopTen();
}
