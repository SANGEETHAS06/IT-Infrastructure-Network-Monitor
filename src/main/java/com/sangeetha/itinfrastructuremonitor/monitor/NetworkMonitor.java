package com.sangeetha.itinfrastructuremonitor.monitor;

import com.sangeetha.itinfrastructuremonitor.entity.Device;
import com.sangeetha.itinfrastructuremonitor.entity.Incident;
import com.sangeetha.itinfrastructuremonitor.repository.DeviceRepository;
import com.sangeetha.itinfrastructuremonitor.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NetworkMonitor {

    private final DeviceRepository deviceRepository;
    private final IncidentRepository incidentRepository;

    public NetworkMonitor(DeviceRepository deviceRepository,
                          IncidentRepository incidentRepository) {
        this.deviceRepository = deviceRepository;
        this.incidentRepository = incidentRepository;
    }

    public void checkAllDevices() {

        List<Device> devices = deviceRepository.findAll();

        for (Device device : devices) {

            try {
                InetAddress address =
                        InetAddress.getByName(device.getIpAddress());

                long startTime = System.currentTimeMillis();

                boolean reachable = address.isReachable(3000);

                long responseTime =
                        System.currentTimeMillis() - startTime;

                if (reachable) {

                    device.setStatus("ONLINE");
                    device.setResponseTime(responseTime);

                } else {

                    device.setStatus("OFFLINE");
                    device.setResponseTime(null);

                    createIncident(device);
                }

            } catch (Exception e) {

                device.setStatus("OFFLINE");
                device.setResponseTime(null);

                createIncident(device);
            }

            device.setLastChecked(LocalDateTime.now());

            deviceRepository.save(device);
        }
    }

    private void createIncident(Device device) {

    boolean alreadyExists =
            incidentRepository.existsByDeviceNameAndStatus(
                    device.getDeviceName(),
                    "OPEN"
            );

    if (alreadyExists) {
        return;
    }

    Incident incident = new Incident();

    incident.setIncidentId(
            "INC-" + System.currentTimeMillis()
    );

    incident.setDeviceName(device.getDeviceName());

    incident.setProblem(
            "Device is unreachable at " + device.getIpAddress()
    );

    incident.setPriority("HIGH");

    incident.setStatus("OPEN");

    incident.setCreatedAt(LocalDateTime.now());

    incidentRepository.save(incident);
}
}
