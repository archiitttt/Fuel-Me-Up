package com.fuelmeup.fuelmeupbackend.Dto.RequestDto;

import lombok.Data;

@Data
public class VerifyPaymentRequestDto {
    private String razorpay_order_id;
    private String razorpay_payment_id;
    private String razorpay_signature;
}
