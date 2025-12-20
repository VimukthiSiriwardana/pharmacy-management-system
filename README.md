# 💊 Pharmacy Medicine Management System

<p align="center">
  <b>A smart, real-time pharmacy inventory management system</b><br>
  Built with <b>Java 17</b> & <b>Spring Boot 3</b><br>
  Featuring <b>automatic low-stock and expiry alerts</b> using the <b>Observer Design Pattern</b>
</p>

<p align="center">
  <i>“Never let a medicine expire or run out of stock again.”</i>
</p>

<p align="center">
  📌 <a href="https://github.com/VimukthiSiriwardana/pharmacy-management-system">GitHub Repository</a>
</p>

---

## 🧠 Project Overview

Managing pharmacy inventory manually often results in **expired medicines, stock shortages, and financial losses**.  
This system automates inventory monitoring and alert generation to ensure **efficiency, accuracy, and reliability**.

### 🔹 The system enables:
- 📦 **Real-time stock level tracking**
- ⏱️ **Automatic expiry monitoring**
- 🚨 **Instant alerts using a decoupled Observer Pattern**
- 🖥️ **Clean and professional dashboard for pharmacists**

The project follows **industry-standard layered architecture** and adheres to **SOLID principles**, ensuring scalability and maintainability.

---

## ✨ Key Features

### 🔹 Intelligent Inventory Tracking
- Automatic **medicine status classification**
  - ✅ **In Stock** → Quantity > Reorder Threshold
  - ⚠️ **Low Stock** → 0 < Quantity ≤ Reorder Threshold
  - ❌ **Out of Stock** → Quantity = 0
- ⏱️ **Expiry monitoring**
  - Medicines expiring within **30 days** are flagged automatically

---

### 🚨 Real-Time Alerts (Observer Pattern)
- Inventory updates automatically trigger alerts
- Alert logic is **fully decoupled** from business services
- No hard-coded conditional checks
- Alerts displayed in:
  - 📊 Inventory Dashboard
  - 🔔 Dedicated Alerts Page

---

### 🛠️ CRUD & Inventory Operations
- ➕ Add new medicines
- ✏️ Update medicine details
- 🗑️ Delete medicines
- 🔍 Search and filter inventory
- 🔄 Automatic stock deduction during sales
- ✔️ Form validation for data accuracy

---

### 🎨 Professional User Interface
- 🌙 Dark-themed, modern dashboard
- 🏷️ Status badges for quick visibility
- 📱 Responsive design (desktop & tablet)
- Built using **Thymeleaf + Bootstrap 5**

---

## 🏗️ System Architecture

The system follows a **layered architecture** combined with the **Observer Pattern**:


---

## 🛠️ Technology Stack

| Layer | Technology |
|------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| ORM | Hibernate |
| Persistence | Spring Data JPA |
| Database | Microsoft SQL Server |
| Frontend | Thymeleaf, Bootstrap 5 |
| Icons | Font Awesome |
| Build Tool | Maven |
| IDE | IntelliJ IDEA |

---

## 🧠 Design Pattern Used: Observer Pattern

The **Observer Design Pattern** is used to notify multiple alert mechanisms whenever the medicine inventory changes.

### 🎯 Problem Statement
> How can multiple alert systems respond to inventory changes **without tightly coupling** them to the business logic?

### ✅ Solution
- `MedicineService` acts as the **Subject**
- Alert classes act as **Observers**
- Observers subscribe to inventory changes and react independently

---

### 🔄 Workflow
1. `MedicineService` updates medicine data  
2. Observers are notified automatically  
3. Each observer checks its own condition  
4. Alerts are triggered without modifying core logic  

---

### 🔧 Example Code

```java
public void updateStock(Long id, int newQuantity) {
    Medicine medicine = getMedicineById(id);
    medicine.setQuantity(newQuantity);
    medicineRepository.save(medicine);
    notifyObservers(medicine); // 🔔 Alerts triggered automatically
}


