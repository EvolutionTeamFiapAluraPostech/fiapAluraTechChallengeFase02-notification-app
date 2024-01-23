package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import org.springframework.data.domain.Page;

public record UserInputDto(
    String id,
    String name,
    String email,
    String cpf
) {

  public UserInputDto(User user) {
      this(user.getId() != null ? user.getId().toString() : null,
          user.getName(),
          user.getEmail(),
          user.getCpf());
  }

  public static Page<UserInputDto> toPage(Page<User> usersPage) {
    return usersPage.map(UserInputDto::new);
  }

  public static UserInputDto from(User user){
    return new UserInputDto(user);
  }

}
