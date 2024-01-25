package br.com.digitalparking.shared.infrastructure.mail;

public interface ParkingNotificationMessage {

  void send(String from, String to, String subject, String content);
}
