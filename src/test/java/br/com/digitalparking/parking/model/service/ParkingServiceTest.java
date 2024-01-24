package br.com.digitalparking.parking.model.service;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

  @Mock
  private ParkingRepository parkingRepository;
  @InjectMocks
  private ParkingService parkingService;

  @Test
  void shouldFindParkingFixedWhenFinishDateTimeParkingIs15minutesAfterFromNow() {
    var actualDateTime = LocalDateTime.now();
    var initialDateTime = actualDateTime.plusMinutes(15);
    var finalDateTime = initialDateTime.plusMinutes(1);
    var parking = createNewParking();
    parking.setFinalParking(initialDateTime);
    when(parkingRepository.findByParkingStateAndParkingTypeAndFinalParkingBetween(ParkingState.BUSY,
        ParkingType.FIXED, initialDateTime, finalDateTime)).thenReturn(
        Collections.singletonList(parking));

    var parkingList = parkingService.findByParkingStateAndParkingTypeAndFinalParkingBetween(
        ParkingState.BUSY, ParkingType.FIXED, initialDateTime, finalDateTime);

    assertThat(parkingList).isNotNull().isNotEmpty();
  }

  @Test
  void shouldNotFindParkingFixedWhenParkingDoesNotExistWithFinishDateTimeParkingIs15minutesAfterFromNow() {
    var actualDateTime = LocalDateTime.now();
    var initialDateTime = actualDateTime.plusMinutes(15);
    var finalDateTime = initialDateTime.plusMinutes(1);
    when(parkingRepository.findByParkingStateAndParkingTypeAndFinalParkingBetween(ParkingState.BUSY,
        ParkingType.FIXED, initialDateTime, finalDateTime)).thenReturn(Collections.emptyList());

    var parkingList = parkingService.findByParkingStateAndParkingTypeAndFinalParkingBetween(
        ParkingState.BUSY, ParkingType.FIXED, initialDateTime, finalDateTime);

    assertThat(parkingList).isNotNull().isEmpty();
  }

  @Test
  void shouldFindParkingFlexWhenFinishDateTimeParkingIsNow() {
    var initialDateTime = LocalDateTime.now();
    var finalDateTime = initialDateTime.plusMinutes(1);
    var parking = createNewParking();
    parking.setFinalParking(initialDateTime);
    when(parkingRepository.findByParkingStateAndParkingTypeAndFinalParkingBetween(ParkingState.BUSY,
        ParkingType.FIXED, initialDateTime, finalDateTime)).thenReturn(
        Collections.singletonList(parking));

    var parkingList = parkingService.findByParkingStateAndParkingTypeAndFinalParkingBetween(
        ParkingState.BUSY, ParkingType.FIXED, initialDateTime, finalDateTime);

    assertThat(parkingList).isNotNull().isNotEmpty();
  }

  @Test
  void shouldNotFindParkingFlexWhenParkingDoesNotExistWithFinishDateTimeParkingIsNow() {
    var initialDateTime = LocalDateTime.now();
    var finalDateTime = initialDateTime.plusMinutes(1);
    when(parkingRepository.findByParkingStateAndParkingTypeAndFinalParkingBetween(ParkingState.BUSY,
        ParkingType.FIXED, initialDateTime, finalDateTime)).thenReturn(Collections.emptyList());

    var parkingList = parkingService.findByParkingStateAndParkingTypeAndFinalParkingBetween(
        ParkingState.BUSY, ParkingType.FIXED, initialDateTime, finalDateTime);

    assertThat(parkingList).isNotNull().isEmpty();
  }
}