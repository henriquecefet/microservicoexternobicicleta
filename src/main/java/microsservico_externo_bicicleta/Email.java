package microsservico_externo_bicicleta;
import java.security.SecureRandom;
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
public class Email {
	private String id;
	private String email;
	private String mensagem;
	
	public Email() {
		
	}
	public Email(String email, String mensagem) {
		SecureRandom rand = new SecureRandom();
		this.id = rand.nextInt(1000)+"";
		this.email = email;
		this.mensagem = mensagem;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public static void enviarEmail(Email e_mail) throws AddressException, MessagingException {
		// Configuracao do SMTP
		Properties propriedades = new Properties();
		propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propriedades.put("mail.smtp.ssl.checkserveridentity", true);
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
		  Message.RecipientType.TO, InternetAddress.parse(e_mail.getEmail()));
		email.setSubject("Sistema de Controle de Bicicletario");
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(e_mail.getMensagem(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		email.setContent(multipart);

		Transport.send(email);
	}

}
