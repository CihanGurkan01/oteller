package com.oteller.reservationservice.data.repository;


import com.oteller.reservationservice.data.model.HotelEntity;
import com.oteller.reservationservice.enumeration.StatusEnumeration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:34
 */
@Repository
public interface HotelRepository extends JpaRepository<HotelEntity,Long> {

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END " +
            "FROM HotelEntity h " +
            "WHERE h.name = :name " +
            "AND h.address.addressStreet = :street " +
            "AND h.address.addressCity = :city " +
            "AND h.address.addressCountry = :country " +
            "AND (:id IS NULL OR h.id != :id)")
    boolean existsHotelWithSameAddress(
            @Param("name") String name,
            @Param("street") String street,
            @Param("city") String city,
            @Param("country") String country,
            @Param("id") Long id
    );

}
