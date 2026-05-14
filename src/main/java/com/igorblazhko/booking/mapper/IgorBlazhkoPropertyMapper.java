package com.igorblazhko.booking.mapper;

import com.igorblazhko.booking.dto.property.IgorBlazhkoPropertyResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoPropertyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgorBlazhkoPropertyMapper {

    @Mapping(target = "adminUserId", source = "adminUser.id")
    @Mapping(target = "adminUserName", source = "adminUser.fullName")
    @Mapping(target = "imageFileId", source = "imageFile.id")
    IgorBlazhkoPropertyResponse toResponse(IgorBlazhkoPropertyEntity entity);
}