package com.test_task.booking_api_v2.repository.booking;

import com.test_task.booking_api_v2.entity.BookingEntity;
import com.test_task.booking_api_v2.entity.BookingStatus;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

  @Query(value = """
      SELECT * FROM Booking b
      WHERE b.unit_ = :unitId
      AND booking_status = ':status'
      AND (b.check_in < :checkOut AND b.check_out > :checkIn);""", nativeQuery = true)
  Optional<BookingEntity> findExistingBooking(Long unitId, BookingStatus status, Instant checkIn, Instant checkOut);

  Page<BookingEntity> findAllByUserId(Long userId, Pageable pageable);
}
