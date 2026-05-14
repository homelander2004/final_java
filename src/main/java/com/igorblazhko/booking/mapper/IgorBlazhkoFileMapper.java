package com.igorblazhko.booking.mapper;

import com.igorblazhko.booking.dto.file.IgorBlazhkoFileResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoStoredFileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IgorBlazhkoFileMapper {

    IgorBlazhkoFileResponse toResponse(IgorBlazhkoStoredFileEntity entity);
}