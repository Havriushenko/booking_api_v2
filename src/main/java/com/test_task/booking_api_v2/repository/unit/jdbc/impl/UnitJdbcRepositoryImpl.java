package com.test_task.booking_api_v2.repository.unit.jdbc.impl;

import com.test_task.booking_api_v2.dto.response.UnitResponseDto;
import com.test_task.booking_api_v2.repository.unit.jdbc.UnitJdbcRepository;
import com.test_task.booking_api_v2.repository.unit.mapper.UnitRowMapper;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UnitJdbcRepositoryImpl implements UnitJdbcRepository {

  private static final String BASE_SELECT = """
      SELECT u.unit_ as id,
      u.unit_type as unitType,
      u.number_of_rooms as numberOfRooms,
      u.floor,
      u.tax,
      u.base_price as basePrice,
      u.total_price as totalPrice
      FROM unit u
      """;
  private static final String WHERE = "WHERE \n";
  private static final String PRICE_BETWEEN = "total_price BETWEEN :minPrice AND :maxPrice \n";
  private static final String AND = "AND \n";
  private static final String SELECT_RATE_DATE = """
      NOT EXISTS(
      SELECT 1 FROM booking b
      WHERE b.unit_ = u.unit_
      AND b.booking_status = 'CONFIRMED'
      AND (b.check_in < :checkOut AND b.check_out > :checkIn)
      )
      """;
  private static final String GENERAL_SUFFIX = """ 
      ORDER BY u.total_price ASC
      LIMIT :limit
      OFFSET :offset;
      """;

  private final NamedParameterJdbcTemplate jdbcTemplate;

  /**
   * In this method we can add 1 more query which returns the total number of units with search terms. This method currently
   * returns {@link PageImpl} with total number of elements on the page.
   */
  public Page<UnitResponseDto> findAll(
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Instant checkIn,
      Instant checkOut,
      Pageable pageable
  ) {
    UnitRowMapper mapper = new UnitRowMapper();
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("minPrice", minPrice);
    params.addValue("maxPrice", maxPrice);
    params.addValue("limit", pageable.getPageSize());
    params.addValue("offset", pageable.getOffset());
    if (this.isDateRate(checkIn, checkOut)) {
      params.addValue("checkIn", Timestamp.from(checkIn));
      params.addValue("checkOut", Timestamp.from(checkOut));
    }

    List<UnitResponseDto> result = jdbcTemplate.query(buildSearchQuery(minPrice, maxPrice, checkIn, checkOut), params, mapper);
    return new PageImpl<>(result, pageable, result.size());
  }

  public String buildSearchQuery(
      BigDecimal mixPrice,
      BigDecimal maxPrice,
      Instant checkIn,
      Instant checkOut
  ) {
    boolean isPrice = ObjectUtils.isNotEmpty(mixPrice) && ObjectUtils.isNotEmpty(maxPrice);
    boolean dateRate = this.isDateRate(checkIn, checkOut);
    if (isPrice && dateRate) {
      return BASE_SELECT + WHERE + PRICE_BETWEEN + AND + SELECT_RATE_DATE + GENERAL_SUFFIX;
    } else if (isPrice) {
      return BASE_SELECT + WHERE + PRICE_BETWEEN + GENERAL_SUFFIX;
    } else if (dateRate) {
      return BASE_SELECT + WHERE + SELECT_RATE_DATE + GENERAL_SUFFIX;
    }
    return BASE_SELECT + GENERAL_SUFFIX;
  }

  private boolean isDateRate(Instant checkIn, Instant checkOut) {
    return ObjectUtils.isNotEmpty(checkIn) && ObjectUtils.isNotEmpty(checkOut);
  }
}
