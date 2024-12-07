package com.example.easyfix.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    private Integer id;
    private Integer userId;
    private String userEmail;
    private Integer serviceProviderId;
    private String serviceProviderEmail;
    private String status;
    private String serviceDescription;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Timestamp createdDate;
    private Boolean isDeleted;
    private Timestamp deletedBy;
    private Timestamp deletedDate;
    private Integer serviceId;
    private String address;
    private String serviceName;
}
