package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.shared.model.enums.PaymentState;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;

@Tag(name = "ParkingPaymentInputDto", description = "DTO de entrada para registrar atributos de pagamento de estacionamento")
public record ParkingPaymentInputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "UUID válido")
    String id,
    @Schema(example = "PIX, CREDIT_CARD, DEBIT_CARD", description = "Tipos de pagamentos aceitos")
    String paymentMethod,
    @Schema(example = "NOT_PAID, PAID", description = "Pagamento realizado ou não")
    String paymentState,
    @Schema(example = "5.00", description = "Valor do pagamento")
    BigDecimal paymentValue
) {

  public static ParkingPayment to(ParkingPaymentInputDto parkingPaymentInputDto) {
    return ParkingPayment.builder()
        .paymentMethod(PaymentMethod.valueOf(parkingPaymentInputDto.paymentMethod))
        .paymentState(PaymentState.valueOf(parkingPaymentInputDto.paymentState))
        .paymentValue(parkingPaymentInputDto.paymentValue)
        .build();
  }
}
