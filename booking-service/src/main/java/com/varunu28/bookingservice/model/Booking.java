package com.varunu28.bookingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity(name = "bookings")
public class Booking {
    @Id
    private UUID id;
    private UUID itemId;
    private UUID customerId;
    private Date startDate;
    private Date endDate;
    private Date createdAt;

    private Date updatedAt;
    private Date deletedAt;

    public Booking() {
    }

    public Booking(UUID id, UUID itemId, UUID customerId, Date startDate, Date endDate) {
        this.id = id;
        this.itemId = itemId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public UUID getId() {
        return id;
    }

    public UUID getItemId() {
        return itemId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date setUpdatedAt(Date updatedAt) {
        return this.updatedAt = updatedAt;
    }
}
