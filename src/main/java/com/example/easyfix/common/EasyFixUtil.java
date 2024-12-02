package com.example.easyfix.common;

import com.example.easyfix.dto.ResponseDto;

public class EasyFixUtil {

    private EasyFixUtil() {

    }

    public static ResponseDto getCustomResponse(final String statusMessage, final int statusCode,
                                                final String textMessage, final Object responseData) {

        ResponseDto response = new ResponseDto();
        response.setStatus(statusMessage);
        response.setStatusCode(statusCode);
        response.setStatusMessage(textMessage);
        if (responseData != null) {
            response.setResponseData(responseData);
        }
        return response;
    }
}
