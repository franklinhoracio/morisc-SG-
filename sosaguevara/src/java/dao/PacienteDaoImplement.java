/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Paciente;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistencia.NewHibernateUtil;

;

/**
 *
 * @author Administrator
 */
public class PacienteDaoImplement implements PacienteDao {

    @Override
    public List<Paciente> mostrarPacientes() {
        Session session = null;
        List<Paciente> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Paciente where activo = true order by dateadmission desc");
            lista = (List<Paciente>) query.list();
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
    public String insertarPaciente(Paciente paciente) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //SET PATIENT ID
            if (paciente.getExtid() == null || paciente.getExtid().equals("")) {
                if (paciente.getPassport() == null || paciente.getPassport().equals("")) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date curDate = new Date();
                    String TimeStamp = dateFormat.format(curDate);
                    paciente.setIdPaciente(TimeStamp);
                } else {
                    paciente.setIdPaciente(paciente.getPassport());
                }
            } else {
                paciente.setIdPaciente(paciente.getExtid());
            }

            String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("id_paciente", paciente.getIdPaciente());
            query.setParameter("namepatient", paciente.getNamepatient());
            query.setParameter("lastnamepatient", paciente.getLastnamepatient());
            query.setParameter("gender", paciente.getGender());
            query.setParameter("datebirth", paciente.getDatebirth());
            query.setParameter("adress", paciente.getAdress());
            query.setParameter("phone", paciente.getPhone());
            query.setParameter("extid", paciente.getExtid());
            query.setParameter("email", paciente.getEmail());
            query.setParameter("passport", paciente.getPassport());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Paciente ingresado con exito!"));

        } catch (JDBCException e) {
            String error = e.getSQLException().toString();
            if (error.contains("id_paciente") && error.contains("already exists")) {
                error = "El ID: " + paciente.getIdPaciente() + " ya esta registrado para otro paciente!";
            }
            if (error.contains("value too long")) {
                error = "Nombre o Apellido de paciente sobrepasa el limite de caracteres(100caracteres)!";
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no se ingreso paciente!", error));

            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return paciente.getIdPaciente();
    }

    @Override
    public void modificarPaciente(Paciente paciente, String oldid) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            String SQLstr = "UPDATE public.paciente SET id_paciente = :id_paciente, namepatient = :namepatient, lastnamepatient = :lastnamepatient, gender = :gender, datebirth = :datebirth, adress = :adress, phone = :phone, extid = :extid, email = :email, passport = :passport, adt08 = :adt08, adt14 = :adt14 WHERE id_paciente = :oldid";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("id_paciente", paciente.getIdPaciente());
            query.setParameter("namepatient", paciente.getNamepatient());
            query.setParameter("lastnamepatient", paciente.getLastnamepatient());
            query.setParameter("gender", paciente.getGender());
            query.setParameter("datebirth", paciente.getDatebirth());
            query.setParameter("adress", paciente.getAdress());
            query.setParameter("phone", paciente.getPhone());
            query.setParameter("extid", paciente.getExtid());
            query.setParameter("email", paciente.getEmail());
            query.setParameter("passport", paciente.getPassport());
            query.setParameter("adt08", paciente.getAdt08());
            query.setParameter("adt14", paciente.getAdt14());
            query.setParameter("oldid", oldid);

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Paciente modificado con exito!"));
        } catch (JDBCException e) {
            String error = e.getSQLException().toString();
            if (error.contains("id_paciente") && error.contains("already exists")) {
                error = "El ID: " + paciente.getIdPaciente() + " ya esta registrado para otro paciente!";
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", error));
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void eliminarPaciente(Paciente paciente) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            String SQLstr = "UPDATE public.paciente SET activo = :activo WHERE id_paciente = :id_paciente";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("id_paciente", paciente.getIdPaciente());
            query.setParameter("activo", false);

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Paciente eliminado con exito!"));
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
    public List<Paciente> mostrarPacientesDui(String dui) {
        Session session = null;
        List<Paciente> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Paciente where activo = true and idPaciente = :dui order by dateadmission desc");
            query.setParameter("dui", dui);
            lista = (List<Paciente>) query.list();
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
    public List<Paciente> mostrarPacientesPass(String passport) {
        Session session = null;
        List<Paciente> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Paciente where activo = true and idPaciente = :passport order by dateadmission desc");
            query.setParameter("passport", passport);
            lista = (List<Paciente>) query.list();
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
    public Paciente mostrarPacienteId(String id) {
        Session session = null;
        Paciente paciente = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Paciente where activo = true and idPaciente = :idpaciente order by dateadmission desc");
            query.setParameter("idpaciente", id);
            paciente = (Paciente) query.uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return paciente;
    }

    @Override
    public List<Paciente> mostrarListaPacientes() {
        List<Paciente> lista = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "from Paciente where activo = true";

        try {
            lista = session.createQuery(hql).list();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        }
        return lista;

    }
}
