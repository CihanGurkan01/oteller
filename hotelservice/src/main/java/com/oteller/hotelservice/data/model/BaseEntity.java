package com.oteller.hotelservice.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 * Time:13:10
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
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
