package ricardombertani.projetos.allinyourhands.mail.send;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MailSender extends javax.mail.Authenticator {
	// private String mailhost = "";//"smtp.gmail.com";
	private String user;
	private String password;
	private Session session;

	private static Logger log = Logger.getLogger(MailSender.class.getName());

	static {
		Security.addProvider(new JSSEProvider());
	}

	public MailSender() {

		super();
		user = "aiyhadmin";
		password = "babA!@#oprA";

		/*
		 * Properties props = new Properties();
		 * props.put("mail.smtp.starttls.enable=", "false");
		 * props.setProperty("mail.transport.protocol", "smtp");
		 * props.setProperty("mail.host", mailhost); props.put("mail.smtp.auth",
		 * "true"); props.put("mail.smtp.port", "25");
		 * props.put("mail.smtp.socketFactory.port", "465");
		 * props.put("mail.smtp.socketFactory.class",
		 * "javax.net.ssl.SSLSocketFactory");
		 * props.put("mail.smtp.socketFactory.fallback", "false");
		 * 
		 * props.setProperty("mail.smtp.quitwait", "false");
		 * 
		 * session = Session.getInstance(props,
		 * this);//Session.getDefaultInstance(props, this);
		 */

		Properties props = new Properties();
		// props.setProperty("mail.smtp.host", mailhost);
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");

		session = Session.getInstance(props, this);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	public synchronized void sendMail(String subject, String body,
			String recipients)  {
		try {

			MimeMessage msg = new MimeMessage(session);
			DataHandler handler = new DataHandler(new ByteArrayDataSource(
					body.getBytes(), "text/plain"));
			String sender = "aiyhadmin@aiyhappbd.com";
			msg.setFrom(new InternetAddress(sender));
			if (recipients.indexOf(',') > 0)
				msg.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(recipients));
			else
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
						recipients));
			msg.setSubject(subject);
			msg.setText(body);
			msg.setDataHandler(handler);
			Transport transport = session.getTransport("smtp");
			transport.connect();

			Transport.send(msg);
			
			log.debug("\n--> Mail was sent! Message [ "+body+" ] and destMail: "+recipients);

		} catch (Exception e) {

			log.debug("\n-->Exception was send during message sent:  " + e.getMessage());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			Date currentDate = new Date();

			try {
				File emailErrorFile = new File(System.getProperty("ricardombertani.projetos.allinyourhands.emailerror.file.path"));
				if (!emailErrorFile.exists()) {

					if (emailErrorFile.createNewFile()) {
						log.debug("\n-->New Email Error File created!");
					}

				}

				// salvando registros em arquivo
				OutputStream os = (OutputStream) new FileOutputStream(
						emailErrorFile, true);
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
				PrintWriter printWriter = new PrintWriter(osw);
				printWriter.println(sdf.format(currentDate) + "  subject [ "
						+ subject + " ]   message  [ " + body + " ]");
				printWriter.close();
				osw.close();
				os.close();
				log.debug("\n-->Email Message Register was saved with success!!");
			} catch (IOException e2) {

				e.printStackTrace();
			}
		}
	}

	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
}