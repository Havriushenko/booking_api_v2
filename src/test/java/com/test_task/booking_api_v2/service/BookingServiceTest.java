package com.test_task.booking_api_v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.test_task.booking_api_v2.BookingApplication;
import com.test_task.booking_api_v2.dto.request.BookingRequestDto;
import com.test_task.booking_api_v2.dto.response.BookingResponseDto;
import com.test_task.booking_api_v2.exception.BookingNotFoundException;
import com.test_task.booking_api_v2.exception.BookingValidationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@Tag("integration")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = BookingApplication.class)
class BookingServiceTest {

  @Autowired
  private BookingService bookingService;

  @Test
  void when_getBookingByUserId_expect_successfulFind() {
    Pageable pageable = PageRequest.of(0, 10);
    assertThat(this.bookingService.findAllByUser(5L, pageable))
        .isNotNull()
        .isNotEmpty()
        .hasSize(2);
  }

  @Test
  void when_getBookingByUserId_expect_successfulEmptyList() {
    Pageable pageable = PageRequest.of(0, 10);
    assertThat(this.bookingService.findAllByUser(100000L, pageable))
        .isNotNull()
        .isEmpty();
  }

  @Test
  void when_createBooking_expect_successfulCreateBooking() {
    BookingRequestDto dto = this.buildRequestDto();
    assertThat(this.bookingService.create(dto))
        .isNotNull()
        .extracting(BookingResponseDto::id)
        .isNotNull();
  }

  @Test
  void when_updateBooking_expect_successfulUpdateBooking() {
    BookingRequestDto dto = BookingRequestDto.builder()
        .userId(25L)
        .unitId(2L)
        .checkIn(LocalDateTime.parse("2026-07-16T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .checkOut(LocalDateTime.parse("2026-07-20T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .build();

    assertThat(this.bookingService.update(7L, dto))
        .isNotNull();
  }

  @Test
  void when_updateBooking_expect_throwExceptionNotFound() {
    BookingRequestDto dto = BookingRequestDto.builder()
        .userId(25L)
        .unitId(2L)
        .checkIn(LocalDateTime.parse("2026-07-16T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .checkOut(LocalDateTime.parse("2026-07-20T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .build();

    assertThatThrownBy(() -> this.bookingService.update(-1L, dto))
        .isNotNull()
        .isInstanceOf(BookingNotFoundException.class);
  }

  @Test
  void when_updateBooking_expect_throwValidationException() {
    BookingRequestDto dto = BookingRequestDto.builder()
        .userId(3000L)
        .unitId(2L)
        .checkIn(LocalDateTime.parse("2026-07-16T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .checkOut(LocalDateTime.parse("2026-07-20T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .build();

    assertThatThrownBy(() -> this.bookingService.update(7L, dto))
        .isNotNull()
        .isInstanceOf(BookingValidationException.class);
  }

  @Test
  @SuppressWarnings("java:S5778")
  void when_deleteBooking_expect_successfulDelete() {
    BookingResponseDto request = this.bookingService.create(this.buildRequestDto());
    this.bookingService.delete(request.id());

    assertThatThrownBy(() -> this.bookingService.findById(request.id()))
        .isNotNull()
        .isInstanceOf(BookingNotFoundException.class);
  }

  private BookingRequestDto buildRequestDto() {
    return BookingRequestDto.builder()
        .userId(2000L)
        .unitId(4L)
        .checkIn(LocalDateTime.parse("2026-07-16T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .checkOut(LocalDateTime.parse("2026-07-20T21:19:51").atZone(ZoneId.of("Europe/Kyiv")).toInstant())
        .build();
  }

}
