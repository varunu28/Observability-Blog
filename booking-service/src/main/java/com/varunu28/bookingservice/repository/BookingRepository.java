package com.varunu28.bookingservice.repository;

import com.varunu28.bookingservice.model.Booking;
import io.micrometer.observation.annotation.Observed;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface BookingRepository extends CrudRepository<Booking, UUID> {

    @Override
    <S extends Booking> S save(S entity);
}
