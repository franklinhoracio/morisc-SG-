/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Plantillas;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author franklin
 */
public class PlantillasDaoImplement implements PlantillasDao {

    @Override
    public List<Plantillas> mostrarPlantillas() {
        Session session = null;
        List<Plantillas> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Plantillas where activo = true order by idplantilla asc");
            lista = (List<Plantillas>) query.list();
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
    public void insertarPlantilla(Plantillas plantilla) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO public.plantillas(plantilla, nombre, modalidad) VALUES ( :plantilla , :nombre , :modalidad);";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("plantilla", plantilla.getPlantilla());
            query.setParameter("nombre", plantilla.getNombre());
            query.setParameter("modalidad", plantilla.getModalidad());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Plantilla ingresada con exito!"));
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar nombre de plantilla!"));
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void modificarPlantilla(Plantillas plantilla) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE plantillas SET plantilla = :plantilla , nombre = :nombre , modalidad = :modalidad WHERE idplantilla = :idplantilla";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("plantilla", plantilla.getPlantilla());
            query.setParameter("nombre", plantilla.getNombre());
            query.setParameter("idplantilla", plantilla.getIdplantilla());
            query.setParameter("modalidad", plantilla.getModalidad());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Plantilla modificada con exito!"));
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
    public void eliminarPlantilla(Plantillas plantilla) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE plantillas SET activo = :activo WHERE idplantilla = :idplantilla";
            System.out.println("LLEGO HASTA EL DAO DE ELIMINAR");
            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("activo", false);
            System.out.println("MUESTRA PLANTILLA "+plantilla.getNombre());
            query.setParameter("idplantilla", plantilla.getIdplantilla());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Plantilla eliminada con exito!"));
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
    public Plantillas obtenerPlantillaObj(int idplantilla) {
        System.out.println("OBTENERPLANTILLAOBJ PLANTILLASDAO");
        Session session = null;
        Plantillas resultado = null;

        List<Plantillas> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Plantillas where idplantilla = :idplantilla");
            System.out.println("VALUE EN EL DAO:  " + idplantilla);
            query.setParameter("idplantilla", idplantilla);
            resultado = (Plantillas) query.uniqueResult();
            System.out.println("recuperado " + resultado.getNombre());
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
