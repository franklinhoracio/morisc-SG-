/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigInteger;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Medicos;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author Administrator
 */
public class MedicosDaoImplement implements MedicosDao {

    @Override
    public List<Medicos> mostrarMedicos() {
        Session session = null;
        List<Medicos> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Medicos where activo = true and idmedico != 0");
            lista = (List<Medicos>) query.list();
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
    public Object mostrarReferenteId(Long idreferente) {
        Session session = null;
        Object referente = null;
        //List<Medicos> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Medicos where activo = true and idmedico = :idreferente");
            query.setParameter("idreferente", idreferente);
            //lista = (List<Medicos>) query.list();
            referente = query.uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return referente;
    }

    @Override
    public void insertarMedico(Medicos medico) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO medicos (nombremedico, apellidomedico, juntavigilancia, especialidad, clinica, direccionclinica, telefono, celular, tipo) VALUES (:nombremedico, :apellidomedico, :juntavigilancia, :especialidad, :clinica, :direccionclinica, :telefono, :celular, :tipo);";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("nombremedico", medico.getNombremedico());
            query.setParameter("apellidomedico", medico.getApellidomedico());
            query.setParameter("juntavigilancia", medico.getJuntavigilancia());
            query.setParameter("especialidad", medico.getEspecialidad());
            query.setParameter("clinica", medico.getClinica());
            query.setParameter("direccionclinica", medico.getDireccionclinica());
            query.setParameter("telefono", medico.getTelefono());
            query.setParameter("celular", medico.getCelular());
            query.setParameter("tipo", medico.getTipo());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Referente ingresado con exito!"));
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
    public void modificarMedico(Medicos medico) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            String SQLstr = "UPDATE medicos SET  nombremedico = :nombremedico , apellidomedico = :apellidomedico , juntavigilancia = :juntavigilancia , especialidad = :especialidad , clinica = :clinica, direccionclinica = :direccionclinica , telefono = :telefono , celular = :celular , email = :email , tipo = :tipo WHERE idmedico = :idmedico ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("nombremedico", medico.getNombremedico());
            query.setParameter("apellidomedico", medico.getApellidomedico());
            query.setParameter("juntavigilancia", medico.getJuntavigilancia());
            query.setParameter("especialidad", medico.getEspecialidad());
            query.setParameter("clinica", medico.getClinica());
            query.setParameter("direccionclinica", medico.getDireccionclinica());
            query.setParameter("telefono", medico.getTelefono());
            query.setParameter("celular", medico.getCelular());
            query.setParameter("email", medico.getEmail());
            query.setParameter("tipo", medico.getTipo());
            query.setParameter("idmedico", medico.getIdmedico());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Medico modificado con exito!"));
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
    public void eliminarMedico(Medicos medico) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //SELECT TOP 1 Id FROM [TABLENAME] ORDER BY Id DESC
    @Override
    public long obtenerUltimoMedico() {
        Session session = null;
        long idmedico = 0;
        BigInteger bvalue = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "SELECT MAX(idmedico) FROM public.medicos;";

            SQLQuery query = session.createSQLQuery(SQLstr);

            //query.executeUpdate();
            bvalue = (BigInteger) query.uniqueResult();

            session.getTransaction().commit();

            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Medico modificado con exito!"));
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return bvalue.longValue();
    }

    @Override
    public Medicos obtenerNumeroJunta(String numerojunta) {
        Session session = null;
        Medicos resultado = new Medicos();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Medicos where activo = true and juntavigilancia = :juntavigilancia");
            query.setParameter("juntavigilancia", numerojunta);
            resultado = (Medicos) query.uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }

    @Override
    public List<Medicos> listaTopTen() {
        Session session = null;
        List<Medicos> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Medicos where idmedico != 0 ORDER BY estudioagendado desc ");
            //lista = (List<Medicos>) query.list();
            query.setMaxResults(10);
            lista = (List<Medicos>) query.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;

    }
}
