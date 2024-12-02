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
    private String email;
    private String phoneNumber;
    private String role;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
