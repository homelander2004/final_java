package com.igorblazhko.booking.repository;

import com.igorblazhko.booking.entity.IgorBlazhkoUserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IgorBlazhkoUserRepository extends JpaRepository<IgorBlazhkoUserEntity, Long> {

    @Override
    @EntityGraph(attributePaths = "role")
    List<IgorBlazhkoUserEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "role")
    Optional<IgorBlazhkoUserEntity> findById(Long id);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "role")
    Optional<IgorBlazhkoUserEntity> findByEmail(String email);
}