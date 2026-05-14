package com.igorblazhko.booking.mapper;

import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoPaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgorBlazhkoPaymentMapper {

    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "status", source = "status")
    IgorBlazhkoPaymentResponse toResponse(IgorBlazhkoPaymentEntity entity);
}