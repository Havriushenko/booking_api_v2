package com.test_task.booking_api_v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unit not found")
public class UnitNotFoundException extends RuntimeException{

  public UnitNotFoundException(String message) {
    super(message);
  }
}
