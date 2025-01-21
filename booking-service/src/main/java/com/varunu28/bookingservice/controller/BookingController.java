package com.varunu28.bookingservice.controller;

import com.varunu28.bookingservice.dto.BookingResponse;
import com.varunu28.bookingservice.dto.CreateBookingRequest;
import com.varunu28.bookingservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody CreateBookingRequest createBookingRequest) {
        return ResponseEntity.ok(bookingService.createBooking(
            createBookingRequest.itemId(),
            createBookingRequest.customerId(),
            createBookingRequest.startDate(),
            createBookingRequest.endDate()
        ));
    }
}
