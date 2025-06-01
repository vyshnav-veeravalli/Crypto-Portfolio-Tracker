# 📊 Crypto Portfolio Tracker

A full-stack **Spring Boot** application that lets users manage their cryptocurrency portfolio in real time. It supports crypto holdings management, live price tracking, and alert notifications when a coin crosses a user-defined price threshold.

---

## 🌐 Domain  
FinTech / Crypto Portfolio Management / Trading Tools

---

## 🎯 Objectives

- Allow users to add, update, and delete crypto holdings.
- Track **live cryptocurrency prices** (via GeckoTerminal API).
- Set **price alerts** (above or below a threshold).
- Send **email notifications** when alerts trigger.
- Store triggered alerts for review.
- Role-based session login and alert tracking.

---

## 🧱 Tech Stack

| Layer        | Technology                         |
|--------------|-------------------------------------|
| Backend      | Spring Boot                         |
| Database     | MySQL                               |
| API Client   | GeckoTerminal API                   |
| Email        | JavaMailSender (SMTP via Gmail)     |
| Testing      | JUnit 5 + Mockito                   |
| Build Tool   | Maven                               |
| Auth         | HttpSession-based (simple login)    |
| Scheduler    | Spring `@Scheduled` for price polling |
| Docs         | Swagger (OpenAPI via SpringDoc)     |

---

## 🧩 Key Modules

- 🧑‍💼 **User Login & Session Management**
- 📈 **Crypto Holdings Management**
- 🚨 **Alert Creation & Triggering**
- 📧 **Email Notification System**
- 📦 **Triggered Alert History**
- 🕒 **Scheduler-based Price Tracking**

---

## 🧪 API Endpoints

### 🧾 Authentication
- `POST /auth/register` – Register a user
- `POST /auth/login` – Login and set session

### 💼 Portfolio Management
- `POST /portfolio/add` – Add crypto holding
- `GET /portfolio/my` – Get current user's portfolio
- `PUT /portfolio/update` – Update holding
- `DELETE /portfolio/delete` – Delete holding

### 📈 Price Tracking
- `GET /price/live` – Get live prices of top coins
- `GET /price/{symbol}` – Get live price of a specific coin

### 🚨 Alerts
- `POST /alerts/create` – Create a new price alert
- `GET /alerts/my?email=` – View all active alerts for user
- `GET /alerts/triggered?email=` – View triggered alerts (filtered or all)

---

## 🧭 Entity Overview

| Entity          | Key Fields                                         |
|------------------|---------------------------------------------------|
| `User`           | id, name, email, password                         |
| `CryptoHolding`  | id, symbol, quantity, boughtPrice, addedDate      |
| `Alert`          | id, userEmail, symbol, targetPrice, direction     |
| `TriggeredAlert` | id, email, symbol, targetPrice, direction, triggeredTime |

---

## 🖥 Sample Configuration (`application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crypto
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
