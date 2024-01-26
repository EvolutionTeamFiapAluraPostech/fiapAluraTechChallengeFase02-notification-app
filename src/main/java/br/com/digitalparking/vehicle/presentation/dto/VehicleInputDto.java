package br.com.digitalparking.vehicle.presentation.dto;

import br.com.digitalparking.vehicle.model.entity.Vehicle;

public record VehicleInputDto(String id, String description, String licensePlate, String color) {

  public VehicleInputDto(Vehicle vehicleCreated) {
    this(vehicleCreated.getId().toString(), vehicleCreated.getDescription(),
        vehicleCreated.getLicensePlate(), vehicleCreated.getColor());
  }

  public static VehicleInputDto from(Vehicle vehicleCreated) {
    return new VehicleInputDto(vehicleCreated);
  }

  public static Vehicle to(VehicleInputDto vehicleInputDto) {
    return Vehicle.builder()
        .description(vehicleInputDto.description)
        .licensePlate(vehicleInputDto.licensePlate)
        .color(vehicleInputDto.color)
        .active(true)
        .build();
  }
}
