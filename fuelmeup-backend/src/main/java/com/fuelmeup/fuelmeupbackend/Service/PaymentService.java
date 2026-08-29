package com.fuelmeup.fuelmeupbackend.Service;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.VerifyPaymentRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.VerifyPaymentResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.WebhookResponseDto;
import com.fuelmeup.fuelmeupbackend.Exception.PaymentException;
import com.fuelmeup.fuelmeupbackend.Mapper.PaymentMapper;
import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Repository.CreatorRepo;
import com.fuelmeup.fuelmeupbackend.Repository.FuelRepo;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderService orderService;
    private final FuelRepo fuelRepo;

    @Value("${razorpay.key-secret}")
    private String razorpaySecret;

    @Value("${razorpay.webhook-secret}")
    private String razorpayWebhookSecret;

    public VerifyPaymentResponseDto verifyPayment(VerifyPaymentRequestDto request){
        try{
            JSONObject options = PaymentMapper.VerifyPaymentRequestDtoToJSONObject(request);
            boolean isValid = Utils.verifyPaymentSignature(options, razorpaySecret);
            return PaymentMapper.PaymentStatusToVerifyPaymentResponseDto(isValid);
        } catch (RazorpayException e) {
            throw new PaymentException(e.getMessage());
        }
    }

    public WebhookResponseDto handleRazorpayWebhook(String payload, String signature){
        try{
            boolean isValid = Utils.verifyWebhookSignature(payload, signature, razorpayWebhookSecret);
            if(isValid){
                JSONObject object = new JSONObject(payload);
                String event = object.getString("event");
                String orderId = object.getJSONObject("payload")
                        .getJSONObject("payment")
                        .getJSONObject("entity")
                        .getString("order_id");
                String paymentId = object.getJSONObject("payload")
                        .getJSONObject("payment")
                        .getJSONObject("entity")
                        .getString("id");
                if(event.equals("payment.captured")){
                    orderService.updateOrderDetails(orderId, paymentId, true);
                    return PaymentMapper.RazorpayWebhookToWebhookResponseDto(true, "Payment Successful.");
                }
                else{
                    orderService.updateOrderDetails(orderId, paymentId, false);
                    return PaymentMapper.RazorpayWebhookToWebhookResponseDto(false, "Payment failed.");
                }
            }
            else{
                return PaymentMapper.RazorpayWebhookToWebhookResponseDto(false, "Payment failed. Invalid signature.");
            }
        } catch (RazorpayException e) {
            throw new PaymentException(e.getMessage());
        }
    }

    public BigDecimal getCreatorEarnings(Creator creator){
        return fuelRepo.getTotalEarningsByCreatorId(creator.getCreatorId());
    }

}
