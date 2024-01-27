package br.com.digitalparking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@CrossOrigin
public class ParkingNotificationApplication {

  public final static Logger logger = LoggerFactory.getLogger(ParkingNotificationApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ParkingNotificationApplication.class, args);
  }
}
