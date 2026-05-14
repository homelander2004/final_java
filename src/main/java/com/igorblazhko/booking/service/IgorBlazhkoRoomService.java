package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.common.IgorBlazhkoPagedResponse;
import com.igorblazhko.booking.dto.room.IgorBlazhkoRoomRequest;
import com.igorblazhko.booking.dto.room.IgorBlazhkoRoomResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoRoomEntity;
import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.mapper.IgorBlazhkoRoomMapper;
import com.igorblazhko.booking.repository.IgorBlazhkoRoomRepository;
import com.igorblazhko.booking.specification.IgorBlazhkoRoomSpecification;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoRoomService {

    private final IgorBlazhkoRoomRepository roomRepository;
    private final IgorBlazhkoPropertyService propertyService;
    private final IgorBlazhkoRoomMapper roomMapper;

    public IgorBlazhkoPagedResponse<IgorBlazhkoRoomResponse> searchRooms(String search,
                                                                         String city,
                                                                         Boolean available,
                                                                         Integer capacity,
                                                                         BigDecimal minPrice,
                                                                         BigDecimal maxPrice,
                                                                         int page,
                                                                         int size,
                                                                         String sortBy,
                                                                         String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        var pageResult = roomRepository.findAll(
                IgorBlazhkoRoomSpecification.filter(search, city, available, capacity, minPrice, maxPrice),
                PageRequest.of(page, size, sort)
        );

        return IgorBlazhkoPagedResponse.<IgorBlazhkoRoomResponse>builder()
                .content(pageResult.getContent().stream().map(roomMapper::toResponse).toList())
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }

    public IgorBlazhkoRoomResponse getRoomById(Long id) {
        return roomMapper.toResponse(findRoomEntity(id));
    }

    @Transactional
    public IgorBlazhkoRoomResponse createRoom(IgorBlazhkoRoomRequest request) {
        IgorBlazhkoRoomEntity room = new IgorBlazhkoRoomEntity();
        applyRequest(room, request);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Transactional
    public IgorBlazhkoRoomResponse updateRoom(Long id, IgorBlazhkoRoomRequest request) {
        IgorBlazhkoRoomEntity room = findRoomEntity(id);
        applyRequest(room, request);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.delete(findRoomEntity(id));
    }

    public IgorBlazhkoRoomEntity findRoomEntity(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("Room not found with id: " + id));
    }

    private void applyRequest(IgorBlazhkoRoomEntity room, IgorBlazhkoRoomRequest request) {
        room.setRoomNumber(request.roomNumber());
        room.setRoomType(request.roomType());
        room.setPricePerNight(request.pricePerNight());
        room.setCapacity(request.capacity());
        room.setAvailable(request.available());
        room.setProperty(propertyService.findPropertyEntity(request.propertyId()));
    }
}