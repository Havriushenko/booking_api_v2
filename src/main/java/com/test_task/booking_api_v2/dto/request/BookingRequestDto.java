package com.test_task.booking_api_v2.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Builder;

@Builder
public record BookingRequestDto(@NotNull Long unitId,
                                @NotNull Long userId,
                                @NotNull Instant checkIn,
                                @NotNull Instant checkOut) {

}
