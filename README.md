# Kun.uz — News Portal Backend

REST API backend for a multilingual news portal inspired by kun.uz: articles with categories, regions, sections and tags, comments, likes, bookmarks, and user verification by email or SMS.

## 🛠 Tech Stack

- **Backend:** Java 17, Spring Boot 3.5, Spring Security
- **Database:** PostgreSQL, Spring Data JPA, Hibernate
- **Build Tool:** Maven
- **Other:** JWT, Lombok, Jakarta Validation, Spring Mail, RestTemplate

## 📦 Features

- ✅ JWT Authentication & Authorization
- ✅ Role-based Access Control (RBAC)
- ✅ Email Verification with OTP
- ✅ SMS Integration (RestTemplate, eskiz.uz)
- ✅ File Upload/Download/Open
- ✅ Multi-language Support (UZ/RU/EN)
- ✅ Article Management with Categories, Regions, Sections, Tags
- ✅ Like/Dislike System (Article & Comment)
- ✅ Comment System with Reply support
- ✅ Saved Articles (Bookmarks)
- ✅ View & Share count tracking
- ✅ Pagination & Filtering
- ✅ Global Exception Handling

## 📁 Project Structure

```
kun-uz-step-by-step/src/main/java/dasturlash/uz
├── config       # Spring Security, JWT filter, RestTemplate
├── controller   # REST controllers
├── dto          # request / response objects
├── entity       # JPA entities
├── enums
├── exceptions
├── mapper       # projection interfaces
├── repository   # Spring Data and custom (filter) repositories
├── service      # business logic, email and SMS senders
└── util         # JWT, paging, phone and security helpers
```

## 🚀 Getting Started

**Requirements:** JDK 17, PostgreSQL, Maven (wrapper included)

```bash
git clone https://github.com/amirkhan0102/kun.uz.git
cd kun.uz/kun-uz-step-by-step
```

1. Create a PostgreSQL database.
2. Set the database, mail (SMTP) and SMS provider settings in `src/main/resources/application.properties`.
3. Run the application:

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

## 👨‍💻 Author

**Amriddin Javqochdiyev** — Backend Developer  
📧 javqochdiyev@gmail.com  
🐙 [GitHub](https://github.com/amirkhan0102) · [LinkedIn](https://www.linkedin.com/in/amriddin-j-0a92a8293)

> ⭐ If you found this project helpful, please give it a star!
