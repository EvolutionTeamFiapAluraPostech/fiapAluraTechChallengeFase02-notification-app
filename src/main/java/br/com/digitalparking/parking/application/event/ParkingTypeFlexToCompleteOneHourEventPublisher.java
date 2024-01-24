package br.com.digitalparking.parking.application.event;

public interface ParkingTypeFlexToCompleteOneHourEventPublisher {

  void publishEvent(
      final ParkingTypeFlexToCompleteOneHourEvent parkingTypeFlexToCompleteOneHourEvent);
}
