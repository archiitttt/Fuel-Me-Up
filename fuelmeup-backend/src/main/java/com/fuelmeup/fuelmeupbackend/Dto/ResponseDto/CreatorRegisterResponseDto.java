package com.fuelmeup.fuelmeupbackend.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorRegisterResponseDto {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String creatorId;
    private String bio;
    private String profileImage;
    private String coverImage;
}
