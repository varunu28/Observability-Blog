package com.varunu28.bookingservice.service;

import com.varunu28.bookingservice.dto.BookingResponse;
import com.varunu28.bookingservice.model.Booking;
import com.varunu28.bookingservice.repository.BookingRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.Date;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingService {

    public static final String FAILED_TO_GET_BOOKING_ID_FROM_BOOKING_ID_SERVICE =
        "Failed to get booking ID from booking id service";

    @Value("${booking.id.service.url}")
    private String bookingIdServiceUrl;

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;

    public BookingService(
        BookingRepository bookingRepository, RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
    }

    public BookingResponse createBooking(UUID itemId, UUID customerId, Date startDate, Date endDate) {
        Timer createBookingTimer = Timer.builder("booking_create")
            .tag("requestId", MDC.get("requestId"))
            .description("Time taken to create a booking")
            .register(meterRegistry);
        BookingResponse bookingResponse;
        try {
            bookingResponse = createBookingTimer.recordCallable(() -> {
                UUID bookingId = callExternalService();
                Booking booking = new Booking(
                    bookingId,
                    itemId,
                    customerId,
                    startDate,
                    endDate
                );
                writeToDatabase(booking);
                return new BookingResponse(
                    booking.getId(),
                    booking.getItemId(),
                    booking.getCustomerId(),
                    booking.getStartDate(),
                    booking.getEndDate()
                );
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return bookingResponse;
    }

    private void writeToDatabase(Booking booking) {
        Timer dbWriteTimer = Timer.builder("booking_create_db_write")
            .description("Time taken to write a booking to database")
            .tag("requestId", MDC.get("requestId"))
            .register(meterRegistry);
        dbWriteTimer.record(() -> bookingRepository.save(booking));
    }

    private UUID callExternalService() {
        Timer externalServiceTimer = Timer.builder("booking_create_external_service")
            .tag("requestId", MDC.get("requestId"))
            .description("Time taken to call external service")
            .register(meterRegistry);
        UUID bookingId;
        try {
            bookingId = externalServiceTimer.recordCallable(() -> restTemplate.postForObject(
                bookingIdServiceUrl,
                null,
                UUID.class));
        } catch (Exception e) {
            throw new InternalError(FAILED_TO_GET_BOOKING_ID_FROM_BOOKING_ID_SERVICE);
        }
        if (bookingId == null) {
            throw new InternalError(FAILED_TO_GET_BOOKING_ID_FROM_BOOKING_ID_SERVICE);
        }
        return bookingId;
    }
}
