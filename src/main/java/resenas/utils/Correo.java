package resenas.utils;

import javax.mail.*;
import javax.mail.internet.*;

import static spark.Spark.stop;

import java.util.Properties;

public class Correo {

    public static String olvideContrasena(String destinatario, String token) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Utils.REMITENTE_GOOGLE, Utils.PASSWORD_GOOGLE);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Utils.REMITENTE_GOOGLE));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            MimeBodyPart htmlPart = new MimeBodyPart();
            String url = "http://localhost:3000/forget-password-action?token=" + token;
            String mensajeHTML = "<html>" +
                    "<head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                    "</head>" +
                    "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; width: 100%;'>" +
                    "<table width='100%' bgcolor='#F58A27' style='color: #fff; text-align: center;'>" +
                    "<tr>" +
                    "<td>" +
                    "<img style=\"width: 100px;\" src=\"cid:imagen\">" +
                    "</td>" +
                    "<td>" +
                    "<h1>Ferretería Callejas</h1>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div style='width: 100%; background-color: #fff; text-align: center;'>" +
                    "<h2>Recuperación de Contraseña</h2>" +
                    "<div style='background-color: #f2f2f2; width: 30%; padding: 10px; margin: 0 auto;'>" +
                    "<p> Seleccione el botón inferior para ser redirigido para ingresar su nueva contraseña </p>" +
                    "</div>" +
                    "</div>" +
                    "<div style='width: 100%; background-color: #fff; margin-top: 40px;  text-align: center;'>" +
                    "<a style=\"background-color:#F58A27; padding:10px 5px; color:#fff; text-decoration:none;\" href=\""
                    + url + "\">Recuperar Contraseña</a>"
                    +
                    "</div>" +
                    "</body>" +
                    "</html>";

            message.setSubject("Recuperación de Contraseña - Ferretería Callejas");
            htmlPart.setContent(mensajeHTML, "text/html");

            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.attachFile("src/main/java/resenas/recursos/bitmap.png");
            adjuntoPart.setContentID("<imagen>");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(adjuntoPart);

            message.setContent(multipart);

            Transport.send(message);
            return "Revisa tu correo, hemos envíado un enlace para recuperar tu contraseña.";
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            return "Hubo un error";
        } catch (Exception e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            return "Hubo un error";
        }
    }

}