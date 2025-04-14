package com.oteller.reservationservice.data.model;

import com.oteller.reservationservice.enumeration.StatusEnumeration;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    //private StatusEnumeration status = StatusEnumeration.ACTIVE;
    @Column(name = "created_at", insertable = true, updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", insertable = false, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreateBase() {
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }
}
