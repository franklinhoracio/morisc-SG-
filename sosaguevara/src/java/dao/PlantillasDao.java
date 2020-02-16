/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Plantillas;

/**
 *
 * @author franklin
 */
public interface PlantillasDao {
    public List<Plantillas> mostrarPlantillas();
    public Plantillas obtenerPlantillaObj(int idplantilla);
    public void insertarPlantilla(Plantillas plantilla);
    public void modificarPlantilla(Plantillas plantilla);
    public void eliminarPlantilla(Plantillas plantilla);
}
