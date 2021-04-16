package microsservico_externo_bicicleta;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import io.javalin.http.Context;

public class EnviarEmail {
	// Metodo para enviar e-mail
	public static void enviarEmail(String enderecoEmail, String mensagem) throws AddressException, MessagingException {
		// Configuracao do SMTP
		Properties propriedades = new Properties();
		propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propriedades.put("mail.smtp.port", "465");
        propriedades.put("mail.smtp.socketFactory.port", "465");
        propriedades.put("mail.smtp.auth", "true");
        // Autenticacao da conta utilizada para mandar o e-mail
		Session sessao = Session.getInstance(propriedades, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("sistemabicicleta@gmail.com", "henriquethiago");
		    }
		});
		// Criacao e envio do e-mail propriamente dito
		Message email = new MimeMessage(sessao);
		email.setFrom(new InternetAddress("rodriguesoareshenrique@gmail.com"));
		email.setRecipients(
		  Message.RecipientType.TO, InternetAddress.parse(enderecoEmail));
		email.setSubject("Sistema de Controle de Bicicletário");
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(mensagem, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		email.setContent(multipart);

		Transport.send(email);
	}
}
