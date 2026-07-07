package com.fuelmeup.fuelmeupbackend.Controller;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.UserLoginRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.UserRegisterRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserLoginResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserRegisterResponseDto;
import com.fuelmeup.fuelmeupbackend.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "API Endpoints for operations related to users.")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(description = "Register a user to the platform.")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto request){
        UserRegisterResponseDto response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(description = "Login a user to the platform using credentials.")
    public ResponseEntity<UserLoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto request){
        UserLoginResponseDto response = userService.loginUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
