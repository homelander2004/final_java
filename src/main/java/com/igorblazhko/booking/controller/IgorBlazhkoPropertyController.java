package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.property.IgorBlazhkoPropertyRequest;
import com.igorblazhko.booking.dto.property.IgorBlazhkoPropertyResponse;
import com.igorblazhko.booking.service.IgorBlazhkoPropertyService;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class IgorBlazhkoPropertyController {

    private final IgorBlazhkoPropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<IgorBlazhkoPropertyResponse>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IgorBlazhkoPropertyResponse> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @PostMapping
    public ResponseEntity<IgorBlazhkoPropertyResponse> createProperty(@Valid @RequestBody IgorBlazhkoPropertyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgorBlazhkoPropertyResponse> updateProperty(@PathVariable Long id,
                                                                      @Valid @RequestBody IgorBlazhkoPropertyRequest request) {
        return ResponseEntity.ok(propertyService.updateProperty(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}