package com.example.easyfix.repository;

import com.example.easyfix.model.Booking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {

    @Query(nativeQuery = true, value = "SELECT bs.* FROM easy_fix.booking_services bs "
            + "WHERE bs.user_id = :id")
    List<Booking> findListOfBookingsById(final Integer id);
}
