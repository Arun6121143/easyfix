package com.example.easyfix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "easy_fix", name = "booking_services")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "service_provider_id")
    private Integer serviceProviderId;

    private String status;

    private String address;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @Column(name = "booking_create_date")
    private Timestamp createdDate;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_date")
    private Timestamp deletedDate;
}
