package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.shared.model.enums.PaymentState;
import java.math.BigDecimal;

public record ParkingPaymentInputDto(
    String id,
    String paymentMethod,
    String paymentState,
    BigDecimal paymentValue
) {

  public ParkingPaymentInputDto(ParkingPayment parkingPayment) {
    this(parkingPayment.getId() != null ? parkingPayment.getId().toString() : "",
        parkingPayment.getPaymentMethod().getDescription(),
        parkingPayment.getPaymentState().name(),
        parkingPayment.getPaymentValue());
  }

  public static ParkingPaymentInputDto from(ParkingPayment parkingPayment) {
    return new ParkingPaymentInputDto(parkingPayment);
  }

  public static ParkingPayment to(ParkingPaymentInputDto parkingPaymentInputDto) {
    return ParkingPayment.builder()
        .paymentMethod(PaymentMethod.valueOf(parkingPaymentInputDto.paymentMethod))
        .paymentState(PaymentState.valueOf(parkingPaymentInputDto.paymentState))
        .paymentValue(parkingPaymentInputDto.paymentValue)
        .build();
  }
}
