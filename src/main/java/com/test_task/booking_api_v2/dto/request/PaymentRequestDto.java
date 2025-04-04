package com.test_task.booking_api_v2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentRequestDto(@NotNull Long userId,
                                @NotNull Long bookingId) {

}
