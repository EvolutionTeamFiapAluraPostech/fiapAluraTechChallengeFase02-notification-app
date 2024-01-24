package br.com.digitalparking.parking.application.event;

import br.com.digitalparking.parking.model.entity.Parking;
import java.util.List;

public record ParkingTypeFlexToCompleteOneHourEvent(List<Parking> parkingList) {

}
