package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.parking.model.enums.ParkingState.BUSY;
import static br.com.digitalparking.parking.model.enums.ParkingType.FLEX;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
    var parking = createNewParking();
    var actualDateTime = LocalDateTime.now();
    var initialDateTime = actualDateTime.minusHours(1);
    parking.setInitialParking(initialDateTime);
    when(
        parkingService.findByParkingStateAndParkingTypeAndInitialParkingGreaterThanEqual(BUSY, FLEX,
            initialDateTime)).thenReturn(List.of(parking));

    var parkingList = getParkingTypeFlexThatCompletedOneHourUseCase.execute(FLEX.name(),
        actualDateTime);

    assertThat(parkingList).isNotNull().isNotEmpty();
  }

  @Test
  void shouldNotFindParkingFlexWhenParkingDoesNotExistWithFinishDateTimeParkingIs1minuteFromNow() {
    var actualTime = LocalDateTime.now();
    var initialDateTime = actualTime.minusHours(1);
    when(
        parkingService.findByParkingStateAndParkingTypeAndInitialParkingGreaterThanEqual(BUSY, FLEX,
            initialDateTime)).thenReturn(Collections.emptyList());

    var parkingList = getParkingTypeFlexThatCompletedOneHourUseCase.execute(FLEX.name(),
        actualTime);

    assertThat(parkingList).isNotNull().isEmpty();

  }
}