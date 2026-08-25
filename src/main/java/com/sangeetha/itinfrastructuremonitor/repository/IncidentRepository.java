package com.sangeetha.itinfrastructuremonitor.repository;

import com.sangeetha.itinfrastructuremonitor.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    boolean existsByDeviceNameAndStatus(String deviceName, String status);
}