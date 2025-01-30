package com.varunu28.bookingidservice.repository;

import com.varunu28.bookingidservice.model.BookingId;
import io.micrometer.observation.annotation.Observed;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface BookingIdRepository extends CrudRepository<BookingId, UUID> {
    @Override
    <S extends BookingId> S save(S entity);
}
