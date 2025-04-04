package com.test_task.booking_api_v2.entity;

import lombok.Getter;

@Getter
public enum BookingStatus {
  PENDING("pending"),
  CONFIRMED("confirmed");

  BookingStatus(String code) {
    this.code = code;
  }

  private String code;

}
