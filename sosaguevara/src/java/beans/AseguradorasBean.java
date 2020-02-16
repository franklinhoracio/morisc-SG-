/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.AseguradorasDao;
import dao.AseguradorasDaoImplement;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import model.Aseguradoras;

/**
 *
 * @author frodriguez
 */
@ManagedBean
@SessionScoped
public class AseguradorasBean implements Serializable{

    /**
     * Creates a new instance of AseguradorasBean
     */
    private Aseguradoras aseguradora;
    private List<Aseguradoras> aseguradoras;
    
    public AseguradorasBean() {
        aseguradora = new Aseguradoras();
    }

    public Aseguradoras getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradoras aseguradora) {
        this.aseguradora = aseguradora;
    }

    public List<Aseguradoras> getAseguradoras() {
       
        if (aseguradoras == null){
            try{
                AseguradorasDao linkDao = new AseguradorasDaoImplement();
                aseguradoras = linkDao.mostrarAseguradoras();
                
            }catch(Exception e) {
                System.out.println("Error al cargar: " + e);
            }
        }
        
        return aseguradoras;
    }

    public void setAseguradoras(List<Aseguradoras> aseguradoras) {
        this.aseguradoras = aseguradoras;
    }
    
    public void insertarAseguradora(){
        AseguradorasDao linkDao = new AseguradorasDaoImplement();
        linkDao.insertarAseguradora(aseguradora);
        aseguradoras = null;
    }

    public void inicializarAseguradora(ActionEvent actionevent){
        aseguradora = new Aseguradoras();
    }
    
    public void modificarAseguradora(){
        AseguradorasDao linkDao = new AseguradorasDaoImplement();
        linkDao.modificarAseguradora(aseguradora);
        aseguradoras = null;
    }
    
}
