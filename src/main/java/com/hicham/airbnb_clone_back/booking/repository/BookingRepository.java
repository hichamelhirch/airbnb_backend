package com.hicham.airbnb_clone_back.booking.repository;


import com.hicham.airbnb_clone_back.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking,Long> {
}
