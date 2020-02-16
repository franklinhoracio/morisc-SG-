/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.ProyeccionesDao;
import dao.ProyeccionesDaoImplement;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Proyecciones;

/**
 *
 * @author frodriguez
 */
@FacesConverter(value = "entityConverter")
public class convertProyeccion implements Converter {

    Proyecciones proyeccion;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String cptstring) {
        //System.out.println("CONVERTER GETOBJECAS");
        //System.out.println(cptstring);
        ProyeccionesDao linkDao = new ProyeccionesDaoImplement();
        proyeccion = (Proyecciones) linkDao.obtenerCargoObj(Long.parseLong(cptstring));
        //System.out.println("OBJECTO");
        //System.out.println(proyeccion.getCpt());
        //System.out.println(proyeccion.getProc());
        return proyeccion;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object proyeccionobj) {
        //System.out.println("CONVERTER GETASSTRING");
        String string;
        if (proyeccionobj == null) {
            string = "";
        } else {
            try {
                proyeccion = (Proyecciones) proyeccionobj;
                //System.out.println(proyeccion.getCpt());
                Long cpt = proyeccion.getCpt();
                string = Long.toString(cpt);
            } catch (ClassCastException cce) {
                throw new ConverterException();
            }
        }
        return string;
    }

}
