package br.com.digitalparking.shared.infrastructure.mail.impl;

import br.com.digitalparking.ParkingNotificationApplication;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParkingNotificationSendGridImpl implements ParkingNotificationMessage {

  private final String mailApiKey;

  public ParkingNotificationSendGridImpl(@Value("${app.mail-api-key}") String mailApiKey) {
    this.mailApiKey = mailApiKey;
  }

  @Override
  public void send(String fromEmail, String toEmail, String subject, String contentDescription) {
    Email from = new Email(fromEmail);
    Email to = new Email(toEmail);
    Content content = new Content("text/plain", contentDescription);
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid(mailApiKey);
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
      ParkingNotificationApplication.logger.info("e-mail sent! " + contentDescription);
    } catch (IOException e) {
      ParkingNotificationApplication.logger.error("Error by sending e-mail!  {}", e.getMessage());
    }
  }
}
