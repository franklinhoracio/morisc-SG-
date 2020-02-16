/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Aseguradoras;

/**
 *
 * @author frodriguez
 */
public interface AseguradorasDao {

    public List<Aseguradoras> mostrarAseguradoras();
    public void insertarAseguradora(Aseguradoras aseguradora);
    public void modificarAseguradora(Aseguradoras aseguradora);
    public void eliminarAseguradora(Aseguradoras aseguradora);
    
}
