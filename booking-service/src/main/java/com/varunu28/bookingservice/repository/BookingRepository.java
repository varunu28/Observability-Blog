package com.varunu28.bookingservice.repository;

import com.varunu28.bookingservice.model.Booking;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, UUID> {

}
