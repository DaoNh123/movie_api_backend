package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.dto.dtoForLogin.UserDto;
import com.example.backend_sem2.entity.User;
import com.example.backend_sem2.service.interfaceService.AmazonService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public abstract class UserMapper {
    @Autowired
    AmazonService amazonService;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract CreateUserRequest toDto (User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User toEntity (CreateUserRequest createUserRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "avatarUrl", target = "avatarUrl", qualifiedByName = "preSignedImage")
    public abstract UserDto toUserDto(User user);

    @Named("preSignedImage")
    public String preSignedImage(String imageLink){
        return amazonService.createPreSignedPosterUrl(imageLink);
    }
}
