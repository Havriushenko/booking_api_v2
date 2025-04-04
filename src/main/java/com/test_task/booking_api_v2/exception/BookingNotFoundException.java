package com.test_task.booking_api_v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "booking not found")
public class BookingNotFoundException extends RuntimeException{

  public BookingNotFoundException(String message) {
    super(message);
  }
}
