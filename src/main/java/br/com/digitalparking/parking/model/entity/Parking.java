package br.com.digitalparking.parking.model.entity;

import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.shared.model.entity.BaseEntity;
import br.com.digitalparking.shared.model.enums.PaymentState;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
@Table(schema = "parking_management", name = "parking")
public class Parking extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "vehicle_id")
  private Vehicle vehicle;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private String street;
  private String neighborhood;
  private String city;
  private String state;
  private String country;

  @Enumerated(EnumType.STRING)
  private ParkingState parkingState;

  @Enumerated(EnumType.STRING)
  private ParkingType parkingType;

  private Integer parkingTime;
  private LocalDateTime initialParking;
  private LocalDateTime finalParking;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parking_payment_id", referencedColumnName = "id")
  private ParkingPayment parkingPayment;


  public Boolean isParkingTimeValidForParkingType() {
    if (ParkingType.FIXED.equals(this.getParkingType())) {
      return this.getParkingTime() != null && this.getParkingTime() > 0;
    }
    return this.getParkingTime() != null && this.getParkingTime() >= 0;
  }

  public String getParkingPaymentStateDescription() {
    return
        this.getParkingPayment() != null && this.getParkingPayment().getPaymentState() != null
            ? this.getParkingPayment().getPaymentState().getStateDescription()
            : PaymentState.NOT_PAID.getStateDescription();
  }

  public long getTotalHoursParking() {
    if (this.getInitialParking() != null && this.getFinalParking() != null) {
      return ChronoUnit.HOURS.between(this.getInitialParking(), this.getFinalParking());
    }
    return 0;
  }

  public BigDecimal getTotalAmountToPay() {
    var totalHoursParking = this.getTotalHoursParking();
    var parkingTimeValuePerHOur = ParkingTime.ONE.getValuePerHour();
    return new BigDecimal(parkingTimeValuePerHOur).multiply(new BigDecimal(totalHoursParking));
  }

  public String getTotalAmountPaid() {
    if (this.getParkingPayment() == null) {
      return "";
    }
    return this.getParkingPayment().getTotalAmountPaid();
  }
}