package com.fuelmeup.fuelmeupbackend.Mapper;


import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.UserRegisterRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserLoginResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserRegisterResponseDto;
import com.fuelmeup.fuelmeupbackend.Model.User;

public class UserMapper {

    public static User UserRegisterRequestDtoToUser(UserRegisterRequestDto request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static UserRegisterResponseDto UserToUserRegisterResponseDto(User user){
        return UserRegisterResponseDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }

    public static UserLoginResponseDto TokenToUserLoginResponseDto(String token){
        return UserLoginResponseDto.builder()
                .token(token)
                .build();
    }

}
