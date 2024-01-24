package br.com.digitalparking.parking.infrastructure.publisher;

import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEvent;
import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFixedToFinishEventPublisherImpl implements
    ParkingTypeFixedToFinishEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ParkingTypeFixedToFinishEventPublisherImpl(
      ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publishEvent(final ParkingTypeFixedToFinishEvent parkingTypeFixedToFinishEvent) {
    applicationEventPublisher.publishEvent(parkingTypeFixedToFinishEvent);
  }
}
