package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;

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

  public static UserInputDto from(User user) {
    return new UserInputDto(user);
  }

  public static User to(UserInputDto userInputDto) {
    return User.builder()
        .name(userInputDto.name)
        .email(userInputDto.email)
        .cpf(userInputDto.cpf)
        .build();
  }
}
