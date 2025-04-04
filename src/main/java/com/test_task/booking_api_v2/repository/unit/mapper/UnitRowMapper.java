package com.test_task.booking_api_v2.repository.unit.mapper;

import com.test_task.booking_api_v2.dto.response.UnitResponseDto;
import com.test_task.booking_api_v2.entity.UnitType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UnitRowMapper implements RowMapper<UnitResponseDto> {

  @Override
  public UnitResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    return UnitResponseDto.builder()
        .id(rs.getLong("id"))
        .numberOfRooms(rs.getInt("numberOfRooms"))
        .floor(rs.getInt("floor"))
        .unitType(UnitType.getUnitTypeByCode(rs.getString("unitType")))
        .basePrice(rs.getBigDecimal("basePrice"))
        .tax(rs.getBigDecimal("tax"))
        .totalPrice(rs.getBigDecimal("totalPrice"))
        .build();
  }
}
