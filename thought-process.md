# Thought Process and Implementation Overview

## **Understanding the Task**

After reviewing the project requirements, the following key considerations were identified:

### **REST Service Creation**
- **GET Endpoint**: Implemented a GET endpoint `/api/verve/accept`.
- **POST Endpoint**: Extended functionality with a POST endpoint.

### **Query Parameters**
- `id` (mandatory integer)
- `endpoint` (optional string)

### **High Throughput**
- Ensured the service can handle at least 10K requests per second.

### **Response Handling**
- Return `"ok"` if the request is successful.
- Return `"failed"` if the request has duplicate IDs or encounters errors.
- Log unique request counts every minute.

### **Key Functional Enhancements**
- **ID De-duplication**: Ensured unique ID storage.
- **Distributed Streams Integration**: Used distributed messaging systems.
- **Concurrency & Thread Safety**: Managed concurrent requests efficiently.

---

## **Design and Implementation Approach**

### **1. Project Setup**
- **Framework**: Spring Boot (Maven-based project).
- **Dependencies**: Included necessary dependencies such as Spring Web, Redis, Kafka, and Zookeeper.

### **2. Architecture & Design Pattern**
- **Design Pattern**: Adopted MVC (Model-View-Controller) for better separation of concerns.
- **Project Layers**:
  - **Controller**: Handles API requests.
  - **Service Layer**: Implements business logic.
  - **Repository Layer**: Interacts with Redis and Kafka.

### **3. Key Technologies Used**
- **Redis**: Used for storing unique IDs to ensure ID de-duplication.
- **Kafka & Zookeeper**: Implemented for distributed streaming services and asynchronous messaging.

### **4. Implementation Steps**
- **Created the Spring Boot Application** with required configurations.
- **Defined Endpoints**:
  - **GET endpoint** `/api/verve/accept` with query parameters.
  - **POST endpoint** for future extensibility.
- **ID Management**:
  - Stored IDs in Redis to check for uniqueness.
  - Logged counts to Kafka for distributed processing.
- **Request Processing**:
  - Managed concurrent requests with thread-safe collections.
  - Scheduled tasks logged unique counts every minute.

### **5. Testing and Validation**
- **Browser & Postman Tests**: Verified basic request-response flows.
- **JMeter Load Testing**:
  - Created a test plan in JMeter.
  - Simulated 10K+ requests per second.
  - Observed and validated system performance and log entries.

---
