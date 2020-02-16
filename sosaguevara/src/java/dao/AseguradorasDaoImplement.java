/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Aseguradoras;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author frodriguez
 */
public class AseguradorasDaoImplement implements AseguradorasDao {

    @Override
    public List<Aseguradoras> mostrarAseguradoras() {
        Session session = null;
        List<Aseguradoras> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Aseguradoras where idaseguradora != 0");
            lista = (List<Aseguradoras>) query.list();
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
    public void insertarAseguradora(Aseguradoras aseguradora) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO aseguradoras (aseguradora, direccion, telefono) VALUES ( :aseguradora , :direccion , :telefono );";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("aseguradora", aseguradora.getAseguradora());
            query.setParameter("direccion", aseguradora.getDireccion());
            query.setParameter("telefono", aseguradora.getTelefono());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Aseguradora ingresada con exito!"));
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
    public void modificarAseguradora(Aseguradoras aseguradora) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE aseguradoras SET aseguradora = :aseguradora , direccion = :direccion , telefono = :telefono WHERE idaseguradora = :idaseguradora ";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("aseguradora", aseguradora.getAseguradora());
            query.setParameter("direccion", aseguradora.getDireccion());
            query.setParameter("telefono", aseguradora.getTelefono());
            query.setParameter("idaseguradora", aseguradora.getIdaseguradora());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Aseguradora modificada con exito!"));
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
    public void eliminarAseguradora(Aseguradoras aseguradora) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
