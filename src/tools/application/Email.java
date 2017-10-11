package tools.application;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javafx.concurrent.Task;
import tools.utils.Util;

public class Email {

	private String servidorSmtp;
	private String flagTtlsEnable;
	private String portSmtp;
	private String loginSmtp;
	private String flagServerAutentica;
	private String password;
	private File anexo;
	
	public Email(){
		
	}
	
	public Email(String servidorSmtp, String flagTtlsEnable, String portSmtp, String loginSmtp, String flagServerAutentica, String pwd, File anexo) {
		super();
		this.servidorSmtp = servidorSmtp;
		this.flagTtlsEnable = flagTtlsEnable;
		this.portSmtp = portSmtp;
		this.loginSmtp = loginSmtp;
		this.flagServerAutentica = flagServerAutentica;
		this.password = pwd;
		this.anexo = anexo;
	}

	public void enviaEmail(String destinatarios, String msg, String assunto) throws MessagingException{
		
		Properties props = new Properties();
		props.put("mail.smtp.host", servidorSmtp);
		props.setProperty("mail.smtp.starttls.enable", flagTtlsEnable);
		props.setProperty("mail.smtp.port", portSmtp);
		props.setProperty("mail.smtp.user", loginSmtp);
		props.setProperty("mail.smtp.auth", flagServerAutentica);

		Session session = Session.getDefaultInstance(props, null);
		
		session.setDebug(false);

		BodyPart texto = new MimeBodyPart();
		texto.setText(msg);
		BodyPart adjunto = new MimeBodyPart();
		
		if(anexo != null){
		
			adjunto.setDataHandler(new DataHandler(new FileDataSource(anexo)));
			adjunto.setFileName(anexo.getName());
		}
		MimeMultipart multiParte = new MimeMultipart();

		multiParte.addBodyPart(texto);
		multiParte.addBodyPart(adjunto);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(loginSmtp));
		Boolean flag = true;

		String to[] = destinatarios.split(";");
		InternetAddress[] address = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			if (Util.validateEmail(to[i])) {
				address[i] = new InternetAddress(to[i]);
			} else {
				Util.alertError("Endereço de e-mail incorreto.");
				flag = false;
				break;
			}
		}


		if (flag) {
			message.addRecipients(Message.RecipientType.CC, address);
			message.setSubject(assunto);
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");

			t.connect(loginSmtp, password);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
	}
		}
}
