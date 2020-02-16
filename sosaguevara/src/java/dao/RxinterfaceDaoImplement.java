/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static java.lang.Math.toIntExact;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Aseguradoras;
import model.Medicos;
import model.Paciente;
import model.Proyecciones;
import model.Rxinterface;
import model.Usuarios;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistencia.NewHibernateUtil;

/**
 *
 * @author Administrator
 */
public class RxinterfaceDaoImplement implements RxinterfaceDao {

    @Override
    public List<Rxinterface> mostrarInterfaces(Date workdate) {
        Session session = null;
        List<Rxinterface> lista = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(workdate);
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where (status = 'agendado' or status = 'completado' or status = 'cancelado' or status = 'edicion' ) and year(datestudy) = :year and month(datestudy) = :month and day(datestudy) = :day order by accnumber desc ");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            lista = (List<Rxinterface>) query.list();
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
    public List<Rxinterface> mostrarInformes(Date workdate) {
        Session session = null;
        List<Rxinterface> lista = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(workdate);
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where (status = 'completado' or status = 'dictado' or status = 'edicion') and year(datestudy) = :year and month(datestudy) = :month and day(datestudy) = :day order by accnumber desc");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            lista = (List<Rxinterface>) query.list();
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
    public List<Rxinterface> mostrarFinalizados(Date workdate) {
        Session session = null;
        List<Rxinterface> lista = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(workdate);
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where (status = 'finalizado') and year(datestudy) = :year and month(datestudy) = :month and day(datestudy) = :day order by accnumber desc");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            lista = (List<Rxinterface>) query.list();
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
    public void insertarInterface(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO public.rxinterface ( idpatient, idreference, cpt, aseguradora , tipopago , institucion , pagado ) VALUES (  :idpatient, :idreference, :cpt , :aseguradora , :tipopago , :institucion , :pagado );";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("idpatient", rxinterface.getPaciente().getIdPaciente());
            query.setParameter("idreference", rxinterface.getMedicos().getIdmedico());
            query.setParameter("cpt", rxinterface.getProyecciones().getCpt());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("institucion", rxinterface.getInstitucion());
            if (rxinterface.getPagado() != null) {
                query.setParameter("pagado", rxinterface.getPagado());
            } else {
                query.setParameter("pagado", 0);
            }

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio ingresado con exito!"));
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
    public void insertarInterfaceHist(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO public.rxinterface ( idpatient, idreference, cpt, aseguradora , tipopago , institucion , datestudy ) VALUES (  :idpatient, :idreference, :cpt , :aseguradora , :tipopago , :institucion , :datestudy );";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("idpatient", rxinterface.getPaciente().getIdPaciente());
            query.setParameter("idreference", rxinterface.getMedicos().getIdmedico());
            query.setParameter("cpt", rxinterface.getProyecciones().getCpt());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("institucion", rxinterface.getInstitucion());
            query.setParameter("datestudy", new java.sql.Timestamp(rxinterface.getDatestudy().getTime()));
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio ingresado con exito!"));
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void modificarInterface(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE rxinterface SET  control = :control, idreference = :idreference , cpt = :cpt , status = :status , aseguradora = :aseguradora , tipopago = :tipopago , institucion = :institucion , pagado = :pagado WHERE accnumber = :accnumber ;";

            SQLQuery query = session.createSQLQuery(SQLstr);

            //Si el se cambia el status a cancelado, se ejecuta el canal hl7 de cancelacion y no el de actualizacion
            if (rxinterface.getStatus().equals("cancelado")) {
                query.setParameter("control", "1");
            } else {
                query.setParameter("control", "5");
            }

            query.setParameter("status", rxinterface.getStatus());
            query.setParameter("idreference", rxinterface.getMedicos().getIdmedico());
            query.setParameter("cpt", rxinterface.getProyecciones().getCpt());
            query.setParameter("accnumber", rxinterface.getAccnumber());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("institucion", rxinterface.getInstitucion());
            query.setParameter("pagado", rxinterface.getPagado());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio modificado con exito!"));
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
    public void cancelarInterface(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET control = :control, status = :status WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "1");
            query.setParameter("status", "cancelado");
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio cancelado!"));
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
    public List<Paciente> mostrarPacientes() {
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

    @Override
    public List<Medicos> mostrarMedicos() {
        List<Medicos> lista = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "from Medicos where activo = true and tipo = 'referente'";

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

    @Override
    public List<Usuarios> mostrarRadiologos() {
        List<Usuarios> lista = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "from Usuarios where radiologo = true";

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

    @Override
    public List<Proyecciones> mostrarProyecciones() {
        List<Proyecciones> lista = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "from Proyecciones where activo = true";

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

    @Override
    public List<Aseguradoras> mostrarAseguradoras() {
        Session session = null;
        List<Aseguradoras> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Aseguradoras");
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
    public void insertarInforme(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET report = :report, control = :control, status = :status , idphysician = :idphysician , idreference = :idreference , aseguradora = :aseguradora , tipopago = :tipopago WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "3");
            query.setParameter("status", "dictado");
            query.setParameter("report", rxinterface.getReport().replaceAll("<br>", " "));
            query.setParameter("idphysician", rxinterface.getUsuarios().getIduser());
            query.setParameter("idreference", rxinterface.getMedicos().getIdmedico());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio informado!"));
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
    public void insertarFinalizado(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET report = :report, control = :control, status = :status , idphysician = :idphysician , idreference = :idreference , aseguradora = :aseguradora , tipopago = :tipopago WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "3");
            query.setParameter("status", "finalizado");
            query.setParameter("report", rxinterface.getReport());
            query.setParameter("idphysician", rxinterface.getUsuarios().getIduser());
            query.setParameter("idreference", rxinterface.getMedicos().getIdmedico());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio informado!"));
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
    public int cantidadEstudios(String status, Date date) {
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
            System.out.println(query.uniqueResult());
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
    public List<Rxinterface> mostrarInformeDiario(Date workdate) {
        Session session = null;
        List<Rxinterface> lista = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(workdate);
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras where year(datestudy) = :year and month(datestudy) = :month and day(datestudy) = :day order by accnumber asc");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            lista = (List<Rxinterface>) query.list();
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
    public void completarInterface(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET control = :control, status = :status WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "1");
            query.setParameter("status", "completado");
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio completado!"));
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
    public void finalizarInterface(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET control = :control, status = :status WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "3");
            query.setParameter("status", "finalizado");
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio finalizado! "));
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
    public void edicionInforme(Rxinterface rxinterface, String status) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET report = :report, control = :control, status = :status , idphysician = :idphysician WHERE accnumber = :accnum ;";
            //String SQLstr = "UPDATE public.rxinterface SET report = :report, control = :control, status = :status WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "2");
            query.setParameter("status", status);
            query.setParameter("report", rxinterface.getReport());
            query.setParameter("idphysician", rxinterface.getUsuarios().getIduser());
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio informado!"));
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
    public List<Rxinterface> mostrarInformesMul(Paciente paciente, Date workdate) {
        Session session = null;
        List<Rxinterface> lista = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(workdate);
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where (status = 'completado' or status = 'edicion' or status = 'dictado') and ( idpatient = :idpatient) and year(datestudy) = :year and month(datestudy) = :month and day(datestudy) = :day ");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            query.setParameter("idpatient", paciente.getIdPaciente());
            lista = (List<Rxinterface>) query.list();
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
    public List<Rxinterface> mostrarFinalizadosMul(Paciente paciente, Date workdate) {
        Session session = null;
        List<Rxinterface> lista = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(workdate);
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where (status = 'finalizado') and ( idpatient = :idpatient) and year(datestudy) = :year and month(datestudy) = :month and day(datestudy) = :day ");
            query.setInteger("day", cal.get(Calendar.DATE));
            query.setInteger("month", cal.get(Calendar.MONTH) + 1);
            query.setInteger("year", cal.get(Calendar.YEAR));
            query.setParameter("idpatient", paciente.getIdPaciente());
            lista = (List<Rxinterface>) query.list();
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
    public List<Rxinterface> mostrarNombreEstudio(Long accnum) {
        Session session = null;
        List<Rxinterface> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras where accnumber = :accnum  ");
            query.setParameter("accnum", accnum);
            lista = (List<Rxinterface>) query.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;
    }

    //Setear el radiologo que abre el informe
    @Override
    public void informeRadiologo(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET idphysician = :idphysician WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);

            query.setParameter("idphysician", rxinterface.getUsuarios().getIduser());
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Estudio informado!"));
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
    public List<Rxinterface> mostrarEstudiosPaciente(String idpaciente) {
        Session session = null;
        List<Rxinterface> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where idpatient = :idpatient order by accnumber desc ");
            query.setParameter("idpatient", idpaciente);
            lista = (List<Rxinterface>) query.list();
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
    public List<Medicos> mostrarMedicosFilter(String query) {
        List<Medicos> lista = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String hql = "from Medicos where activo = true and tipo = 'referente' and apellidomedico = :query";
            Query qr = session.createQuery(hql);
            qr.setParameter("query", query);
            lista = qr.list();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        }
        return lista;

    }

    @Override
    public List<Rxinterface> mostrarInfarce(Long accnum) {
        Session session = null;
        List<Rxinterface> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where accnumber = :accnumber ");
            query.setParameter("accnumber", accnum);

            lista = (List<Rxinterface>) query.list();
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
    public List<Paciente> mostrarPacientesTimestamp(String nombre, String apellido, Date dob) {
        List<Paciente> lista = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "from Paciente where namepatient = :namepatient and lastnamepatient = :lastnamepatient and datebirth = :datebirth";

        try {
            Query query = session.createQuery(hql);
            query.setParameter("namepatient", nombre);
            query.setParameter("lastnamepatient", apellido);
            query.setParameter("datebirth", dob);
            lista = query.list();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        }
        return lista;

    }

    @Override
    public void enviarFinalizado(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET report = :report, control = :control, status = :status , idphysician = :idphysician , idreference = :idreference , aseguradora = :aseguradora , tipopago = :tipopago WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("control", "3");
            query.setParameter("status", "finalizado");
            query.setParameter("report", rxinterface.getReport());
            query.setParameter("idphysician", rxinterface.getUsuarios().getIduser());
            query.setParameter("idreference", rxinterface.getMedicos().getIdmedico());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Informe enviado al PACS!"));
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
    public void editarRepoDiario(Rxinterface rxinterface) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.rxinterface SET tipodoc = :tipodoc, documento = :documento, tipopago = :tipopago, institucion = :institucion , aseguradora = :aseguradora , pagado = :pagado WHERE accnumber = :accnum ;";
            //String SQLstr = "UPDATE public.rxinterface SET report = :report, control = :control, status = :status WHERE accnumber = :accnum ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("tipodoc", rxinterface.getTipodoc());
            query.setParameter("documento", rxinterface.getDocumento());
            query.setParameter("tipopago", rxinterface.getTipopago());
            query.setParameter("institucion", rxinterface.getInstitucion());
            query.setParameter("aseguradora", rxinterface.getAseguradoras().getIdaseguradora());
            query.setParameter("pagado", rxinterface.getPagado());
            query.setParameter("accnum", rxinterface.getAccnumber());

            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Registro modificado!"));
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
    public List<Rxinterface> mostrarEstudiosTotal(Date initDate, Date endDate) {
        Session session = null;
        List<Rxinterface> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Rxinterface as O inner join fetch O.paciente inner join fetch O.medicos inner join fetch O.proyecciones inner join fetch O.aseguradoras inner join fetch O.usuarios where O.date between :initDate and :endDate order by date asc ");
            query.setDate("initDate", initDate);
            query.setDate("endDate", endDate);
            lista = (List<Rxinterface>) query.list();
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
