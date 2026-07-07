package com.fuelmeup.fuelmeupbackend.Dto.RequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "Username should not be blank")
    @Pattern(regexp = "^[a-z0-9]{4,25}$", message = "Username must contain only lowercase English letters or numbers and must be between 4 and 25 characters.")
    private String username;

    @NotBlank(message = "First Name should not be blank.")
    private String firstName;

    @NotBlank(message = "Last Name should not be blank.")
    private String lastName;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email should not be blank.")
    private String email;

    @NotBlank(message = "Password should not be blank.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{5,}$", message = "Password should be at least 5 characters long, contain a lowercase and uppercase English character, and a special character.")
    private String password;

}
