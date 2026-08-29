package com.fuelmeup.fuelmeupbackend.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFuelOrderResponseDto {
    private String id;
    private Integer amount;
    private Integer amountDue;
    private Integer amountPaid;
    private Integer attempts;
    private String currency;
    private String receipt;
    private Date createdAt;
}
