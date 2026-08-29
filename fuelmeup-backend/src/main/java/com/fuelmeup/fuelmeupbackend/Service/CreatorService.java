package com.fuelmeup.fuelmeupbackend.Service;

import com.fuelmeup.fuelmeupbackend.Dto.RequestDto.CreatorRegisterRequestDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorDashboardDetailsResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorDetailsResponseDto;
import com.fuelmeup.fuelmeupbackend.Dto.ResponseDto.CreatorRegisterResponseDto;
import com.fuelmeup.fuelmeupbackend.Enum.Role;
import com.fuelmeup.fuelmeupbackend.Exception.UserAlreadyExistsException;
import com.fuelmeup.fuelmeupbackend.Mapper.CreatorMapper;
import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Model.User;
import com.fuelmeup.fuelmeupbackend.Repository.CreatorRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatorService {

    private final CreatorRepo creatorRepo;
    private final UserService userService;

    @Transactional
    public CreatorRegisterResponseDto registerCreator(CreatorRegisterRequestDto request, UserDetails userDetails){

        if(userDetails==null){
            throw new AccessDeniedException("Kindly log in or sign up before registering as a creator.");
        }
        User user = userService.findUserByUsername(userDetails.getUsername());
        user.setRole(Role.ROLE_CREATOR);
        if(creatorRepo.existsByUserId(user.getId())){
            throw new UserAlreadyExistsException("User is already a registered creator.");
        }
        Creator creator = CreatorMapper.CreatorRegisterRequestDtoToCreator(request, user);
        creatorRepo.save(creator);
        return CreatorMapper.CreatorToCreatorRegisterResponseDto(creator, user);
    }

    public CreatorDetailsResponseDto creatorDetails(String creatorName){
        if(!creatorRepo.existsCreatorByUserUsername(creatorName)){
            throw new UsernameNotFoundException("Creator with name " + creatorName + " not found.");
        }

        Creator creator = creatorRepo.findByUserUsername(creatorName).orElseThrow(()-> new UsernameNotFoundException("Creator not found."));

        return CreatorMapper.CreatorToCreatorDetailsResponseDto(creator);
    }

    boolean creatorExistsByName(String creatorName){
        return creatorRepo.existsCreatorByUserUsername(creatorName);
    }

    public Creator findCreatorByCreatorName(String creatorName){
        return creatorRepo.findByUserUsername(creatorName).orElseThrow(()-> new UsernameNotFoundException("Creator not found."));
    }


}
