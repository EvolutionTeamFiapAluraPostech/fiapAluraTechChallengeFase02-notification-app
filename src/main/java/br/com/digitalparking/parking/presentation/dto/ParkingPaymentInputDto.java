package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import java.math.BigDecimal;

public record ParkingPaymentInputDto(
    String paymentMethod,
    String paymentState,
    BigDecimal paymentValue
) {

  public ParkingPaymentInputDto(ParkingPayment parkingPayment) {
    this(parkingPayment.getPaymentMethod().getDescription(),
        parkingPayment.getPaymentState().name(),
        parkingPayment.getPaymentValue());
  }

  public static ParkingPaymentInputDto from(ParkingPayment parkingPayment) {
    return new ParkingPaymentInputDto(parkingPayment);
  }
}
