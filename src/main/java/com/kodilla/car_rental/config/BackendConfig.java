package com.kodilla.car_rental.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BackendConfig {

    @Value("${car.api.endpoint}")
    private String carEndpoint;

    @Value("${rental.api.endpoint}")
    private String rentalEndpoint;

    @Value("${user.api.endpoint}")
    private String userEndpoint;

    @Value("${vin.api.endpoint}")
    private String vinApiEndpoint;

    @Value("${login.api.endpoint}")
    private String loginEndpoint;
}
