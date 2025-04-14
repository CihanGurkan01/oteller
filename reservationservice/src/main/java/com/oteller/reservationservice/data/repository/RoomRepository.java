package com.oteller.reservationservice.data.repository;

import com.oteller.reservationservice.data.model.RoomEntity;
import com.oteller.reservationservice.enumeration.StatusEnumeration;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    boolean existsByHotelIdAndRoomNumberAndIdNot(Long hotelId, String roomNumber, Long id);

    boolean existsByHotelIdAndId(Long hotelId, Long id);

    boolean existsByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RoomEntity r WHERE r.id = :roomId")
    Optional<RoomEntity> lockRoomById(@Param("roomId") Long roomId);

}
