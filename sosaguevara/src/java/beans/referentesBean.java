/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UsuariosDao;
import dao.UsuariosDaoImplement;
import model.Usuarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class referentesBean implements Serializable {

    /**
     * Creates a new instance of referentesBean
     */
    private List<SelectItem> listReferentes;
    private Usuarios referente;

    public referentesBean() {
        referente = new Usuarios();
    }

    public Usuarios getReferente() {
        return referente;
    }

    public void setReferente(Usuarios referente) {
        this.referente = referente;
    }

    public List<SelectItem> getListReferentes() {
        this.listReferentes = new ArrayList<SelectItem>();
        UsuariosDao referentes = new UsuariosDaoImplement();
        List<Usuarios> p = referentes.mostrarReferentes();
        listReferentes.clear();

        for (Usuarios referente : p)  {
            SelectItem referenteItem = new SelectItem(referente.getIduser(),referente.getNameuser()+" "+referente.getLastnameuser());
            this.listReferentes.add(referenteItem);
        }
        return listReferentes;
        
    }

}
