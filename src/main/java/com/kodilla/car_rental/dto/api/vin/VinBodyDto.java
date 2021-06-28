package com.kodilla.car_rental.dto.api.vin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VinBodyDto {

    @JsonProperty("Make")
    private String manufacturer;

    @JsonProperty("Model")
    private String model;

    @JsonProperty("ModelYear")
    private String productYear;

    @JsonProperty("Body Class")
    private String chassisType;

    @JsonProperty("VehicleType")
    private String vehicleType;
}
