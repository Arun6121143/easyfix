package com.example.easyfix.config;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@Slf4j
public class ResponseMessageConfig extends ResourceBundleMessageSource {

    private String[] emptyArray = {};

    private String getStatusMessage(final int statusCode, final @Nullable String... replacementValues) {
        String message = String.valueOf(statusCode);
        try {
            message = getMessage(message, replacementValues, Locale.getDefault());
        } catch (Exception e) {
            log.error("Exception while fetching the message for the status code: {}", statusCode, e);
        }
        return message;
    }

    private String getStatusMessages(final int statusCode) {
        return getStatusMessage(statusCode, emptyArray);
    }

    @PostConstruct
    void setMessageSource() {
        this.setBasename("response-messages");
        this.setUseCodeAsDefaultMessage(true);
    }


}
