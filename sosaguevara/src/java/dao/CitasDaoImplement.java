/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Citas;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author franklin
 */
public class CitasDaoImplement implements CitasDao {

    @Override
    public List<Citas> mostrarCitas() {
        Session session = null;
        List<Citas> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Citas where activo = '1'");
            lista = (List<Citas>) query.list();
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
    public void insertarCita(Citas cita) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO public.citas(descripcion, nombre, apellido, telefono, confirmado, horainicio, horafin, iduser) VALUES (:descripcion, :nombre, :apellido, :telefono, :confirmado, :horainicio, :horafin, :iduser);";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("descripcion", cita.getDescripcion());
            query.setParameter("nombre", cita.getNombre());
            query.setParameter("apellido", cita.getApellido());
            query.setParameter("telefono", cita.getTelefono());
            query.setParameter("horainicio", cita.getHorainicio());
            query.setParameter("horafin", cita.getHorafin());
            query.setParameter("confirmado", cita.getConfirmado());
            query.setParameter("iduser", cita.getIduser());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cita ingresada con exito!"));
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
    public void modificarCita(Citas cita) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.citas SET descripcion = :descripcion, nombre = :nombre, apellido = :apellido, telefono = :telefono, confirmado = :confirmado, horainicio = :horainicio, horafin = :horafin, iduser = :iduser WHERE idcitas = :idcitas ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            System.out.println("\n VALORES EN EL DAO MODIFICAR CITA");
            query.setParameter("descripcion", cita.getDescripcion());
            System.out.println("DESCRIPCION "+cita.getDescripcion());
            query.setParameter("nombre", cita.getNombre());
            System.out.println("NOMBRE "+cita.getNombre());
            query.setParameter("apellido", cita.getApellido());
            System.out.println("APELLIDO: "+cita.getApellido());
            query.setParameter("telefono", cita.getTelefono());
            System.out.println("TELEFONO "+cita.getTelefono());
            query.setParameter("horainicio", cita.getHorainicio());
            System.out.println("HORA INICIO "+cita.getHorainicio());
            query.setParameter("horafin", cita.getHorafin());
            System.out.println("HORA FIN "+cita.getHorafin());
            query.setParameter("idcitas", cita.getIdcitas());
            System.out.println("ID CITA "+cita.getIdcitas());
            query.setParameter("confirmado", cita.getConfirmado());
            query.setParameter("iduser", cita.getIduser());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cita modificada con exito!"));
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
    public void desactivarCita(Citas cita) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.citas SET activo = :activo, cancelado = :cancelado WHERE idcitas = :idcitas ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            System.out.println("\n VALORES EN EL DAO DESACTIVAR CITA");
            query.setParameter("activo", "0");
            query.setParameter("cancelado", "1");
            query.setParameter("idcitas", cita.getIdcitas());
            System.out.println("ID CITA "+cita.getIdcitas());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cita modificada con exito!"));
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
    public void arrivarCita(Citas cita) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.citas SET activo = :activo, realizado = :realizado WHERE idcitas = :idcitas ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("activo", "0");
            query.setParameter("realizado", "1");
            query.setParameter("idcitas", cita.getIdcitas());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cita modificada con exito!"));
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
