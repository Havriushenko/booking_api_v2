package com.test_task.booking_api_v2.dto.request;

import com.test_task.booking_api_v2.entity.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record UnitRequestDto(@NotNull UnitType unitType,
                             @NotNull @Size(max = 6) Integer numberOfRooms,
                             Integer floor,
                             BigDecimal tax,
                             @NotNull BigDecimal basePrice) {

}
