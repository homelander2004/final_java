package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.file.IgorBlazhkoFileResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoStoredFileEntity;
import com.igorblazhko.booking.exception.IgorBlazhkoBadRequestException;
import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.mapper.IgorBlazhkoFileMapper;
import com.igorblazhko.booking.repository.IgorBlazhkoStoredFileRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoFileStorageService {

    private final IgorBlazhkoStoredFileRepository storedFileRepository;
    private final IgorBlazhkoFileMapper fileMapper;

    @Value("${igorblazhko.file-storage.upload-dir}")
    private String uploadDir;

    @Transactional
    public IgorBlazhkoFileResponse uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IgorBlazhkoBadRequestException("Uploaded file is empty");
        }
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            String storageFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(storageFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            IgorBlazhkoStoredFileEntity storedFile = new IgorBlazhkoStoredFileEntity();
            storedFile.setOriginalFileName(file.getOriginalFilename());
            storedFile.setStorageFileName(storageFileName);
            storedFile.setFilePath(filePath.toString());
            storedFile.setContentType(file.getContentType() == null ? "application/octet-stream" : file.getContentType());
            storedFile.setSize(file.getSize());
            return fileMapper.toResponse(storedFileRepository.save(storedFile));
        } catch (IOException exception) {
            throw new IgorBlazhkoBadRequestException("Failed to store file");
        }
    }

    public Resource downloadFile(Long id) {
        try {
            IgorBlazhkoStoredFileEntity file = findFileEntity(id);
            Resource resource = new UrlResource(Path.of(file.getFilePath()).toUri());
            if (!resource.exists()) {
                throw new IgorBlazhkoResourceNotFoundException("File content not found for id: " + id);
            }
            return resource;
        } catch (IOException exception) {
            throw new IgorBlazhkoBadRequestException("Failed to read file");
        }
    }

    public IgorBlazhkoStoredFileEntity findFileEntity(Long id) {
        return storedFileRepository.findById(id)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("File not found with id: " + id));
    }
}