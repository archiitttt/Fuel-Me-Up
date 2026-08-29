package com.fuelmeup.fuelmeupbackend.Controller;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.CreateFuelOrderRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreateFuelOrderResponseDto;
import com.fuelmeup.fuelmeupbackend.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "API Endpoints for operations related to orders.")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create/{creatorName}")
    @Operation(description = "Create an order to fuel a creator.")
    public ResponseEntity<CreateFuelOrderResponseDto> createOrder(@Valid @RequestBody CreateFuelOrderRequestDto request, @PathVariable String creatorName, @AuthenticationPrincipal UserDetails userDetails){
        CreateFuelOrderResponseDto response = orderService.createOrder(request, creatorName, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
