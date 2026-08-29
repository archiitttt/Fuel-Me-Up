package com.fuelmeup.fuelmeupbackend.Dto.RequestDto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateFuelOrderRequestDto {
    @Min(value = 100, message = "Minimum amount is 1 INR.")
    private Integer amount;
    private String currency="INR";
    private String message="Fueled!";
}
