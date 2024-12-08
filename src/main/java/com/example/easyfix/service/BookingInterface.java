package com.example.easyfix.service;

import com.example.easyfix.dto.BookingDto;
import com.example.easyfix.dto.ResponseDto;

public interface BookingInterface {

    ResponseDto getBookingList(final Integer id);

    ResponseDto addBooking(final BookingDto bookingDto);

    ResponseDto updateBooking(final BookingDto bookingDto);

    ResponseDto purgeBookings(final String id);

    ResponseDto restoreBookings(final String id);

}
