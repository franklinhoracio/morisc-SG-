/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.PlantillasDao;
import dao.PlantillasDaoImplement;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Plantillas;

/**
 *
 * @author franklin
 */
@FacesConverter(value = "plantillasConverter")
public class convertPlantilla implements Converter {

    Plantillas plantilla;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String idplantillastring) {

        PlantillasDao linkDao = new PlantillasDaoImplement();
        plantilla = (Plantillas) linkDao.obtenerPlantillaObj(Integer.parseInt(idplantillastring));

        return plantilla;

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object plantillaobj) {
        String string;
        if (plantillaobj == null) {
            string = "";
        } else {
            try {
                plantilla = (Plantillas) plantillaobj;
                //System.out.println(proyeccion.getCpt());
                int idplant = plantilla.getIdplantilla();
                string = String.valueOf(idplant);
            } catch (ClassCastException cce) {
                System.out.println("Excepcion en el converter: "+cce);
                throw new ConverterException();
            }
        }
        return string;

    }

}
