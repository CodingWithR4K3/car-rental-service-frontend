package com.kodilla.car_rental.client;

import com.kodilla.car_rental.config.BackendConfig;
import com.kodilla.car_rental.dto.RentalDto;
import com.kodilla.car_rental.dto.RentalWithCarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class RentalClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;

    @Autowired
    public RentalClient(RestTemplate restTemplate, BackendConfig backendConfig) {
        this.restTemplate = restTemplate;
        this.backendConfig = backendConfig;
    }

    public List<RentalWithCarDto> getRentals() {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getRentalEndpoint()).build().encode().toUri();
            RentalWithCarDto[] response = restTemplate.getForObject(url, RentalWithCarDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RentalWithCarDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<RentalWithCarDto> getRentalsByUserId(Long userId) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getRentalEndpoint() + "/id/" + userId)
                    .build().encode().toUri();
            RentalWithCarDto[] response = restTemplate.getForObject(url, RentalWithCarDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RentalWithCarDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void createRental(RentalDto rentalDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getRentalEndpoint()).build().encode().toUri();
        restTemplate.postForObject(url, rentalDto, RentalWithCarDto.class);
    }


    public void modifyRental(RentalDto rentalDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getRentalEndpoint()).build().encode().toUri();
        restTemplate.put(url, rentalDto);
    }

    public void closeRental(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getRentalEndpoint() + "/" + id)
                .build().encode().toUri();
        restTemplate.delete(url);
    }
}
