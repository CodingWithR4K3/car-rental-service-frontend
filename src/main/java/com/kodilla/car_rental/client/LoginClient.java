package com.kodilla.car_rental.client;

import com.kodilla.car_rental.config.BackendConfig;
import com.kodilla.car_rental.dto.LoginDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class LoginClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backEndConfiguration;

    public LoginClient(RestTemplate restTemplate, BackendConfig backEndConfiguration) {
        this.restTemplate = restTemplate;
        this.backEndConfiguration = backEndConfiguration;
    }

    public Boolean isLoginRegistered(LoginDto loginDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backEndConfiguration.getLoginEndpoint() + "/is_already_registered")
                .queryParam("email", loginDto.getEmail())
                .queryParam("password", loginDto.getPassword())
                .build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }
}
