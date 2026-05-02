# Bajaj Coding Assessment - Backend & Logging Integration

This repository contains the backend implementations for the assessment, including the Notification Service and Vehicle Maintenance Scheduler, both fully integrated with the required Logging Middleware.

## API Testing Screenshots

Below are the test executions from Postman showing successful interactions with both the local backend services and the remote evaluation server.

### 1. Remote Evaluation Server Logging
This demonstrates the `RemoteLogger` middleware successfully authenticating and dispatching a log to the remote evaluation server (`http://20.207.122.201/evaluation-service/logs`), receiving a `201 Created` response.

![Remote Logging Success](/Users/sathwik/Development/RA2311003020748/screenshots/Screenshot 2026-05-02 at 11.43.26 AM.png)

### 2. Notification Service API
This shows the local Notification service successfully processing a POST request to `http://localhost:8081/notifications` and returning the generated record with a `200 OK` response.

![Notification API Success](screenshots/Screenshot 2026-05-02 at 12.13.29 PM.png)

### 3. Vehicle Maintenance Scheduler API
This shows the local Vehicle maintenance scheduler successfully handling a POST request to `http://localhost:8081/api/vehicles`, registering the vehicle, and returning the object with a `200 OK` response.

![Vehicle Scheduler Success](screenshots/Screenshot 2026-05-02 at 12.32.08 PM.png)
