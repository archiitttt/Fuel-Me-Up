package com.fuelmeup.fuelmeupbackend.Mapper;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.CreatorRegisterRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorDetailsResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorRegisterResponseDto;
import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Model.User;

public class CreatorMapper {

    public static Creator CreatorRegisterRequestDtoToCreator(CreatorRegisterRequestDto request, User userRef){
        Creator creator = new Creator();
        creator.setUser(userRef);
        creator.setBio(request.getBio());
        creator.setProfileImage(request.getProfileImage());
        creator.setCoverImage(request.getCoverImage());
        return creator;
    }

    public static CreatorRegisterResponseDto CreatorToCreatorRegisterResponseDto(Creator creator, User user){
        return CreatorRegisterResponseDto.builder()
                .userId(user.getId().toString())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .creatorId(creator.getCreatorId().toString())
                .bio(creator.getBio())
                .profileImage(creator.getProfileImage())
                .coverImage(creator.getCoverImage())
                .build();
    }

    public static CreatorDetailsResponseDto CreatorToCreatorDetailsResponseDto(Creator creator){
        return CreatorDetailsResponseDto.builder()
                .email(creator.getUser().getEmail())
                .creatorId(creator.getCreatorId().toString())
                .username(creator.getUser().getUsername())
                .firstName(creator.getUser().getFirstName())
                .lastName(creator.getUser().getLastName())
                .bio(creator.getBio())
                .profileImage(creator.getProfileImage())
                .coverImage(creator.getCoverImage())
                .build();
    }

}
