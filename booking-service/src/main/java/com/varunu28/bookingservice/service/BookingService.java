package com.varunu28.bookingservice.service;

import com.varunu28.bookingservice.dto.BookingResponse;
import com.varunu28.bookingservice.dto.UuidResponse;
import com.varunu28.bookingservice.model.Booking;
import com.varunu28.bookingservice.repository.BookingRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.Date;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingService {

    private static final String EXTERNAL_SERVICE_URL = "https://httpbin.org/uuid";
    public static final String FAILED_TO_GET_BOOKING_ID_FROM_EXTERNAL_SERVICE =
        "Failed to get booking ID from external service";

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
                UuidResponse bookingId = callExternalService();
                Booking booking = new Booking(
                    bookingId.getUuid(),
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

    private UuidResponse callExternalService() {
        Timer externalServiceTimer = Timer.builder("booking_create_external_service")
            .tag("requestId", MDC.get("requestId"))
            .description("Time taken to call external service")
            .register(meterRegistry);
        UuidResponse bookingId;
        try {
            bookingId = externalServiceTimer.recordCallable(() -> restTemplate.getForObject(
                EXTERNAL_SERVICE_URL,
                UuidResponse.class));
        } catch (Exception e) {
            throw new InternalError(FAILED_TO_GET_BOOKING_ID_FROM_EXTERNAL_SERVICE);
        }
        if (bookingId == null) {
            throw new InternalError(FAILED_TO_GET_BOOKING_ID_FROM_EXTERNAL_SERVICE);
        }
        return bookingId;
    }
}
