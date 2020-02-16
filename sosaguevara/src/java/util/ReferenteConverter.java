/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import dao.MedicosDao;
import dao.MedicosDaoImplement;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Medicos;

/**
 *
 * @author frodriguez
 */
@FacesConverter("referenteConverter")
public class ReferenteConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        MedicosDao linkDao = new MedicosDaoImplement(); 
        return linkDao.mostrarReferenteId(Long.valueOf(string)); 
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
         return Long.toString(((Medicos) o).getIdmedico());
    }
    
}
