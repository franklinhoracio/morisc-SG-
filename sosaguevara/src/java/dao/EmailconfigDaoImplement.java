/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Emailconfig;
import model.Rxinterface;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author franklin
 */
public class EmailconfigDaoImplement implements EmailconfigDao {

    @Override
    public Emailconfig obtenerDatosCorreo() {
        Session session = null;
        Emailconfig datos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Emailconfig");
            datos = (Emailconfig) query.uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return datos;

    }

    @Override
    public void updateDatos(Emailconfig emailconfig) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            String SQLstr = "UPDATE public.emailconfig SET username = :username , password = :password , server = :server , puerto = :puerto ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("username", emailconfig.getUsername());
            query.setParameter("password", emailconfig.getPassword());
            query.setParameter("server", emailconfig.getServer());
            query.setParameter("puerto", emailconfig.getPuerto());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos modificados con exito!"));
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
