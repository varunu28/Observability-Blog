package com.varunu28.bookingidservice.controller;

import com.varunu28.bookingidservice.service.BookingIdService;
import io.micrometer.observation.annotation.Observed;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/booking-id")
@Observed(name = "bookingIdController")
public class BookingIdController {

    private final BookingIdService bookingIdService;

    public BookingIdController(BookingIdService bookingIdService) {
        this.bookingIdService = bookingIdService;
    }

    @PostMapping
    @Observed(name = "createBookingId")
    public ResponseEntity<UUID> createBookingId() {
        UUID uuid = bookingIdService.generateNewBookingId();
        return ResponseEntity.ok(uuid);
    }
}
