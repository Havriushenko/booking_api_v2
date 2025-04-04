package com.test_task.booking_api_v2.repository.unit.jdbc;

import com.test_task.booking_api_v2.dto.response.UnitResponseDto;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnitJdbcRepository {

  Page<UnitResponseDto> findAll(
      BigDecimal mixPrice,
      BigDecimal maxPrice,
      Instant checkIn,
      Instant checkOut,
      Pageable pageable
  );
}
