package com.igorblazhko.booking.mapper;

import com.igorblazhko.booking.dto.user.IgorBlazhkoUserResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgorBlazhkoUserMapper {

    @Mapping(target = "role", source = "role.name")
    IgorBlazhkoUserResponse toResponse(IgorBlazhkoUserEntity entity);
}