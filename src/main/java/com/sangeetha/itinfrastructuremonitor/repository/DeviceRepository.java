package com.sangeetha.itinfrastructuremonitor.repository;

import com.sangeetha.itinfrastructuremonitor.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}