package com.test_task.booking_api_v2.exception;

public class BookingExistException extends RuntimeException {

  public BookingExistException(String message) {
    super(message);
  }
}
