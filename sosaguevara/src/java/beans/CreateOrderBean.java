/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import model.Proyecciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "order")
@SessionScoped
public class CreateOrderBean implements Serializable {

    /**
     * Creates a new instance of CreateOrderBean
     */
    //private Revista revista = new Revista();  
    //private List<Revista> revistas = new ArrayList<Revista>(); 
    private Proyecciones cargo = new Proyecciones();
    private List<Proyecciones> cargos = new ArrayList<Proyecciones>();

    public void deleteAction(Proyecciones cargo) {
        cargos.remove(cargo);
        //Abrir un dialogo desde un bean:
        //RequestContext context = RequestContext.getCurrentInstance();
        //context.execute("PF('dlgordenar').show();");
    }

    public String reinit() {
        cargo = new Proyecciones();
        return null;
    }
    
    public Proyecciones getCargo() {
        return cargo;
    }
    
    public void setCargo(Proyecciones cargo) {
        cargos.add(cargo);
    }
    
    public List<Proyecciones> getCargos() {
        return cargos;
    }    
    
    public void setCargos(List<Proyecciones> cargos) {
        this.cargos = cargos;
    }    

}
