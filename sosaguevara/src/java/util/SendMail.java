/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.EmailconfigDao;
import dao.EmailconfigDaoImplement;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import model.Emailconfig;

/**
 *
 * @author frodriguez
 */
public class SendMail {


    public static void enviarCorreo(String sendto, String resultado, String titulo) throws ParserConfigurationException, SAXException, IOException {

        
        EmailconfigDao linkDao = new EmailconfigDaoImplement();
        Emailconfig datos = linkDao.obtenerDatosCorreo();
        
        final String username = datos.getUsername();
        final String password = datos.getPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", datos.getServer());
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", datos.getServer());
        props.put("mail.smtp.port", datos.getPuerto());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            //Quien envia el correo:
            message.setFrom(new InternetAddress(username));
            //A quien se envia el correo:
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendto));
            message.setSubject(titulo);
            message.setText(resultado);

            Transport.send(message);
            //System.out.println("CORREO ENVIADO CON EXITO!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
