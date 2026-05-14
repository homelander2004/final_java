package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.property.IgorBlazhkoPropertyRequest;
import com.igorblazhko.booking.dto.property.IgorBlazhkoPropertyResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoPropertyEntity;
import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.mapper.IgorBlazhkoPropertyMapper;
import com.igorblazhko.booking.repository.IgorBlazhkoPropertyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoPropertyService {

    private final IgorBlazhkoPropertyRepository propertyRepository;
    private final IgorBlazhkoUserService userService;
    private final IgorBlazhkoFileStorageService fileStorageService;
    private final IgorBlazhkoPropertyMapper propertyMapper;

    public List<IgorBlazhkoPropertyResponse> getAllProperties() {
        return propertyRepository.findAll().stream().map(propertyMapper::toResponse).toList();
    }

    public IgorBlazhkoPropertyResponse getPropertyById(Long id) {
        return propertyMapper.toResponse(findPropertyEntity(id));
    }

    @Transactional
    public IgorBlazhkoPropertyResponse createProperty(IgorBlazhkoPropertyRequest request) {
        IgorBlazhkoPropertyEntity property = new IgorBlazhkoPropertyEntity();
        applyRequest(property, request);
        return propertyMapper.toResponse(propertyRepository.save(property));
    }

    @Transactional
    public IgorBlazhkoPropertyResponse updateProperty(Long id, IgorBlazhkoPropertyRequest request) {
        IgorBlazhkoPropertyEntity property = findPropertyEntity(id);
        applyRequest(property, request);
        return propertyMapper.toResponse(propertyRepository.save(property));
    }

    @Transactional
    public void deleteProperty(Long id) {
        propertyRepository.delete(findPropertyEntity(id));
    }

    public IgorBlazhkoPropertyEntity findPropertyEntity(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("Property not found with id: " + id));
    }

    private void applyRequest(IgorBlazhkoPropertyEntity property, IgorBlazhkoPropertyRequest request) {
        property.setName(request.name());
        property.setCity(request.city());
        property.setAddress(request.address());
        property.setDescription(request.description());
        property.setAdminUser(userService.findUserEntity(request.adminUserId()));
        property.setImageFile(request.imageFileId() == null ? null : fileStorageService.findFileEntity(request.imageFileId()));
    }
}