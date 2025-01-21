package com.varunu28.bookingservice.dto;

import java.util.Date;
import java.util.UUID;

public record CreateBookingRequest(UUID customerId, UUID itemId, Date startDate, Date endDate) {
}
