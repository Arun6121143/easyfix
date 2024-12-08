package com.example.easyfix.service;

import com.example.easyfix.common.EasyFixConstants;
import com.example.easyfix.common.EasyFixUtil;
import com.example.easyfix.common.EmptyJsonResponse;
import com.example.easyfix.common.StatusCodes;
import com.example.easyfix.config.ResponseMessageConfig;
import com.example.easyfix.dto.BookingDto;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.model.Booking;
import com.example.easyfix.model.Services;
import com.example.easyfix.model.User;
import com.example.easyfix.repository.BookingRepo;
import com.example.easyfix.repository.ServiceRepo;
import com.example.easyfix.repository.UserRepo;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService implements BookingInterface {

    private final BookingRepo bookingRepo;
    private final ResponseMessageConfig responseMessageConfig;
    private final UserRepo userRepo;
    private final ServiceRepo serviceRepo;
    private final String payload = "Bookings";

    public ResponseDto getBookingList(final Integer id) {
        try {
            List<Booking> bookings = bookingRepo.findListOfBookingsById(id);

            if (bookings.isEmpty()) {
                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.EMPTY_LIST,
                        responseMessageConfig.getStatusMessage(StatusCodes.EMPTY_LIST,
                                payload),
                        EmptyJsonResponse.getEmptyResponseArray());
            } else {
                List<BookingDto> bookingDtoList = new ArrayList<>();
                bookings.forEach(booking -> {
                    Boolean isDeleted = booking.getDeletedBy() == null && booking.getDeletedDate() == null;
                    String userEmail = userRepo.findById(booking.getUserId()).map(User::getEmail).orElse("");
                    String serviceProviderEmail = userRepo.findById(booking.getServiceProviderId())
                            .map(User::getEmail).orElse("");
                    String serviceName = serviceRepo.findById(booking.getServiceId())
                            .map(Services::getServiceName).orElse("");
                    BookingDto bookingDto = BookingDto.builder()
                            .id(booking.getId()).serviceDescription(booking.getServiceDescription())
                            .isDeleted(isDeleted).userEmail(userEmail).serviceName(serviceName)
                            .userId(booking.getUserId()).bookingDate(booking.getBookingDate())
                            .bookingTime(booking.getBookingTime()).serviceProviderId(booking.getServiceProviderId())
                            .serviceProviderEmail(serviceProviderEmail).status(booking.getStatus().toUpperCase())
                            .address(booking.getAddress())
                            .build();
                    bookingDtoList.add(bookingDto);
                });

                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.LIST_OPERATION_SUCCESS,
                        responseMessageConfig.getStatusMessage(StatusCodes.LIST_OPERATION_SUCCESS,
                                payload),
                        bookingDtoList);
            }
        } catch (Exception e) {
            log.error("Exception While fetching Booking details of the user", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.LIST_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.LIST_OPERATION_FAILED,
                                payload), EmptyJsonResponse.getEmptyResponseArray());
    }

    public ResponseDto addBooking(final BookingDto bookingDto) {
        try {
            Booking booking = Booking.builder()
                    .bookingDate(bookingDto.getBookingDate()).bookingTime(bookingDto.getBookingTime())
                    .serviceId(bookingDto.getServiceId()).serviceProviderId(bookingDto.getServiceProviderId())
                    .status(bookingDto.getStatus().toUpperCase()).createdDate(Timestamp.from(Instant.now()))
                    .serviceDescription(bookingDto.getServiceDescription())
                    .address(bookingDto.getAddress())
                    .build();
            bookingRepo.save(booking);
            return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                    StatusCodes.BOOKING_OPERATION_SUCCESS, responseMessageConfig
                            .getStatusMessage(StatusCodes.BOOKING_OPERATION_SUCCESS),
                    EmptyJsonResponse.getEmptyResponseArray());
        } catch (Exception e) {
            log.error("Exception While making a booking", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.BOOKING_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.BOOKING_OPERATION_FAILED),
                EmptyJsonResponse.getEmptyResponseArray());
    }

    public ResponseDto updateBooking(final BookingDto bookingDto) {
        try {
            Optional<Booking> booking = bookingRepo.findById(bookingDto.getId());

            if (booking.isPresent()) {
                Booking booking1 = Booking.builder()
                        .bookingTime(bookingDto.getBookingTime())
                        .serviceId(bookingDto.getServiceId())
                        .bookingDate(bookingDto.getBookingDate())
                        .userId(bookingDto.getUserId())
                        .serviceDescription(bookingDto.getServiceDescription())
                        .address(bookingDto.getAddress())
                        .build();
                bookingRepo.save(booking1);

                return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.UPDATE_OPERATION_SUCCESS, responseMessageConfig
                                .getStatusMessage(StatusCodes.UPDATE_OPERATION_SUCCESS, payload),
                        EmptyJsonResponse.getEmptyResponseArray());
            }

        } catch (Exception e) {
            log.error("Exception While Updating The Booking", e);
        }

        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.UPDATE_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.UPDATE_OPERATION_FAILED, payload),
                EmptyJsonResponse.getEmptyResponseArray());
    }

    public ResponseDto purgeBookings(final String id) {
        try {
            List<Integer> ids = Arrays.stream(id.split(",")).map(Integer::parseInt).toList();

            if (!ids.isEmpty()) {
                ids.forEach(bookingId -> {
                    Booking booking = bookingRepo.findById(bookingId).orElseThrow();
                    String userEmail = userRepo.findById(booking.getUserId()).map(User::getEmail).orElse("");
                    booking.setDeletedBy(userEmail);
                    booking.setDeletedDate(Timestamp.from(Instant.now()));
                    bookingRepo.save(booking);
                });

                return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.PURGE_OPERATION_SUCCESS, responseMessageConfig
                                .getStatusMessage(StatusCodes.PURGE_OPERATION_SUCCESS, payload),
                        EmptyJsonResponse.getEmptyResponseArray());
            }
        } catch (Exception e) {
            log.error("Exception While Purging the Booking", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.PURGE_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.PURGE_OPERATION_FAILED, payload),
                EmptyJsonResponse.getEmptyResponseArray());
    }

    public ResponseDto restoreBookings(final String id) {
        try {
            List<Integer> ids = Arrays.stream(id.split(",")).map(Integer::parseInt).toList();

            if (!ids.isEmpty()) {
                ids.forEach(bookingId -> {
                    Booking booking = bookingRepo.findById(bookingId).orElseThrow();
                    booking.setDeletedBy(null);
                    booking.setDeletedDate(null);
                    bookingRepo.save(booking);
                });

                return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.RESTORE_OPERATION_SUCCESS, responseMessageConfig
                                .getStatusMessage(StatusCodes.RESTORE_OPERATION_SUCCESS, payload),
                        EmptyJsonResponse.getEmptyResponseArray());
            }
        } catch (Exception e) {
            log.error("Exception While Restoring the Booking", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.RESTORE_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.RESTORE_OPERATION_FAILED, payload),
                EmptyJsonResponse.getEmptyResponseArray());
    }
}
