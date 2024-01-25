package br.com.digitalparking.shared.infrastructure.mail;

public interface ParkingNotificationMail {

  void send(String from, String to, String subject, String content);
}
