package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
class GetParkingTypeFlexThatCompletedOneHourUseCaseTest {

  @Mock
  private ParkingService parkingService;
  @InjectMocks
  private GetParkingTypeFlexThatCompletedOneHourUseCase getParkingTypeFlexThatCompletedOneHourUseCase;

  @Test
  void shouldGetListOfParkingFlexWhenWillFinishIn1MinutesFromNow() {
    var initialDateTime = LocalDateTime.now();
    var finalDateTime = initialDateTime.plusMinutes(1);
    var parking = createNewParking();
    parking.setFinalParking(initialDateTime);
    when(parkingService.findByParkingTypeAndFinalParkingBetween(ParkingType.FLEX, initialDateTime,
        finalDateTime)).thenReturn(List.of(parking));

    var parkingList = getParkingTypeFlexThatCompletedOneHourUseCase.execute(
        ParkingType.FLEX.name(), initialDateTime);

    assertThat(parkingList).isNotNull().isNotEmpty();
  }

  @Test
  void shouldNotFindParkingFlexWhenParkingDoesNotExistWithFinishDateTimeParkingIs1minuteFromNow() {
    var initialDateTime = LocalDateTime.now();
    var finalDateTime = initialDateTime.plusMinutes(1);
    when(parkingService.findByParkingTypeAndFinalParkingBetween(ParkingType.FLEX, initialDateTime,
        finalDateTime)).thenReturn(Collections.emptyList());

    var parkingList = getParkingTypeFlexThatCompletedOneHourUseCase.execute(
        ParkingType.FLEX.name(), initialDateTime);

    assertThat(parkingList).isNotNull().isEmpty();

  }
}