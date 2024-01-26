package br.com.digitalparking.shared.application.validator;

import br.com.digitalparking.shared.exception.ValidatorException;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class EmailValidator {

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." +
      "[a-zA-Z0-9_+&*-]+)*@" +
      "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
      "A-Z]{2,7}$";

  public void validate(String email) {
    if (email != null && !email.isBlank()) {
      var validEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
      if (!validEmail) {
        throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "email",
            "User email is invalid %s.".formatted(email)));
      }
    }
  }
}
