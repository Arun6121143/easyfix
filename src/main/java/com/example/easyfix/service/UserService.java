package com.example.easyfix.service;

import com.example.easyfix.common.EasyFixConstants;
import com.example.easyfix.common.EasyFixUtil;
import com.example.easyfix.common.EmptyJsonResponse;
import com.example.easyfix.common.StatusCodes;
import com.example.easyfix.config.ResponseMessageConfig;
import com.example.easyfix.dto.LoginDto;
import com.example.easyfix.dto.ResponseDto;
import com.example.easyfix.dto.UserDto;
import com.example.easyfix.model.Services;
import com.example.easyfix.model.User;
import com.example.easyfix.repository.ServiceRepo;
import com.example.easyfix.repository.UserRepo;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserInterfaceService {

    private final UserRepo userRepo;
    private final ResponseMessageConfig responseMessageConfig;
    private final ServiceRepo serviceRepo;
    private final String payload = "user";
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final String service = "service provider";

    private final JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public ResponseDto getById(final Integer id) {
        try {
            Optional<User> user = userRepo.findById(id);

            return user.map(user1 -> {
                UserDto userDto = UserDto.builder()
                        .id(user1.getId()).email(user1.getEmail())
                        .role(user1.getRole()).createdDate(user1.getCreatedDate())
                        .password(user1.getPassword()).name(user1.getName())
                        .services(String.join(",", user1.getServices()))
                        .build();
                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.LIST_OPERATION_SUCCESS,
                        responseMessageConfig.getStatusMessage(StatusCodes.LIST_OPERATION_SUCCESS, payload),
                        List.of(userDto));
            }).orElseGet(() -> EasyFixUtil.getCustomResponse(
                    EasyFixConstants.STATUS_SUCCESS,
                    StatusCodes.EMPTY_LIST,
                    responseMessageConfig.getStatusMessage(StatusCodes.EMPTY_LIST, payload),
                    EmptyJsonResponse.getEmptyResponseArray()
            ));
        } catch (Exception e) {
            log.error("Exception While fetching the user details", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.LIST_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.LIST_OPERATION_FAILED,
                                payload), EmptyJsonResponse.getEmptyResponseArray());
    }

    @Override
    public ResponseDto registerUser(final UserDto userDto) {
        try {
            Boolean isUserExists = userRepo.existsByEmail(userDto.getEmail());

            if (Boolean.TRUE.equals(isUserExists)) {
                return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.EMAIL_ALREADY_EXISTS, responseMessageConfig
                                .getStatusMessage(StatusCodes.EMAIL_ALREADY_EXISTS),
                        EmptyJsonResponse.getEmptyJsonResponse());
            }
            Set<String> services = userDto.getRole().equalsIgnoreCase("service_provider") ?
                    getServiceNames(userDto.getServices()) : null;
            User user = User.builder()
                    .name(userDto.getName()).email(userDto.getEmail())
                    .createdDate(Timestamp.from(Instant.now())).password(passwordEncoder.encode(userDto.getPassword()))
                    .role(userDto.getRole().toUpperCase())
                    .services(services)
                    .build();
            userRepo.save(user);
            return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                    StatusCodes.REGISTER_OPERATION_SUCCESS, responseMessageConfig
                            .getStatusMessage(StatusCodes.REGISTER_OPERATION_SUCCESS, payload),
                    EmptyJsonResponse.getEmptyJsonResponse());
        } catch (Exception e) {
            log.error("Exception While registering the user", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.REGISTER_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.REGISTER_OPERATION_FAILED, payload),
                EmptyJsonResponse.getEmptyJsonResponse());
    }

    private Set<String> getServiceNames(String services) {
        if (services != null && !services.isEmpty()) {
            HashSet<String> serviceNames = new HashSet<>();
            List<Integer> serviceIds = Arrays.stream(services.split(","))
                    .map(Integer::parseInt)
                    .toList();
            List<Services> servicesList = serviceRepo.findAllById(serviceIds);
            servicesList.forEach(services1 -> {
                serviceNames.add(services1.getServiceName());
            });

            return serviceNames;
        }
        return new HashSet<>();
    }

    @Override
    public ResponseDto loginUser(final LoginDto loginDto) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.LOGIN_OPERATION_SUCCESS, responseMessageConfig
                                .getStatusMessage(StatusCodes.LOGIN_OPERATION_SUCCESS, payload),
                        jwtService.generateToken(loginDto.getEmail()));
            }
        } catch (Exception e) {
            log.error("Exception While user login", e);
        }

        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.LOGIN_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.LOGIN_OPERATION_FAILED, payload),
                EmptyJsonResponse.getEmptyJsonResponse());
    }

    @Override
    public ResponseDto updateUser(final UserDto userDto) {
        try {
            userRepo.findById(userDto.getId()).ifPresent(user -> {
                Set<String> services = userDto.getRole().equalsIgnoreCase("service_provider") ?
                        getServiceNames(userDto.getServices()) : null;
                user.setEmail(userDto.getEmail());
                user.setUpdatedDate(Timestamp.from(Instant.now()));
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setName(userDto.getName());
                user.setRole(userDto.getRole().toUpperCase());
                user.setServices(services);
                userRepo.save(user);
            });
            return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_SUCCESS,
                    StatusCodes.UPDATE_OPERATION_SUCCESS, responseMessageConfig
                            .getStatusMessage(StatusCodes.UPDATE_OPERATION_SUCCESS, payload),
                    EmptyJsonResponse.getEmptyJsonResponse());

        } catch (Exception e) {
            log.error("Exception While Updating The User", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.UPDATE_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.UPDATE_OPERATION_FAILED, payload),
                EmptyJsonResponse.getEmptyJsonResponse());
    }

    @Override
    public ResponseDto listServices() {
        return null;
    }

    @Override
    public ResponseDto listItemsServiceProviders() {
        try {
            List<User> serviceProviderList = userRepo.findByRole();
            if (serviceProviderList == null || serviceProviderList.isEmpty()) {
                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.EMPTY_LIST,
                        responseMessageConfig.getStatusMessage(StatusCodes.EMPTY_LIST
                                , service),
                        EmptyJsonResponse.getEmptyResponseArray());
            } else {
                JSONObject jsonObject = new JSONObject();
                serviceProviderList.forEach(services -> {
                    if (services.getServices() != null && !services.getServices().isEmpty()) {
                        String[] serviceList = String.join(",", services.getServices()).split(",");
                        JSONObject serviceProviderDetails = new JSONObject();
                        serviceProviderDetails.put("id", services.getId());
                        serviceProviderDetails.put("name", services.getName());
                        serviceProviderDetails.put("role", services.getRole());
                        serviceProviderDetails.put("createdDate", services.getCreatedDate());
                        for (String service1 : serviceList) {
                            String serviceName = service1.trim().toUpperCase();
                            if (!jsonObject.has(serviceName)) {
                                jsonObject.put(serviceName, new JSONArray());
                            }
                            jsonObject.getJSONArray(serviceName)
                                    .put(serviceProviderDetails);
                        }
                    }
                });
                return EasyFixUtil.getCustomResponse(
                        EasyFixConstants.STATUS_SUCCESS,
                        StatusCodes.LIST_OPERATION_SUCCESS,
                        responseMessageConfig.getStatusMessage(StatusCodes.LIST_OPERATION_SUCCESS
                                , service),
                        Collections.singletonList(jsonObject.toMap()));
            }
        } catch (Exception e) {
            log.error("Exception While Fetching service provider list", e);
        }
        return EasyFixUtil.getCustomResponse(EasyFixConstants.STATUS_FAILED,
                StatusCodes.LIST_OPERATION_FAILED, responseMessageConfig
                        .getStatusMessage(StatusCodes.LIST_OPERATION_FAILED,
                                service), EmptyJsonResponse.getEmptyResponseArray());
    }

}
