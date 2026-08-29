package com.fuelmeup.fuelmeupbackend.Mapper;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.CreateFuelOrderRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreateFuelOrderResponseDto;
import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Model.Fuel;
import com.fuelmeup.fuelmeupbackend.Model.User;
import com.razorpay.Order;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class FuelMapper {

    public static JSONObject RequestToJsonObject(CreateFuelOrderRequestDto requestDto, String fuelId){
        JSONObject object = new JSONObject();
        object.put("amount", requestDto.getAmount()*100);
        object.put("currency", requestDto.getCurrency());
        object.put("receipt", fuelId);
        return object;
    }

    public static Fuel CreateFuelOrderRequestDtoToFuel(CreateFuelOrderRequestDto requestDto, Creator creator){
        Fuel fuel = new Fuel();
        fuel.setCreator(creator);
        fuel.setAmount(requestDto.getAmount());
        fuel.setMessage(requestDto.getMessage());
        fuel.setCreatedAt(LocalDateTime.now());
        return fuel;
    }

    public static Fuel CreateFuelOrderRequestDtoToFuel(CreateFuelOrderRequestDto requestDto, Creator creator, User user){
        Fuel fuel = new Fuel();
        fuel.setCreator(creator);
        fuel.setUser(user);
        fuel.setAmount(requestDto.getAmount());
        fuel.setMessage(requestDto.getMessage());
        fuel.setCreatedAt(LocalDateTime.now());
        return fuel;
    }

    public static CreateFuelOrderResponseDto RazorpayOrderToCreateFuelOrderResponseDto(Order order){
        return CreateFuelOrderResponseDto.builder()
                .id(order.get("id"))
                .amount(order.get("amount"))
                .amountDue(order.get("amount_due"))
                .amountPaid(order.get("amount_paid"))
                .currency(order.get("currency"))
                .attempts(order.get("attempts"))
                .receipt(order.get("receipt"))
                .createdAt(order.get("created_at"))
                .build();
    }

}
