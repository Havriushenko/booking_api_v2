package com.test_task.booking_api_v2.service;

import com.test_task.booking_api_v2.dto.request.PaymentRequestDto;
import com.test_task.booking_api_v2.entity.BookingEntity;
import com.test_task.booking_api_v2.entity.BookingStatus;
import com.test_task.booking_api_v2.exception.BookingNotFoundException;
import com.test_task.booking_api_v2.repository.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  private final BookingRepository bookingRepository;

  public void createTransaction(PaymentRequestDto dto) {
    BookingEntity booking = this.findById(dto.bookingId());

    log.info("The booking %s was paid by %s user!".formatted(dto.userId(), dto.bookingId()));

    booking.setStatus(BookingStatus.CONFIRMED);
    this.bookingRepository.save(booking);
  }

  private BookingEntity findById(Long id) {
    return this.bookingRepository.findById(id).orElseThrow(
        () -> new BookingNotFoundException("Booking not found with id: %s".formatted(id)));
  }
}
