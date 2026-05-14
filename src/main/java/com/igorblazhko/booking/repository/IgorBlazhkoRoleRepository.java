package com.igorblazhko.booking.repository;

import com.igorblazhko.booking.entity.IgorBlazhkoRoleEntity;
import com.igorblazhko.booking.entity.IgorBlazhkoRoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IgorBlazhkoRoleRepository extends JpaRepository<IgorBlazhkoRoleEntity, Long> {

    Optional<IgorBlazhkoRoleEntity> findByName(IgorBlazhkoRoleName name);
}