package ma.shop.service;

import ma.shop.database.model.Good;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class MailService {
    private static final Logger LOG = Logger.getLogger(MailService.class);

    private static void sendMail(String email, String message) {
        final String username = "mabohdantsiupryk@gmail.com";
        final String password = "Bohdan2006";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("bohtsiy@gmail.com"));
            mimeMessage.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );

            mimeMessage.setSubject("Tsiupryk WebShop");
            mimeMessage.setText(message);

            Transport.send(mimeMessage);

            LOG.debug("Done");

        } catch (MessagingException e) {
            LOG.error("Can't send email", e);
        }
    }

    public static void sendCode(String email, String code) {
        String message = "Your activation code = " + code;
        LOG.info("Send email to " + email + ". Activation code = " + code);
        sendMail(email, message);
    }

    public static void sendGood(String email, List<Good> good) {
        String message = String.format("Thank for buying!\nYour  product:\n%s", good.toString());
        LOG.info("Send email to " + email + " goods - " + good.toString());
        sendMail(email, message);
    }
}

