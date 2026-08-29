package com.fuelmeup.fuelmeupbackend.Mapper;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.VerifyPaymentRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.VerifyPaymentResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.WebhookResponseDto;
import org.json.JSONObject;


public class PaymentMapper {

    public static JSONObject VerifyPaymentRequestDtoToJSONObject(VerifyPaymentRequestDto requestDto){
        JSONObject object = new JSONObject();
        object.put("razorpay_order_id", requestDto.getRazorpay_order_id());
        object.put("razorpay_payment_id", requestDto.getRazorpay_payment_id());
        object.put("razorpay_signature", requestDto.getRazorpay_signature());
        return object;
    }

    public static VerifyPaymentResponseDto PaymentStatusToVerifyPaymentResponseDto(boolean isValid){
        return VerifyPaymentResponseDto.builder()
                .success(isValid)
                .message(isValid ? "Payment verified." : "Payment failed. Invalid signature.")
                .build();
    }

    public static WebhookResponseDto RazorpayWebhookToWebhookResponseDto(boolean success, String message){
        return WebhookResponseDto.builder()
                .success(success)
                .message(message)
                .build();
    }

}
