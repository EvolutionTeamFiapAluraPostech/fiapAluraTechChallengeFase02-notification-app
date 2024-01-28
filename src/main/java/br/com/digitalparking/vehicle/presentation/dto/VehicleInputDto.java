package br.com.digitalparking.vehicle.presentation.dto;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "VehicleInputDto", description = "DTO de entrada para receber atributos do veículo")
public record VehicleInputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "UUID Válido")
    String id,
    @Schema(example = "Honda HRV", description = "Marca e modelo do veículo")
    String description,
    @Schema(example = "ABC-1E98 ou ABC-1056", description = "Placa do veículo")
    String licensePlate,
    @Schema(example = "Branco", description = "Cor do veículo")
    String color) {

  public static Vehicle to(VehicleInputDto vehicleInputDto) {
    return Vehicle.builder()
        .description(vehicleInputDto.description)
        .licensePlate(vehicleInputDto.licensePlate)
        .color(vehicleInputDto.color)
        .active(true)
        .build();
  }
}
