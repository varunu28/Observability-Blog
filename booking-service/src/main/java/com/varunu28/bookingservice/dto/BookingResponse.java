package com.varunu28.bookingservice.dto;

import java.util.Date;
import java.util.UUID;

public record BookingResponse(UUID id, UUID customerId, UUID itemId, Date start, Date end) {
}
