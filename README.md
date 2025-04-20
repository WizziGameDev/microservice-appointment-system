
# 📄 API Documentation Doctor Service

## ⚙️ Technology and Configuration

- **☕ Language & Framework**: Java (Spring Boot)
- **🗄️ Database**: PostgreSQL (Database/Service)
- **⚡ Caching**: Redis is used to store cached responses from GET /api/v1/doctors and /api/v1/doctor/{slug} to improve data retrieval speed.
- **✅ Validation**: Field-level validation is implemented to ensure data integrity during both creation and updates.
- **⚡ Concurrency**: Supports Virtual Threads to enhance performance and efficiency in handling concurrent requests.
- **🔄 Migration**: Using Flyway for database migration

## 🔜 Upcomming

- 🔐 **Updating project with Spring Security and Spring Cloud Security**
  
---

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
---

## 📥 Endpoint

### 🔹 GET /api/v1/doctor/{slug}

#### 📝 Description

Retrieve doctor data based on unique slug.

#### 📌 Example Endpoint

GET localhost:8086/api/v1/doctor/dr-john-doe

#### ✅ Success Response

Status Code: 200 OK

```json
{
  "statusCode": 200,
  "data": {
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
---

# Validation Rules for DoctorRequest and AvailabilityRequest

## DoctorRequest Validation Rules

- **name**: 
  - Must be between **2 and 100 characters**.
  - Cannot be **empty**.

- **email**: 
  - Must be a **valid email address**.
  - Cannot be **empty**.

- **phoneNumber**:
  - Must be **numeric** (maximum of **15 digits**).
  - Cannot be **null**.

- **address**:
  - Cannot be **empty**.

- **gender**: 
  - Must be one of the following: **Male**, **Female**, or **Other**.
  - Cannot be **empty**.

- **birthDate**: 
  - Must be a valid **timestamp** (Unix format).
  - Cannot be **null**.

- **licenseNumber**:
  - Cannot be **empty**.

- **experienceYears**:
  - Must be at least **0 years**.
  - Cannot be **null**.

- **specialization**:
  - Cannot be **empty**.

- **availabilities**: 
  - Must be a **non-empty list**.
  - Each entry must follow the `AvailabilityRequest` structure (defined below).

---

## AvailabilityRequest Validation Rules

- **dayOfWeek**: 
  - Must be one of the following valid days: **Monday**, **Tuesday**, **Wednesday**, **Thursday**, **Friday**, **Saturday**, or **Sunday**.
  - Cannot be **empty**.

- **startTime**: 
  - Must be a **positive timestamp** (Unix format).
  - Cannot be **null**.

- **endTime**: 
  - Must be a **positive timestamp** (Unix format).
  - Cannot be **null**.

---

## 📌 Example Request

```json
{
  "name": "",
  "email": "sarah",
  "phoneNumber": "",
  "address": "",
  "gender": "Hide",
  "birthDate": "",
  "licenseNumber": "",
  "experienceYears": "",
  "specialization": "",
  "availabilities": [
    {
      "dayOfWeek": "Pekan Arep",
      "startTime": null,
      "endTime": ""
    },
    {
      "dayOfWeek": "Sesok Ae",
      "startTime": "",
      "endTime": null
    }
  ]
}
```

### ❌ Error Response

Status Code: 400 Bad Request

```json
{
    "statusCode": 400,
    "data": null,
    "errors": {
        "availabilities[0].startTime": "Start time is required",
        "availabilities[0].endTime": "End time is required",
        "address": "Address is required",
        "gender": "Gender must be Male, Female, or Other",
        "availabilities[1].dayOfWeek": "Day of week must be a valid day (e.g., Monday, Tuesday, etc.)",
        "experienceYears": "Experience years is required",
        "availabilities[1].startTime": "Start time is required",
        "birthDate": "Birth date is required",
        "phoneNumber": "Phone number is required",
        "name": "Name is required",
        "availabilities[0].dayOfWeek": "Day of week must be a valid day (e.g., Monday, Tuesday, etc.)",
        "specialization": "Specialization is required",
        "availabilities[1].endTime": "End time is required",
        "licenseNumber": "License number is required",
        "email": "Email should be valid"
    }
}
```
---

## 📥 Endpoint

## 🔹 POST /api/v1/doctor

### 📝 Description

Create a new doctor entry with the provided details.

### 📌 Example Request

POST localhost:8086/api/v1/doctor

**Request Body:**

```json
{
  "name": "Dr. Sarah Johnson",
  "email": "sarah.johnson@example.com",
  "phoneNumber": 6281234567890,
  "address": "Jl. Sehat No. 123, Jakarta",
  "gender": "Female",
  "birthDate": 631152000000,
  "licenseNumber": "LIC-2024-0001",
  "experienceYears": 5,
  "specialization": "Cardiology",
  "availabilities": [
    {
      "dayOfWeek": "Monday",
      "startTime": 1716890400000,
      "endTime": 1716897600000
    },
    {
      "dayOfWeek": "Wednesday",
      "startTime": 1717063200000,
      "endTime": 1717070400000
    }
  ]
}
```

### ✅ Success Response

Status Code: 200 OK

```json
{
  "statusCode": 200,
  "data": {
    "slug": "dr-sarah-johnson-9595",
    "name": "Dr. Sarah Johnson",
    "email": "sarah.johnson@example.com",
    "phoneNumber": 6281234567890,
    "address": "Jl. Sehat No. 123, Jakarta",
    "gender": "Female",
    "birthDate": 631152000000,
    "licenseNumber": "LIC-2024-0001",
    "experienceYears": 5,
    "specialization": "Cardiology",
    "availabilities": [
      {
        "dayOfWeek": "Monday",
        "startTime": 1716890400000,
        "endTime": 1716897600000
      },
      {
        "dayOfWeek": "Wednesday",
        "startTime": 1717063200000,
        "endTime": 1717070400000
      }
    ]
  },
  "errors": null
}
```
---

## 📥 Endpoint

## 🔹 PUT /api/v1/doctor/{slug}

### 📝 Description

Update an existing doctor's details using their unique slug identifier.

### 📌 Example Request

PUT localhost:8086/api/v1/doctor/dr-sarah-johnson-9595

**Request Body:**

```json
{
  "name": "Dr. Sarah",
  "email": "sarah.johnson@example.com",
  "phoneNumber": 6281234567890,
  "address": "Jl. Sehat No. 123, Jakarta",
  "gender": "Female",
  "birthDate": 631152000000,
  "licenseNumber": "LIC-2024-0001",
  "experienceYears": 5,
  "specialization": "Cardiology",
  "availabilities": [
    {
      "dayOfWeek": "Monday",
      "startTime": 1716890400000,
      "endTime": 1716897600000
    },
    {
      "dayOfWeek": "Wednesday",
      "startTime": 1717063200000,
      "endTime": 1717070400000
    }
  ]
}
```

### ✅ Success Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": {
        "slug": "dr-sarah-1239",
        "name": "Dr. Sarah",
        "email": "sarah.johnson@example.com",
        "phoneNumber": 6281234567890,
        "address": "Jl. Sehat No. 123, Jakarta",
        "gender": "Female",
        "birthDate": 631152000000,
        "licenseNumber": "LIC-2024-0001",
        "experienceYears": 5,
        "specialization": "Cardiology",
        "availabilities": [
            {
                "dayOfWeek": "Monday",
                "startTime": 1716890400000,
                "endTime": 1716897600000
            },
            {
                "dayOfWeek": "Wednesday",
                "startTime": 1717063200000,
                "endTime": 1717070400000
            }
        ]
    },
    "errors": null
}
```

### ❌ Error Response

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
---

## 📥 Endpoint

## 🔹 DELETE /api/v1/doctor/{slug}

### 📝 Description

Delete an existing doctor entry using their unique slug identifier.

### 📌 Example Request

DELETE localhost:8086/api/v1/doctor/dr-sarah-1239

### ✅ Success Response

Status Code: 200 OK

```json
{
  "statusCode": 200,
  "data": "Successfully deleted",
  "errors": null
}
```

### ❌ Error Response

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
---
