package com.test_task.booking_api_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

  @Id
  @Column(name = "booking_", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "unit_", nullable = false)
  private UnitEntity unit;

  @NotNull
  @Column(name = "check_in", nullable = false)
  private Instant checkIn;

  @NotNull
  @Column(name = "check_out", nullable = false)
  private Instant checkOut;

  @Column(name = "create_at", nullable = false)
  private Instant createAt;

  @NotNull
  @Column(name = "booking_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private BookingStatus status;

  @NotNull
  @Column(name="user_",nullable = false)
  private Long userId;
}
