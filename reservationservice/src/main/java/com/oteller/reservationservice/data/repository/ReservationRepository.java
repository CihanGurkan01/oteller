package com.oteller.reservationservice.data.repository;

import com.oteller.reservationservice.data.model.ReservationEntity;
import com.oteller.reservationservice.enumeration.StatusEnumeration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:34
 */
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    @Query("""
    SELECT COUNT(r) > 0 FROM ReservationEntity r
    WHERE r.hotelId = :hotelId
      AND r.roomId = :roomId
      AND r.checkInDate <= :checkOutDate
      AND r.checkOutDate >= :checkInDate
      AND (:id IS NULL OR r.id != :id)
""")
    boolean existsOverlappingReservation(
            @Param("hotelId") Long hotelId,
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("id") Long id
    );

    Optional<ReservationEntity> findByIdAndUserId(Long id, String userId);

    Page<ReservationEntity> findAllByUserId(Pageable pageable, String userId);
}
