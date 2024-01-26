package br.com.digitalparking.shared.application.validator;

import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.util.IsUUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UuidValidator {

  public void validate(String uuid) {
    if (!IsUUID.isUUID().matches(uuid)) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "UUID",
          "UUID is invalid %s.".formatted(uuid)));
    }
  }
}
