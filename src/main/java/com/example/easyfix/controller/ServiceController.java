package com.example.easyfix.controller;

import com.example.easyfix.common.EasyFixConstants;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.service.ServiceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/easyFix/services")
public class ServiceController {

    private final ServiceListService serviceListService;

    @GetMapping("/listServices")
    private ResponseEntity<Object> listServices() {
        ResponseDto responseDto = serviceListService.listServices();
        if (responseDto.getStatus().equals(EasyFixConstants.STATUS_FAILED)) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }
}
