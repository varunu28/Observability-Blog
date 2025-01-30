package com.varunu28.bookingidservice.service;

import com.varunu28.bookingidservice.dto.UuidResponse;
import com.varunu28.bookingidservice.model.BookingId;
import com.varunu28.bookingidservice.repository.BookingIdRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingIdService {

    public static final String FAILED_TO_GET_BOOKING_ID_FROM_EXTERNAL_SERVICE =
        "Failed to get booking ID from external service";

    private final RestTemplate restTemplate;
    private final BookingIdRepository bookingIdRepository;

    @Value("${external.service.url}")
    private String externalServiceUrl;

    public BookingIdService(RestTemplate restTemplate, BookingIdRepository bookingIdRepository) {
        this.restTemplate = restTemplate;
        this.bookingIdRepository = bookingIdRepository;
    }

    public UUID generateNewBookingId() {
        UuidResponse bookingId = callExternalService();
        BookingId bookingIdEntity = new BookingId(bookingId.getUuid());
        bookingIdRepository.save(bookingIdEntity);
        return bookingId.getUuid();
    }

    private UuidResponse callExternalService() {
        UuidResponse bookingId;
        try {
            bookingId = restTemplate.getForObject(
                externalServiceUrl,
                UuidResponse.class);
        } catch (Exception e) {
            throw new InternalError(FAILED_TO_GET_BOOKING_ID_FROM_EXTERNAL_SERVICE);
        }
        if (bookingId == null) {
            throw new InternalError(FAILED_TO_GET_BOOKING_ID_FROM_EXTERNAL_SERVICE);
        }
        return bookingId;
    }
}
