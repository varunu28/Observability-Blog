package com.varunu28.bookingservice.service;

import com.varunu28.bookingservice.dto.BookingResponse;
import com.varunu28.bookingservice.dto.UuidResponse;
import com.varunu28.bookingservice.model.Booking;
import com.varunu28.bookingservice.repository.BookingRepository;
import io.micrometer.core.annotation.Timed;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingService {

    private static final String EXTERNAL_SERVICE_URL = "https://httpbin.org/uuid";

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    public BookingService(BookingRepository bookingRepository, RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    @Timed(value = "booking_create", description = "Time taken to create a booking")
    public BookingResponse createBooking(UUID itemId, UUID customerId, Date startDate, Date endDate) {
        UuidResponse bookingId = restTemplate.getForObject(EXTERNAL_SERVICE_URL, UuidResponse.class);
        if (bookingId == null) {
            throw new InternalError("Failed to get booking ID from external service");
        }

        Booking booking = new Booking(
            bookingId.getUuid(),
            itemId,
            customerId,
            startDate,
            endDate
        );
        bookingRepository.save(booking);

        return new BookingResponse(
            booking.getId(),
            booking.getItemId(),
            booking.getCustomerId(),
            booking.getStartDate(),
            booking.getEndDate()
        );
    }
}
