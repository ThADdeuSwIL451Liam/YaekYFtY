// 代码生成时间: 2025-08-23 13:51:17
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.support.MessageContentPreparator;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;

/**
 * Service component for sending notifications via email.
 */
@Service
@Component
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("{"fromEmail"}"")
    private String fromEmail;

    @Value("{"hostName"}"")
    private String hostName;

    /**
     * Sends a notification email to a specified recipient.
     * 
     * @param toRecipient the email address of the recipient
     * @param subject the subject line of the email
     * @param content the body content of the email
     * @throws MessagingException if an error occurs while sending the email
     */
    public void sendNotification(String toRecipient, String subject, String content) {
        try {
            // Prepare message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(toRecipient);
            helper.setSubject(subject);
            helper.setText(content, true); // true for HTML content

            // Send email
            mailSender.send(mimeMessage);
            logger.info("Notification email sent to: " + toRecipient);
        } catch (MailException | MessagingException e) {
            logger.error("Error sending notification email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Returns the hostname to be used in email communications.
     * 
     * @return the hostname
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the hostname for email communications.
     * 
     * @param hostName the hostname to be set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Sets the sender's email address.
     * 
     * @param fromEmail the email address to be set
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
}
