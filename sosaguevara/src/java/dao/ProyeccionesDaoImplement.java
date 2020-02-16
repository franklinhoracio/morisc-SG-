/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Proyecciones;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author Administrator
 */
public class ProyeccionesDaoImplement implements ProyeccionesDao {

    @Override
    public List<Proyecciones> mostrarCargos() {
        Session session = null;
        List<Proyecciones> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Proyecciones where activo = true");
            lista = (List<Proyecciones>) query.list();
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
    public void insertarCargo(Proyecciones cargo) {

        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO public.proyecciones( proc, modality, precio) VALUES ( :proc, :modality, :precio);";

            SQLQuery query = session.createSQLQuery(SQLstr);
            //query.setParameter("cpt", cargo.getCpt());
            query.setParameter("proc", cargo.getProc());
            query.setParameter("modality", cargo.getModality());
            query.setParameter("precio", cargo.getPrecio());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto ingresado con exito!"));
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
    public void modificarCargo(Proyecciones cargo) {

        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE proyecciones SET proc = :proc , modality = :modality , precio = :precio WHERE cpt = :cpt ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("cpt", cargo.getCpt());
            query.setParameter("proc", cargo.getProc());
            query.setParameter("modality", cargo.getModality());
            query.setParameter("precio", cargo.getPrecio());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto modificado con exito!"));
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
    public void eliminarCargo(Proyecciones cargo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerCargo(long cpt) {

        Session session = null;
        Proyecciones resultado = null;
        String estudio = "";

        List<Proyecciones> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Proyecciones where cpt = :cpt");
            System.out.println("VALUE EN EL DAO:  " + cpt);
            query.setParameter("cpt", cpt);
            lista = (List<Proyecciones>) query.list();
            //resultado = (Proyecciones) query.uniqueResult();
            //lista = (List<Proyecciones>) query.list();
            System.out.println(lista.size());

            System.out.println("55555555555555555555555555555555555555");
            System.out.println(lista.get(0).getProc());
            System.out.println("CPT SACADO DE LA LISTA  " + lista.get(0).getCpt());
            estudio = lista.get(0).getProc();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudio;
    }

    @Override
    public Proyecciones obtenerCargoObj(long cpt) {
        System.out.println("OBTENERCAROBJ PROYECCIONESDAO");
        Session session = null;
        Proyecciones resultado = null;

        List<Proyecciones> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Proyecciones where cpt = :cpt");
            System.out.println("VALUE EN EL DAO:  " + cpt);
            query.setParameter("cpt", cpt);
            resultado = (Proyecciones) query.uniqueResult();
            System.out.println("recuperado "+resultado.getProc());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }

}
