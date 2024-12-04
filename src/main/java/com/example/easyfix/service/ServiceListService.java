package com.example.easyfix.service;

import com.example.easyfix.common.EasyFixConstants;
import com.example.easyfix.common.EasyFixUtil;
import com.example.easyfix.common.EmptyJsonResponse;
import com.example.easyfix.common.StatusCodes;
import com.example.easyfix.config.ResponseMessageConfig;
import com.example.easyfix.dto.LoginDto;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.dto.ServiceDto;
import com.example.easyfix.dto.UserDto;
import com.example.easyfix.model.Services;
import com.example.easyfix.repository.ServiceRepo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceListService implements UserInterfaceService {

    private final ServiceRepo serviceRepo;
    private final ResponseMessageConfig responseMessageConfig;


    public ResponseDto listServices() {
        String payload = "services";
        try {
            List<Services> services = serviceRepo.findAll();

            if (services.isEmpty()) {
                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.EMPTY_LIST,
                        responseMessageConfig.getStatusMessage(StatusCodes.EMPTY_LIST,
                                payload),
                        EmptyJsonResponse.getEmptyResponseArray());
            } else {
                List<ServiceDto> serviceDtoList = new ArrayList<>();
                services.forEach(services1 -> {
                    ServiceDto serviceDto = ServiceDto.builder()
                            .serviceName(services1.getServiceName()).id(services1.getId())
                            .build();
                    serviceDtoList.add(serviceDto);
                });

                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.LIST_OPERATION_SUCCESS,
                        responseMessageConfig.getStatusMessage(StatusCodes.LIST_OPERATION_SUCCESS,
                                payload),
                        serviceDtoList);
            }
        } catch (Exception e) {
            log.error("Exception While fetching service list", e);
        }

        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.LIST_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.LIST_OPERATION_FAILED,
                                payload), EmptyJsonResponse.getEmptyResponseArray());

    }

    @Override
    public ResponseDto getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto registerUser(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseDto loginUser(LoginDto loginDto) {
        return null;
    }

    @Override
    public ResponseDto updateUser(UserDto userDto) {
        return null;
    }


}
