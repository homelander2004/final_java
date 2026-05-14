package com.igorblazhko.booking.repository;

import com.igorblazhko.booking.entity.IgorBlazhkoRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IgorBlazhkoRoomRepository extends JpaRepository<IgorBlazhkoRoomEntity, Long>, JpaSpecificationExecutor<IgorBlazhkoRoomEntity> {
}