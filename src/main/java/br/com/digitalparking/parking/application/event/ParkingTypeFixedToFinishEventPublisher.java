package br.com.digitalparking.parking.application.event;

public interface ParkingTypeFixedToFinishEventPublisher {

  void publishEvent(final ParkingTypeFixedToFinishEvent parkingTypeFixedToFinishEvent);
}
