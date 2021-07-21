package com.kodilla.car_rental.client.api.vin;

import com.kodilla.car_rental.config.BackendConfig;
import com.kodilla.car_rental.dto.api.vin.VinApiDto;
import com.kodilla.car_rental.dto.api.vin.VinDecodedDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Optional.ofNullable;

@Component
public class VinApiClient {
    private final RestTemplate restTemplate;
    private final BackendConfig backEndConfiguration;

    public VinApiClient(RestTemplate restTemplate, BackendConfig backEndConfiguration) {
        this.restTemplate = restTemplate;
        this.backEndConfiguration = backEndConfiguration;
    }

    public VinDecodedDto decodeVinNumber(VinApiDto vinApiDto) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(backEndConfiguration.getVinApiEndpoint() + "/" + vinApiDto.getVinNumber())
                    .build().encode().toUri();
            VinDecodedDto response = restTemplate.getForObject(url, VinDecodedDto.class);
            return ofNullable(response).orElse(new VinDecodedDto());
        } catch (RestClientException e) {
            return new VinDecodedDto();
        }
    }
}
