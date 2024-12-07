package com.example.easyfix.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String services;
    private String serviceIds;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Integer vin;
}
