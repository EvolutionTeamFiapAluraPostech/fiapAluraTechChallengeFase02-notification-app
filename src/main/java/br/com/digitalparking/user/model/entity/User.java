package br.com.digitalparking.user.model.entity;

import br.com.digitalparking.shared.model.entity.BaseEntity;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "user_management", name = "users")
public class User extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String cpf;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(schema = "user_management", name = "user_vehicles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "vehicle_id"))
  private List<Vehicle> vehicles = new ArrayList<>();

  public void add(Vehicle vehicle) {
    if (this.getVehicles() == null) {
      this.setVehicles(new ArrayList<>());
    }
    if (!this.userHasVehicleWithLicensePlate(vehicle.getLicensePlate())) {
      this.vehicles.add(vehicle);
    }
  }

  public boolean userHasVehicleWithLicensePlate(String licensePlate) {
    if (this.getVehicles() == null || this.getVehicles().isEmpty()) {
      return false;
    }
    return getVehicleBy(licensePlate).isPresent();
  }

  public Optional<Vehicle> getVehicleBy(String licensePlate) {
    return getVehicles().stream().filter(vehicle -> vehicle.getLicensePlate().equals(licensePlate))
        .findFirst();
  }
}
