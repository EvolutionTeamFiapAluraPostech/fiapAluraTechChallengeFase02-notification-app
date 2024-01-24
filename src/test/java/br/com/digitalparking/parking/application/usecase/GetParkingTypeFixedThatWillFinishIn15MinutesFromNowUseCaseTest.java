package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.parking.model.service.ParkingService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCaseTest {

  @Mock
  private ParkingService parkingService;
  @InjectMocks
  private GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;

  @Test
  void shouldGetListOfParkingFixedWhenWillFinishIn15MinutesFromNow() {
    var actualDateTime = LocalDateTime.now();
    var initialDateTime = actualDateTime.plusMinutes(15);
    var finalDateTime = initialDateTime.plusMinutes(1);
    var parking = createNewParking();
    parking.setFinalParking(initialDateTime);
    when(parkingService.findByParkingStateAndParkingTypeAndFinalParkingBetween(ParkingState.BUSY,
        ParkingType.FIXED, initialDateTime, finalDateTime)).thenReturn(List.of(parking));

    var parkingList = getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase.execute(
        ParkingType.FIXED.name(), actualDateTime);

    assertThat(parkingList).isNotNull().isNotEmpty();
  }

  @Test
  void shouldNotFindParkingFixedWhenParkingDoesNotExistWithFinishDateTimeParkingIs15minutesAfterFromNow() {
    var actualDateTime = LocalDateTime.now();
    var initialDateTime = actualDateTime.plusMinutes(15);
    var finalDateTime = initialDateTime.plusMinutes(1);
    when(parkingService.findByParkingStateAndParkingTypeAndFinalParkingBetween(ParkingState.BUSY,
        ParkingType.FIXED, initialDateTime, finalDateTime)).thenReturn(Collections.emptyList());

    var parkingList = getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase.execute(
        ParkingType.FIXED.name(), actualDateTime);

    assertThat(parkingList).isNotNull().isEmpty();
  }
}