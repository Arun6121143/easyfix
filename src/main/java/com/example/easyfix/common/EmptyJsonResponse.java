package com.example.easyfix.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public final class EmptyJsonResponse {

    private EmptyJsonResponse() {

    }

    private static EmptyJsonResponse emptyJsonResponse;

    private static final String[] emptyJsonResponseArray = {};

    public static EmptyJsonResponse getEmptyJsonResponse() {
        if (emptyJsonResponse == null) {
            emptyJsonResponse = new EmptyJsonResponse();
        }
        return emptyJsonResponse;
    }

    public static String[] getEmptyResponseArray() {
        return emptyJsonResponseArray;
    }

}
