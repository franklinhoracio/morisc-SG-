/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Rxfusion;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author franklin
 */
public class RxfusionDaoImplement implements RxfusionDao {

    @Override
    public List<Rxfusion> mostrarRxfusion() {
        Session session = null;
        List<Rxfusion> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxfusion");
            lista = (List<Rxfusion>) query.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;
    }

    @Override
    public void insertarRxfusion(Rxfusion rxfusion) {

        String SQLstr = "INSERT INTO public.rxfusion( idmasterpatient, idmrg , accfusion ) VALUES ( :idmasterpatient , :idmrg , :accfusion );";

        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("idmasterpatient", rxfusion.getIdmasterpatient());
            query.setParameter("idmrg", rxfusion.getIdmrg());
            query.setParameter("accfusion", "programado");
            query.executeUpdate();
            session.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Fusion de datos se completo exitosamente!"));
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void modificarRxfusion(Rxfusion rxfusion) {

        String SQLstr = "UPDATE public.rxfusion SET idmasterpatient=?, idmrg=? WHERE <condition>;";

    }

    @Override
    public void fusionarPaciente(Rxfusion rxfusion) {
        String SQLstr = "UPDATE public.rxinterface SET idpatient = :idmasterpatient WHERE idpatient = :idmrg ;";

        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("idmasterpatient", rxfusion.getIdmasterpatient());
            query.setParameter("idmrg", rxfusion.getIdmrg());
            query.executeUpdate();
            session.getTransaction().commit();
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Fusion de datos registrado!"));
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
    
    @Override
    public void borrarPaciente(String idmrg) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "DELETE FROM public.paciente WHERE id_paciente = :idmrg ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("idmrg", idmrg);
            query.executeUpdate();
            session.getTransaction().commit();
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Paciente eliminado con exito!"));
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
