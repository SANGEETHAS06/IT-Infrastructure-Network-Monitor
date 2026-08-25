# IT Infrastructure & Network Monitoring System

## 📌 Project Overview

The **IT Infrastructure & Network Monitoring System** is a web-based monitoring application developed using **Java, Spring Boot, MySQL, Spring Data JPA, and Thymeleaf**.

The system is designed to monitor IT infrastructure devices using their IP addresses, identify whether devices are reachable or unreachable, record response times, and automatically generate incidents when a device becomes unavailable.

This project demonstrates fundamental concepts of **network monitoring, infrastructure monitoring, troubleshooting, and incident management**.

---

## 🎯 Objectives

- Monitor IT infrastructure devices using IP addresses.
- Check device network connectivity.
- Identify ONLINE and OFFLINE devices.
- Measure device response time.
- Automatically generate incidents for unreachable devices.
- Prevent duplicate OPEN incidents for the same device.
- Track incident priority and status.
- Allow incidents to be resolved through the dashboard.
- Provide a centralized monitoring dashboard.

---

## ✨ Features

### 1. Device Monitoring

The system maintains information about infrastructure devices such as:

- Local Computer
- DNS Server
- Application Server
- Other network devices

Each device contains:

- Device Name
- Device Type
- IP Address
- Current Status
- Response Time
- Last Checked Time

---

### 2. Network Connectivity Monitoring

The application uses Java's `InetAddress` to check whether an IP address is reachable.

```text
IP Address
     ↓
Connectivity Check
     ↓
 ┌───────────────┐
 │               │
ONLINE        OFFLINE
 │               │
 ↓               ↓
Response      Incident
Time          Created
