/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static java.lang.Math.toIntExact;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Aseguradoras;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author franklin
 */
public class ReferentesReportDaoImplement implements ReferentesReportDao {

    @Override
    public int cantidadEstudiosDia(String status, Date date) {
        Session session = null;
        int estudios = 0;
        long temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Rxinterface where status = :status and date = :date");
            query.setParameter("date", date);
            query.setParameter("status", status);
            System.out.println("Grafica Status " + query.uniqueResult());
            temp = (long) query.uniqueResult();
            estudios = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;

    }

    @Override
    public int cantidadEstudiosMod(String modalidad, Date date) {
        Session session = null;
        int estudios = 0;
        BigInteger temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            //SQLQuery query = session.createSQLQuery(SQLstr);
            SQLQuery query = session.createSQLQuery("select count(*) from Rxinterface, Proyecciones where proyecciones.cpt = rxinterface.cpt AND proyecciones.modality = :modalidad and rxinterface.date = :date ;");
            query.setParameter("date", date);
            query.setParameter("modalidad", modalidad);
            System.out.println("Grafica Modalidad " + date);
            System.out.println("Grafica Modalidad " + query.uniqueResult());
            temp = (BigInteger) query.uniqueResult();//
            //estudios = toIntExact(temp);
            estudios = temp.intValue();
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;

    }

    @Override
    public int cantidadEstudiosAseg(int aseguradora, Date date) {
        Session session = null;
        int estudios = 0;
        long temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Rxinterface where aseguradora = :aseguradora and date = :date");
            query.setParameter("date", date);
            query.setParameter("aseguradora", aseguradora);
            System.out.println("Grafica aseguradora " + query.uniqueResult());
            temp = (long) query.uniqueResult();
            estudios = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;
    }

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
    public int cantidadEstudios(Date date) {
        Session session = null;
        int estudios = 0;
        long temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Rxinterface where date = :date");
            query.setParameter("date", date);
            temp = (long) query.uniqueResult();
            estudios = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;
    }

    @Override
    public int cantidadPacientesDia(Date date) {
        Session session = null;
        int pacientes = 0;
        long temp;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Paciente where year(dateadmission) = :year and month(dateadmission) = :month and day(dateadmission) = :day");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            temp = (long) query.uniqueResult();
            System.out.println("VALOR DE TEMP DESPUES DE QUERY COUNT: " + temp);
            System.out.println("DATE: " + date);
            pacientes = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return pacientes;
    }

    @Override
    public int cantidadEstudios() {
        Session session = null;
        int estudios = 0;
        long temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Rxinterface where status != :status");
            query.setParameter("status", "cancelado");
            temp = (long) query.uniqueResult();
            estudios = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;
    }

    @Override
    public int cantidadPacientes() {
        Session session = null;
        int pacientes = 0;
        long temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Paciente");
            temp = (long) query.uniqueResult();
            pacientes = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return pacientes;

    }

    @Override
    public int cantidadMedicos() {
        Session session = null;
        int medicos = 0;
        long temp;
        //Date today = new Date();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Query query = session.createQuery("select count(*) from Rxinterface where status = :status");   
            Query query = session.createQuery("select count(*) from Medicos");
            temp = (long) query.uniqueResult();
            medicos = toIntExact(temp);
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return medicos;

    }

    @Override
    public int cantidadEstudiosReferente(Long idreferente) {
        Session session = null;
        int estudios = 0;
        BigInteger temp;
        //BigInteger idref=new BigInteger(idreferente);

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            //SQLQuery query = session.createSQLQuery(SQLstr);
            SQLQuery query = session.createSQLQuery("select count(*) from Rxinterface where idreference = :idreference ;");
            query.setParameter("idreference", idreferente);
            temp = (BigInteger) query.uniqueResult();//
            //estudios = toIntExact(temp);
            estudios = temp.intValue();
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;
    }

    @Override
    public int cantidadEstudiosModalidad(Long idreferente, String modalidad) {
        Session session = null;
        int estudios = 0;
        BigInteger temp;
        //BigInteger idref=new BigInteger(idreferente);

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //String SQLstr = "INSERT INTO public.paciente(id_paciente, namepatient, lastnamepatient, gender, datebirth, adress, phone, extid, email, passport) VALUES (:id_paciente, :namepatient, :lastnamepatient, :gender, :datebirth, :adress, :phone, :extid, :email, :passport);";
            //SQLQuery query = session.createSQLQuery(SQLstr);
            SQLQuery query = session.createSQLQuery("select count(*) from Rxinterface, Proyecciones where idreference = :idreference and Rxinterface.cpt = Proyecciones.cpt and modality = :modality ;");
            query.setParameter("idreference", idreferente);
            query.setParameter("modality", modalidad);
            temp = (BigInteger) query.uniqueResult();//
            //estudios = toIntExact(temp);
            estudios = temp.intValue();
            //session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estudios;
    }


}
