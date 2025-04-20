
# 📄 API Documentation Doctor Service

## ⚙️ Technology and Configuration

- **☕ Language & Framework**: Java (Spring Boot)
- **🗄️ Database**: PostgreSQL (Database/Service)
- **⚡ Caching**: Redis is used to store cached responses from GET /api/v1/doctors to improve data retrieval speed.
- **✅ Validation**: Field-level validation is implemented to ensure data integrity during both creation and updates.
- **⚡ Concurrency**: Supports Virtual Threads to enhance performance and efficiency in handling concurrent requests.
- **🔄 Migration**: Using Flyway for database migration

## 🔜 Upcomming

- 🔐 **Updating project with Spring Security and Spring Cloud Security**

## 📥 Endpoint

### 🔹 GET /api/v1/doctors

#### 📝 Description

Retrieve all doctor data from the system.

#### ✅ Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": [
        {
            "slug": "dr-john-doe",
            "name": "Dr. John Doe",
            "email": "dr.johndoe@example.com",
            "phoneNumber": 1234567890,
            "address": "123 Main St, Anytown, USA",
            "gender": "Male",
            "birthDate": 950745600000,
            "licenseNumber": "LIC1234567890",
            "experienceYears": 10,
            "specialization": "General Practitioner",
            "availabilities": [
                {
                    "dayOfWeek": "Monday",
                    "startTime": 1682047792000,
                    "endTime": 1682051392000
                },
                {
                    "dayOfWeek": "Wednesday",
                    "startTime": 1682134192000,
                    "endTime": 1682137792000
                }
            ]
        },
        {
            "slug": "dr-jane-smith",
            "name": "Dr. Jane Smith",
            "email": "dr.janesmith@example.com",
            "phoneNumber": 2345678901,
            "address": "456 Oak St, Anytown, USA",
            "gender": "Female",
            "birthDate": 945324000000,
            "licenseNumber": "LIC2345678901",
            "experienceYears": 8,
            "specialization": "Cardiologist",
            "availabilities": [
                {
                    "dayOfWeek": "Tuesday",
                    "startTime": 1682134192000,
                    "endTime": 1682137792000
                },
                {
                    "dayOfWeek": "Friday",
                    "startTime": 1682214192000,
                    "endTime": 1682217792000
                }
            ]
        }
    ],
    "errors": null
}
```

#### ❌ Error Response

Status Code: 404 Not Found

```json
{
    "statusCode": 404,
    "data": null,
    "errors": [
        "Doctor Not Found"
    ]
}
```
