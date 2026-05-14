package com.igorblazhko.booking.mapper;

import com.igorblazhko.booking.dto.room.IgorBlazhkoRoomResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoRoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgorBlazhkoRoomMapper {

    @Mapping(target = "propertyId", source = "property.id")
    @Mapping(target = "propertyName", source = "property.name")
    @Mapping(target = "city", source = "property.city")
    IgorBlazhkoRoomResponse toResponse(IgorBlazhkoRoomEntity entity);
}