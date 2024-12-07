package com.example.easyfix.controller;

import com.example.easyfix.common.EasyFixConstants;
import com.example.easyfix.dto.BookingDto;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/easyFix/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/listBookings/{id}")
    private ResponseEntity<Object> getBookingList(@PathVariable final Integer id) {
        ResponseDto responseDto = bookingService.getBookingList(id);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/addBooking")
    private ResponseEntity<Object> addBooking(@RequestBody final BookingDto bookingDto) {
        ResponseDto responseDto = bookingService.addBooking(bookingDto);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/updateBooking")
    private ResponseEntity<Object> updateBooking(@RequestBody final BookingDto bookingDto) {
        ResponseDto responseDto = bookingService.updateBooking(bookingDto);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @DeleteMapping("/purgeBookings/{id}")
    private ResponseEntity<Object> purgeBookings(@PathVariable String id) {
        ResponseDto responseDto = bookingService.purgeBookings(id);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PutMapping("/restoreBookings/{id}")
    private ResponseEntity<Object> restoreBookings(@PathVariable String id) {
        ResponseDto responseDto = bookingService.restoreBookings(id);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }
}
