package com.test_task.booking_api_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "unit")
@NoArgsConstructor
@AllArgsConstructor
public class UnitEntity {

  @Id
  @Column(name = "unit_", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "unit_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private UnitType unitType;

  @NotNull
  @Size(max = 6)
  @Column(name = "number_of_rooms", nullable = false)
  private Integer numberOfRooms;

  @Column
  private Integer floor;

  @NotNull
  @Column(precision = 3, scale = 2, nullable = false)
  private BigDecimal tax;

  @NotNull
  @Column(name = "base_price", nullable = false)
  private BigDecimal basePrice;

  @NotNull
  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;
}
