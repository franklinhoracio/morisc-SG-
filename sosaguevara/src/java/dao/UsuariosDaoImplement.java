/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Usuarios;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import persistencia.NewHibernateUtil;

/**
 *
 * @author Administrator
 */
public class UsuariosDaoImplement implements UsuariosDao {

    @Override
    public List<Usuarios> mostrarUsuarios() {

        Session session = null;
        List<Usuarios> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuarios where activo = true");
            lista = (List<Usuarios>) query.list();
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
    public List<Usuarios> mostrarReferentes() {
        Session session = null;
        List<Usuarios> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuarios where activo = true and rol = 'referente'");
            lista = (List<Usuarios>) query.list();
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
    public List<Usuarios> login(String user, String password) {
        Session session = null;
        List<Usuarios> lista = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuarios where nameuser = :p1 and password = :p2");
            query.setParameter("p1", user);
            query.setParameter("p2", password);
            lista = (List<Usuarios>) query.list();
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
    public void insertarUsuarios(Usuarios usuario) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "INSERT INTO public.usuarios ( nameuser, lastnameuser, firstnameuser, rol, phone, emailuser, password ) VALUES ( :nameuser , :lastnameuser , :firstnameuser , :rol , :phone , :emailuser , :pass );";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("nameuser", usuario.getNameuser());
            query.setParameter("lastnameuser", usuario.getLastnameuser());
            query.setParameter("firstnameuser", usuario.getFirstnameuser());
            query.setParameter("rol", usuario.getRol());
            query.setParameter("phone", usuario.getPhone());
            query.setParameter("emailuser", usuario.getEmailuser());
            query.setParameter("pass", usuario.getPassword());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Usuario ingresado con exito!"));
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
    public void modificarUsuarios(Usuarios usuario) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = " UPDATE public.usuarios SET rol = :rol , lastnameuser = :lastnameuser , phone = :phone , emailuser = :emailuser , firstnameuser = :firstnameuser WHERE iduser = :iduser ; ";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("iduser", usuario.getIduser());
            query.setParameter("lastnameuser", usuario.getLastnameuser());
            query.setParameter("firstnameuser", usuario.getFirstnameuser());
            query.setParameter("rol", usuario.getRol());
            query.setParameter("phone", usuario.getPhone());
            query.setParameter("emailuser", usuario.getEmailuser());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Usuario modificado con exito!"));
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
    public void eliminarUsuarios(Usuarios usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPass(Usuarios usuario) {
        Session session = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String SQLstr = "UPDATE public.usuarios SET password = :pass WHERE iduser = :iduser ;";

            SQLQuery query = session.createSQLQuery(SQLstr);
            query.setParameter("pass", usuario.getPassword());
            query.setParameter("iduser", usuario.getIduser());
            query.executeUpdate();
            session.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Password ingresado con exito!"));
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
    public List<Usuarios> radiologo(String uname) {

        Session session = null;
        List<Usuarios> lista = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuarios where nameuser = :p1 " );
            query.setParameter("p1", uname);
            System.out.println("Usuario en el DAO antes de query"+uname);
            lista = (List<Usuarios>) query.list();
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
