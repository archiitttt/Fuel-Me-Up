package com.fuelmeup.fuelmeupbackend.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetailsResponseDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
