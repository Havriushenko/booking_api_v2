package com.test_task.booking_api_v2.dto.response;

import com.test_task.booking_api_v2.entity.UnitType;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record UnitResponseDto(Long id,
                              UnitType unitType,
                              Integer numberOfRooms,
                              Integer floor,
                              BigDecimal basePrice,
                              BigDecimal tax,
                              BigDecimal totalPrice) {

}
