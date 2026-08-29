package com.fuelmeup.fuelmeupbackend.Controller;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.VerifyPaymentRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.VerifyPaymentResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.WebhookResponseDto;
import com.fuelmeup.fuelmeupbackend.Service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "API Endpoints for operations related to payments.")
public class PaymentController{

    private final PaymentService paymentService;

    @PostMapping("/verify")
    @Operation(description = "Verify payment made by the client.")
    public ResponseEntity<VerifyPaymentResponseDto> verifyPayment(@Valid @RequestBody VerifyPaymentRequestDto request){
        VerifyPaymentResponseDto response = paymentService.verifyPayment(request);
        return ResponseEntity.status(response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/razorpay-webhook")
    @Operation(description = "Handle webhooks from Razorpay.")
    public ResponseEntity<WebhookResponseDto> handleWebhook(@RequestBody String payload, @RequestHeader("X-Razorpay-Signature") String signature){
        WebhookResponseDto response = paymentService.handleRazorpayWebhook(payload, signature);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
