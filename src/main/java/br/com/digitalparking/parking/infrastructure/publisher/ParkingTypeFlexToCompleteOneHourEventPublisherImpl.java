package br.com.digitalparking.parking.infrastructure.publisher;

import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEvent;
import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFlexToCompleteOneHourEventPublisherImpl implements
    ParkingTypeFlexToCompleteOneHourEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ParkingTypeFlexToCompleteOneHourEventPublisherImpl(
      ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publishEvent(
      ParkingTypeFlexToCompleteOneHourEvent parkingTypeFlexToCompleteOneHourEvent) {
    applicationEventPublisher.publishEvent(parkingTypeFlexToCompleteOneHourEvent);
  }
}
