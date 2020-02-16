/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author franklin
 */
public class Estudio {
    
    private long cpt;    
    private String estudio; 

    public Estudio() {
    }

    public Estudio(long cpt, String estudio) {
        this.cpt = cpt;
        this.estudio = estudio;
    }
    
    public long getCpt() {
        return cpt;
    }

    public void setCpt(long cpt) {
        this.cpt = cpt;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    
    
    
    
}
