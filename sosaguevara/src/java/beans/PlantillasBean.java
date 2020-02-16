/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.PlantillasDao;
import dao.PlantillasDaoImplement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import model.Plantillas;

/**
 *
 * @author franklin
 */
@ManagedBean
@SessionScoped
public class PlantillasBean implements Serializable {

    private Plantillas plantilla;
    private List<Plantillas> plantillas;
    private List<SelectItem> listaPlantillas;

    public PlantillasBean() {
        plantilla = new Plantillas();

    }

    public Plantillas getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Plantillas plantilla) {
        this.plantilla = plantilla;
    }

    public List<Plantillas> getPlantillas() {
        PlantillasDao linkDao = new PlantillasDaoImplement();
        plantillas = linkDao.mostrarPlantillas();
        return plantillas;
    }

    public void setPlantillas(List<Plantillas> plantillas) {
        this.plantillas = plantillas;
    }

    public void insertarPlantilla() {
        PlantillasDao linkDao = new PlantillasDaoImplement();
        linkDao.insertarPlantilla(plantilla);
    }

    public void inicializarPlantilla(ActionEvent actionevent) {
        plantilla = new Plantillas();
    }

    public void modificarPlantilla() {
        PlantillasDao linkDao = new PlantillasDaoImplement();
        linkDao.modificarPlantilla(plantilla);
        //plantilla = null;
    }

    public void eliminarPlantilla() {
        PlantillasDao linkDao = new PlantillasDaoImplement();
        linkDao.eliminarPlantilla(plantilla);
        //plantilla = null;
    }

    public String obtenerResumen(String plantilla) {
        String resumen = plantilla;
        if (plantilla.length() > 50) {
            resumen = plantilla.substring(0, 50) + " ... ";
        }

        return resumen;
    }

    public List<SelectItem> getListaPlantillas() {
        this.listaPlantillas = new ArrayList<SelectItem>();
        PlantillasDao linkDao = new PlantillasDaoImplement();
        List<Plantillas> p = linkDao.mostrarPlantillas();
        listaPlantillas.clear();

        for (Plantillas plantillas : p) {
            SelectItem plantillaItem = new SelectItem(plantillas.getIdplantilla(), plantillas.getNombre());
            this.listaPlantillas.add(plantillaItem);
        }
        return listaPlantillas;
    }

    public void setListaPlantillas(List<SelectItem> listaPlantillas) {
        this.listaPlantillas = listaPlantillas;
    }

    public void obtenerDatosPlantilla(int idplantilla) {
        PlantillasDao linkDao = new PlantillasDaoImplement();
        this.plantilla = linkDao.obtenerPlantillaObj(idplantilla);
    }

    public void inicializarTarget(ActionEvent actionevent) {

        plantilla = new Plantillas();
    }

}
