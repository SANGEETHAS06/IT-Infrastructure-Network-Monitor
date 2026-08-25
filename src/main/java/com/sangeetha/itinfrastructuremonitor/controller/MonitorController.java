package com.sangeetha.itinfrastructuremonitor.controller;

import com.sangeetha.itinfrastructuremonitor.entity.Device;
import com.sangeetha.itinfrastructuremonitor.entity.Incident;
import com.sangeetha.itinfrastructuremonitor.monitor.NetworkMonitor;
import com.sangeetha.itinfrastructuremonitor.repository.DeviceRepository;
import com.sangeetha.itinfrastructuremonitor.repository.IncidentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MonitorController {

    private final NetworkMonitor networkMonitor;
    private final DeviceRepository deviceRepository;
    private final IncidentRepository incidentRepository;

    public MonitorController(NetworkMonitor networkMonitor,
                             DeviceRepository deviceRepository,
                             IncidentRepository incidentRepository) {
        this.networkMonitor = networkMonitor;
        this.deviceRepository = deviceRepository;
        this.incidentRepository = incidentRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        List<Device> devices = deviceRepository.findAll();
        List<Incident> incidents = incidentRepository.findAll();

        long online = devices.stream()
                .filter(d -> "ONLINE".equals(d.getStatus()))
                .count();

        long offline = devices.stream()
                .filter(d -> "OFFLINE".equals(d.getStatus()))
                .count();

        long openIncidents = incidents.stream()
                .filter(i -> "OPEN".equals(i.getStatus()))
                .count();

        model.addAttribute("devices", devices);
        model.addAttribute("incidents", incidents);
        model.addAttribute("totalDevices", devices.size());
        model.addAttribute("onlineDevices", online);
        model.addAttribute("offlineDevices", offline);
        model.addAttribute("openIncidents", openIncidents);

        return "dashboard";
    }

    @GetMapping("/dashboard/check")
    public String checkDevices() {

        networkMonitor.checkAllDevices();

        return "redirect:/";
    }

    @GetMapping("/incident/resolve/{id}")
    public String resolveIncident(@PathVariable Long id) {

        Incident incident = incidentRepository.findById(id).orElse(null);

        if (incident != null) {
            incident.setStatus("RESOLVED");
            incidentRepository.save(incident);
        }

        return "redirect:/";
    }
}