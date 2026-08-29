package com.fuelmeup.fuelmeupbackend.Controller;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.CreatorRegisterRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorDetailsResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorRegisterResponseDto;
import com.fuelmeup.fuelmeupbackend.Service.CreatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creators")
@RequiredArgsConstructor
@Tag(name = "Creator API", description = "API Endpoints for operations related to creators.")
public class CreatorController {

    private final CreatorService creatorService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/register")
    @Operation(description = "Register an existing user as a creator.")
    public ResponseEntity<CreatorRegisterResponseDto> registerCreator(@Valid @RequestBody CreatorRegisterRequestDto request, @AuthenticationPrincipal UserDetails userDetails){
        CreatorRegisterResponseDto response = creatorService.registerCreator(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{creatorName}")
    @Operation(description = "Fetch creator details from the creator name.")
    public ResponseEntity<CreatorDetailsResponseDto> creatorDetails(@PathVariable String creatorName){
        CreatorDetailsResponseDto response = creatorService.creatorDetails(creatorName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('CREATOR')")
    @GetMapping("/me")
    @Operation(description = "Fetch creator details for creator's dashboard.")
    public ResponseEntity<?> creatorDashboardDetails(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body("");
    }

}
