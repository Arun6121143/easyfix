package com.example.easyfix.controller;

import com.example.easyfix.common.EasyFixConstants;
import com.example.easyfix.dto.LoginDto;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.dto.UserDto;
import com.example.easyfix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/easyFix/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    private ResponseEntity<Object> getUserById(@PathVariable final Integer id) {
        ResponseDto responseDto = userService.getById(id);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }

    @PostMapping("/register")
    private ResponseEntity<Object> registerUser(@RequestBody final UserDto userDto) {
        ResponseDto responseDto = userService.registerUser(userDto);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<Object> login(@RequestBody final LoginDto loginDto) {
        ResponseDto responseDto = userService.loginUser(loginDto);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    private ResponseEntity<Object> updateUser(@RequestBody final UserDto userDto) {
        ResponseDto responseDto = userService.updateUser(userDto);
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

}
