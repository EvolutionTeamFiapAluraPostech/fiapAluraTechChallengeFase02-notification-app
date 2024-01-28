package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserInputDto", description = "DTO de entrada para receber atributos do condutor do veículo/usuário do aplicativo")
public record UserInputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "UUID válido")
    String id,
    @Schema(example = "Thomas Anderson", description = "Nome do condutor do veículo/usuário do aplicativo")
    String name,
    @Schema(example = "thomas.anderson@matrix.com", description = "email condutor do veículo/usuário do aplicativo")
    String email,
    @Schema(example = "92477979000", description = "CPF do condutor do veículo/usuário do aplicativo")
    String cpf
) {

  public static User to(UserInputDto userInputDto) {
    return User.builder()
        .name(userInputDto.name)
        .email(userInputDto.email)
        .cpf(userInputDto.cpf)
        .build();
  }
}
