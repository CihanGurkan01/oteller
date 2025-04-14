package com.oteller.hotelservice.data.repository;

import com.oteller.hotelservice.data.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:34
 */
@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    boolean existsByHotelIdAndRoomNumberAndIdNot(Long hotelId, String roomNumber, Long id);

    boolean existsByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

}
