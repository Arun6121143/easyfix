package com.example.easyfix.service;

import com.example.easyfix.dto.LoginDto;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserInterfaceService {

    ResponseDto getById(final Integer id);

    ResponseDto registerUser(final UserDto userDto);

    ResponseDto loginUser(final LoginDto loginDto);

    ResponseDto updateUser(final UserDto userDto);

    ResponseDto listServices();
}
