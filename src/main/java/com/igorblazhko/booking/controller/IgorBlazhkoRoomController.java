package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.common.IgorBlazhkoPagedResponse;
import com.igorblazhko.booking.dto.room.IgorBlazhkoRoomRequest;
import com.igorblazhko.booking.dto.room.IgorBlazhkoRoomResponse;
import com.igorblazhko.booking.service.IgorBlazhkoRoomService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class IgorBlazhkoRoomController {

    private final IgorBlazhkoRoomService roomService;

    @GetMapping
    public ResponseEntity<IgorBlazhkoPagedResponse<IgorBlazhkoRoomResponse>> searchRooms(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "pricePerNight") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(roomService.searchRooms(search, city, available, capacity, minPrice, maxPrice, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IgorBlazhkoRoomResponse> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping
    public ResponseEntity<IgorBlazhkoRoomResponse> createRoom(@Valid @RequestBody IgorBlazhkoRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgorBlazhkoRoomResponse> updateRoom(@PathVariable Long id,
                                                              @Valid @RequestBody IgorBlazhkoRoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}