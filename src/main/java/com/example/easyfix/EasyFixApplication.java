package com.example.easyfix;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasyFixApplication {

    public static void main(String[] args) {
        String activeProfile = System.getenv("ACTIVE_PROFILE");
        Dotenv dotenv = Dotenv.configure().filename(".env." + activeProfile).load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        SpringApplication.run(EasyFixApplication.class, args);
    }

}
