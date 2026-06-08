# 💊 Web-Based Pharmacy Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Spring_Boot-3-success?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/Database-SQL_Server-blue?style=for-the-badge&logo=microsoftsqlserver" />
  <img src="https://img.shields.io/badge/Architecture-MVC-important?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Design_Pattern-Observer-yellow?style=for-the-badge" />
</p>

<p align="center">
  <b>A modern pharmacy management solution built with Java & Spring Boot</b><br>
  Designed to automate medicine inventory, prescription handling, customer management, and pharmacy operations efficiently.
</p>

---

# 📌 Project Overview

The **Web-Based Pharmacy Management System** is a full-stack enterprise-style web application developed as a **Software Engineering group project** by a 6-member team.

The system was designed to improve pharmacy operations by automating:

* Medicine inventory management
* Stock monitoring
* Expiry tracking
* Prescription handling
* Customer management
* Order processing
* Inventory alerts

The application follows:

* ✅ MVC Architecture
* ✅ 3-Tier Layered Architecture
* ✅ SOLID Principles
* ✅ Object-Oriented Programming Concepts
* ✅ Design Patterns for scalability and maintainability

---

# 🚀 Key Features

## 🔹 Medicine Management

* Add, update, delete, and search medicines
* Real-time stock quantity management
* Medicine category management
* Manufacturer and expiry tracking
* Low-stock threshold monitoring

## 🔹 Intelligent Inventory Monitoring

* Automatic low-stock detection
* Out-of-stock identification
* Expired medicine tracking
* Expiring-soon medicine alerts

## 🔹 Observer Design Pattern Alerts

* Real-time inventory notifications
* Decoupled alert management system
* Automatic alert triggering when stock changes

## 🔹 User & Customer Management

* Secure user authentication
* Role-based authorization using Spring Security
* Customer registration and profile management

## 🔹 Prescription Handling

* Prescription upload functionality
* Prescription review and approval workflow

## 🔹 Sales & Billing

* Order management system
* Dynamic discount calculation using Strategy Pattern
* Invoice generation

## 🔹 Responsive User Interface

* Modern dashboard design
* Responsive layout using Bootstrap 5
* Status badges and dashboard analytics
* User-friendly pharmacist/admin interface

---

# 🏗️ System Architecture

The project follows a **3-Tier Layered MVC Architecture**.

```text
Presentation Layer
│
├── Thymeleaf Templates
├── HTML/CSS/Bootstrap
│
Business Logic Layer
│
├── Controllers
├── Services
├── Design Patterns
│     ├── Observer Pattern
│     └── Strategy Pattern
│
Data Access Layer
│
├── Spring Data JPA
├── Hibernate ORM
└── Microsoft SQL Server
```

---

# 🧠 Design Patterns Used

## 🔔 Observer Design Pattern

Used for automatic inventory alert generation.

### Purpose

Notify alert systems automatically whenever medicine inventory changes.

### Implementation

* `MedicineService` → Subject
* Alert classes → Observers

### Features

* Low-stock alerts
* Expiry alerts
* Decoupled architecture
* Easier scalability

---

## 🎯 Strategy Design Pattern

Used for dynamic discount calculation during billing.

### Supported Discount Strategies

* Senior citizen discounts
* Loyalty customer discounts
* Standard pricing

---

# 🛠️ Technology Stack

| Category             | Technology           |
| -------------------- | -------------------- |
| Programming Language | Java 17              |
| Backend Framework    | Spring Boot 3        |
| Security             | Spring Security      |
| ORM Framework        | Hibernate            |
| Persistence Layer    | Spring Data JPA      |
| Frontend             | Thymeleaf            |
| UI Framework         | Bootstrap 5          |
| Database             | Microsoft SQL Server |
| Build Tool           | Maven                |
| IDE                  | IntelliJ IDEA        |
| Version Control      | Git & GitHub         |

---

# 📂 Project Structure

```text
src/main/java/
│
├── controller/
├── service/
├── repository/
├── model/
├── config/
├── observer/
├── strategy/
└── security/

src/main/resources/
│
├── templates/
├── static/
└── application.properties
```

---

# 👨‍💻 My Contribution

### 👤 Vimukthi Siriwardana (Me)

Responsible for the **Medicine Management Module**.

### Contributions:

* Developed Medicine CRUD operations
* Implemented inventory stock monitoring
* Designed medicine dashboard analytics
* Built low-stock and expiry alert functionality
* Implemented Observer Design Pattern for inventory notifications
* Developed medicine add/edit forms
* Integrated real-time inventory status monitoring

---

# 📸 System Screenshots

> Add screenshots here:

* Home Page
<img width="1919" height="1016" alt="image" src="https://github.com/user-attachments/assets/52e34f59-3329-4181-9f0d-da4af9ecf893" />

* Medicine Dashboard
<img width="1919" height="1019" alt="image" src="https://github.com/user-attachments/assets/acecbb5d-38c1-4b73-a147-b1fc5d6b5f81" />

* Inventory Alerts Page
<img width="1919" height="1018" alt="image" src="https://github.com/user-attachments/assets/c4f59d0c-28a6-44fc-8949-d288780345e6" />
<img width="1919" height="1019" alt="image" src="https://github.com/user-attachments/assets/7a8c8cce-ba5a-4796-b73a-c26836e7b582" />

* Medicine Add/Edit Forms
<img width="1919" height="1019" alt="image" src="https://github.com/user-attachments/assets/487cdd8d-4e86-4c05-a8ab-68c29e0f50e0" />
<img width="1919" height="1022" alt="image" src="https://github.com/user-attachments/assets/fae8df55-fa32-4cbe-bc44-4d172cf168e8" />

* Customer Dashboard
<img width="1919" height="1020" alt="image" src="https://github.com/user-attachments/assets/a941e8e3-0f4e-4ea8-865f-3769bd2caec4" />
<img width="1919" height="1018" alt="image" src="https://github.com/user-attachments/assets/f29a9977-73be-4a06-a5fb-0d404739eb21" />

* Prescription Upload UI
<img width="1919" height="1020" alt="image" src="https://github.com/user-attachments/assets/da9b1543-3771-47d0-8e29-be114cdcd2b6" />

---

# ⚙️ Installation & Setup

## Prerequisites

* Java 17
* Maven
* SQL Server
* IntelliJ IDEA

## Clone Repository

```bash
git clone https://github.com/your-username/pharmacy-management-system.git
```

## Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=pharmacy_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Run Application

```bash
mvn spring-boot:run
```
---

# 👥 Team Members

* IT24103506 – Vimukthi Siriwardana (Me)
* IT24103558 – Jayawickum H.W.M.
* IT24103519 – Kalubowila G.N.
* IT24102613 – Amarasinghe A.B.E
* IT24103609 – Thanusikan. M
* IT24103553 – Jayarathne K.P.S.U

---

# 📚 Academic Information

* Module: Software Engineering
* Institution: Sri Lanka Institute of Information Technology (SLIIT)
* Academic Year: Year 2 Semester 1

---

# ⭐ Repository

If you found this project helpful, consider giving it a star ⭐
