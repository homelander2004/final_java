package com.igorblazhko.booking.mapper;

import com.igorblazhko.booking.dto.booking.IgorBlazhkoBookingResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoBookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgorBlazhkoBookingMapper {

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.fullName")
    @Mapping(target = "paymentId", source = "payment.id")
    @Mapping(target = "status", source = "status")
    IgorBlazhkoBookingResponse toResponse(IgorBlazhkoBookingEntity entity);
}