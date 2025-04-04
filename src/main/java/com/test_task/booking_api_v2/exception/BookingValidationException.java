package com.test_task.booking_api_v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookingValidationException extends RuntimeException{

  public BookingValidationException(String message) {
    super(message);
  }
}
