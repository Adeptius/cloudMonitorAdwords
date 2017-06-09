package ua.adeptius.sender;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.adeptius.main.Starter;

import javax.mail.*;
import javax.mail.internet.*;
import java.nio.file.Files;
import java.util.Properties;

public class EmailSender extends Thread {

    private static Logger LOGGER = LoggerFactory.getLogger(Starter.class.getSimpleName());

    private final String username = "your.cloud.monitor";
    private final String password = "357Monitor159";
    private String htmlMessage = "";
    private String registrationUrl;
    private String to = "adeptius@gmail.com";

    public EmailSender(String email, String key) {
        to = email;
        registrationUrl = "http://localhost:8080/registerresult.html?key=" + key;
        htmlMessage = Starter.htmlMailBody;
        start();
    }

    @Override
    public void run() {
        String subject = "Подтверждение регистрации";

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);

        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);

        System.out.println("Port: "+session.getProperty("mail.smtp.port"));

        // Create the email addresses involved
        try {
            InternetAddress from = new InternetAddress(username);
            message.setSubject(subject);
            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("");

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();

            //            message.setContent(message, "text/plain; charset=UTF-8");

            htmlMessage = htmlMessage.replaceAll("USERNAME", to.substring(0, to.indexOf("@")));
            htmlMessage = htmlMessage.replaceAll("REGISTRATION_URL", registrationUrl);
            messageBodyPart.setContent(htmlMessage, "text/html; charset=UTF-8");


            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            System.out.println("Transport: "+transport.toString());
            transport.sendMessage(message, message.getAllRecipients());

            LOGGER.info("Registration letter for {} successfully sended", to);
        } catch (MessagingException e) {
            LOGGER.error("Ошибка отправки регистрационного письма для "+to, e);
        }
    }
}
