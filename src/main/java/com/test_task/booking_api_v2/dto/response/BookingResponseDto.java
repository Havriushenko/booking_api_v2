package com.test_task.booking_api_v2.dto.response;

import com.test_task.booking_api_v2.entity.BookingStatus;
import java.time.Instant;
import lombok.Builder;

@Builder
public record BookingResponseDto(Long id,
                                 Long unitId,
                                 BookingStatus status,
                                 Instant checkIn,
                                 Instant checkOut,
                                 Instant createAt) {

}
