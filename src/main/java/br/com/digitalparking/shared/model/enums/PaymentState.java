package br.com.digitalparking.shared.model.enums;

import lombok.Getter;

@Getter
public enum PaymentState {

  PAID("Paid"), NOT_PAID("Not paid");

  private String stateDescription;

  PaymentState(String stateDescription) {
    this.stateDescription = stateDescription;
  }
}
