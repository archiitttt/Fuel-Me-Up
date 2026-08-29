package com.fuelmeup.fuelmeupbackend.Service;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.UserLoginRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.UserRegisterRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserLoginResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserProfileDetailsResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.UserRegisterResponseDto;
import com.fuelmeup.fuelmeupbackend.Enum.Role;
import com.fuelmeup.fuelmeupbackend.Exception.UserAlreadyExistsException;
import com.fuelmeup.fuelmeupbackend.Mapper.UserMapper;
import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Model.User;
import com.fuelmeup.fuelmeupbackend.Repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto request){
        if(userRepo.existsByEmail(request.getEmail()) || userRepo.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("User with this email or username already exists.");
        }
        User user = UserMapper.UserRegisterRequestDtoToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return UserMapper.UserToUserRegisterResponseDto(user);
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto){
        if(!userRepo.existsByUsername(requestDto.getUsername())){
            throw new UsernameNotFoundException("User with username " + requestDto.getUsername() + " does not exist.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(requestDto.getUsername());

        String token = jwtService.generateToken(user);

        return UserMapper.TokenToUserLoginResponseDto(token);
    }

    public User findUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User with username " + username + " not found."));
    }

    public UserProfileDetailsResponseDto userProfileDetails(UserDetails userDetails){
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User with username " + userDetails.getUsername() + " does not exist."));
        return UserMapper.UserToUserProfileDetailsResponseDto(user);
    }

}
