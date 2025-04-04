package com.test_task.booking_api_v2.service;

import com.test_task.booking_api_v2.dto.request.BookingRequestDto;
import com.test_task.booking_api_v2.dto.response.BookingResponseDto;
import com.test_task.booking_api_v2.entity.BookingEntity;
import com.test_task.booking_api_v2.entity.BookingStatus;
import com.test_task.booking_api_v2.entity.UnitEntity;
import com.test_task.booking_api_v2.exception.BookingExistException;
import com.test_task.booking_api_v2.exception.BookingNotFoundException;
import com.test_task.booking_api_v2.exception.BookingValidationException;
import com.test_task.booking_api_v2.repository.booking.BookingRepository;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {

  private final UnitService unitService;
  private final BookingRepository bookingRepository;

  /*
      Add validation for dates!
   */
  @Transactional(rollbackFor = Exception.class)
  public BookingResponseDto create(BookingRequestDto dto) {
    UnitEntity unit = this.unitService.getEntity(dto.unitId());
    BookingEntity exist = this.findExistingBookingByUnitAndDates(dto);
    this.checkUnitDate(exist);

    BookingEntity entity = this.bookingRepository.save(BookingEntity.builder()
        .unit(unit)
        .userId(dto.userId())
        .status(BookingStatus.PENDING)
        .checkIn(dto.checkIn())
        .checkOut(dto.checkOut())
        .createAt(Instant.now())
        .build());

    return this.convertEntityToResponseDto(entity);
  }

  public BookingResponseDto findById(Long id) {
    BookingEntity entity = this.getEntity(id);

    return this.convertEntityToResponseDto(entity);
  }

  public Page<BookingResponseDto> findAllByUser(Long userId, Pageable pageable) {
    return this.bookingRepository.findAllByUserId(userId, pageable)
        .map(this::convertEntityToResponseDto);
  }

  @Transactional(rollbackFor = Exception.class)
  public BookingResponseDto update(Long id, BookingRequestDto dto) {
    BookingEntity entity = this.getEntity(id);
    this.validateUpdateEntity(dto, entity);
    UnitEntity unit = this.unitService.getEntity(dto.unitId());
    entity.setUnit(unit);
    entity.setCheckIn(dto.checkIn());
    entity.setCheckOut(dto.checkOut());

    this.bookingRepository.save(entity);
    return this.convertEntityToResponseDto(entity);
  }

  public void delete(Long id) {
    this.bookingRepository.deleteById(id);
  }

  private BookingEntity findExistingBookingByUnitAndDates(BookingRequestDto dto) {
    return this.bookingRepository.findExistingBooking(dto.unitId(), BookingStatus.CONFIRMED, dto.checkIn(), dto.checkOut())
        .orElse(null);
  }

  private void checkUnitDate(BookingEntity booking) {
    if (Objects.nonNull(booking)) {
      throw new BookingExistException(
          "Unit %s is booked by another user in this dates: %s - %s".formatted(
              booking.getUnit().getId(),
              booking.getCheckIn(),
              booking.getCheckOut()
          ));
    }
  }

  private BookingResponseDto convertEntityToResponseDto(BookingEntity entity) {
    return BookingResponseDto.builder()
        .id(entity.getId())
        .unitId(entity.getUnit().getId())
        .status(entity.getStatus())
        .checkIn(entity.getCheckIn())
        .checkOut(entity.getCheckOut())
        .createAt(entity.getCreateAt())
        .build();
  }

  private BookingEntity getEntity(Long id) {
    return this.bookingRepository.findById(id).orElseThrow(
        () -> new BookingNotFoundException("Booking not found with id: %s!".formatted(id)));
  }

  private void validateUpdateEntity(BookingRequestDto dto, BookingEntity entity) {
    if (!dto.userId().equals(entity.getUserId())) {
      throw new BookingValidationException("User can change just own booking!");
    }
  }
}
