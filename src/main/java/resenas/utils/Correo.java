package resenas.utils;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import com.google.gson.JsonObject;

import resenas.modelo.Direccion;
import resenas.modelo.Persona;
import resenas.modelo.Producto;
import resenas.modelo.ReporteDiario;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Correo {

    

    public static String olvideContrasena(String destinatario, String token) {

        System.out.println("Hola");

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
            String url = "http://localhost:7890/forget-password-action?token=" + token;
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

    public static JsonObject solicitarPedidoCorreo(String destinatario, String asunto, String mensaje) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        JsonObject respuesta = new JsonObject();

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
                    "<h2>" + asunto + "</h2>" +
                    "<div style='background-color: #f2f2f2; width: 30%; padding: 10px; margin: 0 auto;'>" +
                    "<p> " + mensaje + " </p>" +
                    "<p>  Puedes contactarnos al número 2283044402 o a este correo </p>" +
                    "</div>" +
                    "</div>" +
                    "<div style='width: 100%; background-color: #fff; margin-top: 40px;  text-align: center;'>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            message.setSubject("Solicitud de Pedido de forma inmediata - Ferretería Callejas");
            htmlPart.setContent(mensajeHTML, "text/html");

            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.attachFile("src/main/java/resenas/recursos/bitmap.png");
            adjuntoPart.setContentID("<imagen>");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(adjuntoPart);

            message.setContent(multipart);

            Transport.send(message);
            respuesta.addProperty("status", 200);
            respuesta.addProperty("message", "Correo enviado correctamente");
        } catch (MessagingException e) {
            respuesta.addProperty("status", 400);
            respuesta.addProperty("message", "Correo no enviado correctamente");
        } catch (Exception e) {
            respuesta.addProperty("status", 400);
            respuesta.addProperty("message", "Correo no enviado correctamente");
        }

        return respuesta;

    }

    public static boolean productoDanadoo(String path, String destinatario, String nombre) {

        System.out.println(path);

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
                    "<h2>" + "Producto Dañado" + "</h2>" +
                    "<div style='background-color: #f2f2f2; width: 30%; padding: 10px; margin: 0 auto;'>" +
                    "<p>  En su última entrega se ha encontrado un producto dañado, específicamente el producto"
                    + nombre + ", se adjunta un documento en este correo como reporte</p>" +
                    "<p>  Puedes contactarnos al número 2283044402 o a este correo </p>" +
                    "</div>" +
                    "</div>" +
                    "<div style='width: 100%; background-color: #fff; margin-top: 40px;  text-align: center;'>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            message.setSubject("Producto Dañado - Ferretería Callejas");
            htmlPart.setContent(mensajeHTML, "text/html");

            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.attachFile("src/main/java/resenas/recursos/bitmap.png");
            adjuntoPart.setContentID("<imagen>");

            MimeBodyPart pdfPart = new MimeBodyPart();
            File pdfFile = new File(path);
            pdfPart.attachFile(pdfFile);
            pdfPart.setFileName("Reporte " + nombre + ".pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(adjuntoPart);
            multipart.addBodyPart(pdfPart);

            message.setContent(multipart);

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public static String llenarComprobante(Producto producto, Persona persona, Direccion direccion,
            ReporteDiario reporte) {
        try {
            File archivoPDF = new File("src/main/java/documentos/Reporte.pdf");
            PDDocument documento = Loader.loadPDF(archivoPDF);
            PDAcroForm acroForm = documento.getDocumentCatalog().getAcroForm();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = format.format(date);
            String direccionProveedor = direccion.getCalle() + " #" + direccion.getNumero() + ", Col. "
                    + direccion.getColonia() + ", " + direccion.getCiudad();
            if (acroForm != null) {
                acroForm.getField("fecha").setValue(fechaFormateada);
                acroForm.getField("nombreProducto").setValue(producto.getNombre());
                acroForm.getField("descripcionProducto").setValue(producto.getDescripcion());
                acroForm.getField("nombreProveedor").setValue(persona.getNombre());
                acroForm.getField("telefonoProveedor").setValue(persona.getTelefono());
                acroForm.getField("correoProveedor").setValue(persona.getCorreo());
                acroForm.getField("rfcProveedor").setValue(persona.getRfc());
                acroForm.getField("direccionProveedor").setValue(direccionProveedor);
                acroForm.getField("descripcionReporte").setValue(reporte.getDescripcion());
                acroForm.getField("evidenciaDaño").setValue(reporte.getUrlImage());
                acroForm.flatten();

                String nombreArchivoNew = "ComprobantePago" + persona.getId() + ".pdf";
                String path = "src/main/java/documentos/" + nombreArchivoNew;
                String pathWithOutSpace = path.replace(" ", "");
                documento.save(pathWithOutSpace);
                documento.close();
                System.out.println("Formulario rellenado y protegido correctamente.");
                return pathWithOutSpace;
            } else {
                System.out.println("El formulario acro es nulo. No se puede continuar.");
                return "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

}