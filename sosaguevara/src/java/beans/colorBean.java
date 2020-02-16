/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@ManagedBean
@ViewScoped
public class colorBean {

    String url;
    String colorurl1, colorurl2, colorurl3, colorurl4, colorurl5, colorurl6;
    String back1, back2, back3, back4, back5, back6;

    public colorBean() {
    }

    public String getColorurl1() {
        url = this.getUrl();
        if (url.contains("mWorklist.xhtml")) colorurl1 = "white";
        else colorurl1 = "black";
        return colorurl1;
    }

    public String getColorurl2() {
        url = this.getUrl();
        if (url.contains("mInformes.xhtml")) colorurl2 = "white";
        else colorurl2 = "black";
        return colorurl2;
    }

    public String getColorurl3() {
        url = this.getUrl();
        if (url.contains("mPacientes.xhtml")) colorurl3 = "white";
        else colorurl3 = "black";
        return colorurl3;
    }

    public String getColorurl4() {
        url = this.getUrl();
        if (url.contains("mFinalizados.xhtml")) colorurl4 = "white";
        else colorurl4 = "black";
        return colorurl4;
    }

    public String getColorurl5() {
        url = this.getUrl();
        if (url.contains("mCitas.xhtml")) colorurl5 = "white";
        else colorurl5 = "black";
        return colorurl5;
    }

    public String getColorurl6() {
        url = this.getUrl();
        if (url.contains("mDashboard.xhtml")) colorurl6 = "white";
        else colorurl6 = "black";
        return colorurl6;
    }

    public String getBack1() {
        url = this.getUrl();
        if (url.contains("mWorklist.xhtml")) back1 = "#333367";
        else back1 = "white";
        return back1;
    }

    public String getBack2() {
        url = this.getUrl();
        if (url.contains("mInformes.xhtml")) back2 = "#333367";
        else back2 = "white";
        return back2;
    }

    public String getBack3() {
        url = this.getUrl();
        if (url.contains("mPacientes.xhtml")) back3 = "#333367";
        else back3 = "white";
        return back3;
    }

    public String getBack4() {
        url = this.getUrl();
        if (url.contains("mFinalizados.xhtml")) back4 = "#333367";
        else back4 = "white";
        return back4;
    }

    public String getBack5() {
        url = this.getUrl();
        if (url.contains("mCitas.xhtml")) back5 = "#333367";
        else back5 = "white";        
        return back5;
    }

    public String getBack6() {
        url = this.getUrl();
        if (url.contains("mDashboard.xhtml")) back6 = "#333367";
        else back6 = "white";
        return back6;
    }

    public String getUrl() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        return url;
    }

}
