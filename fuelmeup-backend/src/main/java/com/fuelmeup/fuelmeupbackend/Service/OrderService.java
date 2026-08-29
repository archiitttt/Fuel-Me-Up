package com.fuelmeup.fuelmeupbackend.Service;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.CreateFuelOrderRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreateFuelOrderResponseDto;
import com.fuelmeup.fuelmeupbackend.Enum.PaymentStatus;
import com.fuelmeup.fuelmeupbackend.Exception.OrderNotFoundException;
import com.fuelmeup.fuelmeupbackend.Exception.PaymentException;
import com.fuelmeup.fuelmeupbackend.Mapper.FuelMapper;
import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Model.Fuel;
import com.fuelmeup.fuelmeupbackend.Model.User;
import com.fuelmeup.fuelmeupbackend.Repository.FuelRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final FuelRepo fuelRepo;
    private final CreatorService creatorService;
    private final UserService userService;
    private final RazorpayClient razorpayClient;

    @Transactional
    public CreateFuelOrderResponseDto createOrder(CreateFuelOrderRequestDto requestDto, String creatorName, UserDetails userDetails){
        if(!creatorService.creatorExistsByName(creatorName)){
            throw new UsernameNotFoundException("This creator does not exist.");
        }
        Fuel fuel = null;
        Creator creator = creatorService.findCreatorByCreatorName(creatorName);
        if(userDetails!=null){
            User user = userService.findUserByUsername(userDetails.getUsername());
            fuel = FuelMapper.CreateFuelOrderRequestDtoToFuel(requestDto, creator, user);
        }
        else{
            fuel = FuelMapper.CreateFuelOrderRequestDtoToFuel(requestDto, creator);
        }
        fuelRepo.save(fuel);
        JSONObject orderRequest = FuelMapper.RequestToJsonObject(requestDto, fuel.getFuelId().toString());
        try{
            Order razorpayOrder = razorpayClient.orders.create(orderRequest);
            fuel.setRazorpayOrderId(razorpayOrder.get("id"));
            return FuelMapper.RazorpayOrderToCreateFuelOrderResponseDto(razorpayOrder);
        }
        catch (RazorpayException ex){
            throw new PaymentException(ex.getMessage());
        }
    }

    @Transactional
    @Async
    @Retryable(
            maxRetries = 5, multiplier = 2
    )
    public void updateOrderDetails(String razorpayOrderId, String razorpayPaymentId, boolean success){
        Fuel order = fuelRepo.findByRazorpayOrderId(razorpayOrderId).orElseThrow(() -> new OrderNotFoundException("Fuel Order with ID: " + razorpayOrderId + " does not exist."));
        order.setRazorpayPaymentId(razorpayPaymentId);
        order.setPaymentStatus(success ? PaymentStatus.SUCCESSFUL : PaymentStatus.FAILED);
    }

}
