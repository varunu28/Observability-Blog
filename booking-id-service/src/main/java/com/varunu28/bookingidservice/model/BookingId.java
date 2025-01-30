package com.varunu28.bookingidservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity(name = "booking_ids")
public class BookingId {

    @Id
    private UUID id;
    private Date createdAt;
    private Date deletedAt;

    public BookingId() {
    }

    public BookingId(UUID id) {
        this.id = id;
        this.createdAt = new Date();
    }

    public UUID getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
