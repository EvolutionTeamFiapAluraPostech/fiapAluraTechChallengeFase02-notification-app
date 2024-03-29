package br.com.digitalparking.shared.infrastructure.mail.impl;

import br.com.digitalparking.ParkingNotificationApplication;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Primary
@Component
public class ParkingNotificationMessageImpl implements ParkingNotificationMessage {

  private final JavaMailSender mailSender;

  public ParkingNotificationMessageImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void send(String from, String to, String subject, String content) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      mimeMessage.setContent(content, "text/html");
      mailSender.send(mimeMessage);
      ParkingNotificationApplication.logger.info("e-mail sent! " + content);
    } catch (MessagingException e) {
      ParkingNotificationApplication.logger.error("Error by sending e-mail!  {}", e.getMessage());
    }
  }
}
