
# 📄 API Documentation Appointment Service

## ⚙️ Technology and Configuration

- **☕ Language & Framework**: Java (Spring Boot)
- **🗄️ Database**: PostgreSQL (Database/Service)
- **⚡ Caching**: Redis is used to store cached responses from GET /api/v1/appointments and /api/v1/appointment/{slug} to improve data retrieval speed.
- **✅ Validation**: Field-level validation is implemented to ensure data integrity during both creation and updates.
- **⚡ Concurrency**: Supports Virtual Threads to enhance performance and efficiency in handling concurrent requests.
- **🔄 Migration**: Using Flyway for database migration

## 🔜 Upcomming

- 🔐 **Updating project with Spring Security and Spring Cloud Security**
  
---

## 📥 Endpoint

### 🔹 GET /api/v1/appointments

#### 📝 Description

Retrieve all appointment data from the system (Display List).

#### 📌 Example Endpoint

GET localhost:8090/api/v1/appointments

#### ✅ Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": [
        {
            "slug": "apt-001",
            "nameAppointment": "Checkup Umum",
            "dayOfWeek": "Monday",
            "startTime": 1714548000,
            "endTime": 1714551600,
            "status": "CONFIRMED"
        },
        {
            "slug": "apt-002",
            "nameAppointment": "Konsultasi Gigi",
            "dayOfWeek": "Tuesday",
            "startTime": 1714634400,
            "endTime": 1714638000,
            "status": "PENDING"
        },
        {
            "slug": "konsultasi-radiologi-adam-3043",
            "nameAppointment": "Konsultasi Radiologi Adam",
            "dayOfWeek": "MONDAY",
            "startTime": 1714550400000,
            "endTime": 1714554000000,
            "status": "PENDING"
        }
    ],
    "errors": null
}
```
---

## 📥 Endpoint

### 🔹 POST /api/v1/appointment

#### 📝 Description

Create a new doctor entry with the provided details.

### 📌 Example Request

POST localhost:8086/api/v1/appointment

**Request Body:**

```json
{
  "nameAppointment": "Percobaan Kesekian Kali",
  "patientSlug": "patient-004",
  "doctorSlug": "dr-jane-smith",
  "dayOfWeek": "MONDAY",
  "status": "PENDING",
  "startTime": 1714550400000,
  "endTime": 1714554000000
}
```

#### ✅ Success Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": {
        "slug": "percobaan-kesekian-kali-8165",
        "nameAppointment": "Percobaan Kesekian Kali",
        "dayOfWeek": "MONDAY",
        "startTime": 1714550400000,
        "endTime": 1714554000000,
        "status": "PENDING",
        "doctor": {
            "slug": "dr-jane-smith",
            "name": "Dr. Jane Smith",
            "specialization": "Cardiologist"
        },
        "patient": {
            "slug": "patient-004",
            "name": "Bob Brown"
        }
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
```json
{
    "statusCode": 404,
    "data": null,
    "errors": [
        "Patient Not Found"
    ]
}
```
---

## 📥 Endpoint

## 🔹 POST /api/v1/appointment/{slug}

### 📝 Description

Retrieve appointment data based on unique slug.

### 📌 Example Request

POST localhost:8090/api/v1/appointment/percobaan-kesekian-kali-8165

### ✅ Success Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": {
        "slug": "percobaan-kesekian-kali-8165",
        "nameAppointment": "Percobaan Kesekian Kali",
        "dayOfWeek": "MONDAY",
        "startTime": 1714550400000,
        "endTime": 1714554000000,
        "status": "PENDING",
        "doctor": {
            "slug": "dr-jane-smith",
            "name": "Dr. Jane Smith",
            "specialization": "Cardiologist"
        },
        "patient": {
            "slug": "patient-004",
            "name": "Bob Brown"
        }
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

## 📥 Endpoint

## 🔹 PUT /api/v1/appointment/{slug}

### 📝 Description

Update an existing appointment details using their unique slug identifier.

### 📌 Example Request

PUT localhost:8090/api/v1/appointment/percobaan-kesekian-kali-8165

**Request Body:**

```json
{
  "nameAppointment": "Percobaan Berkali kali",
  "patientSlug": "patient-001",
  "doctorSlug": "dr-jane-smith",
  "dayOfWeek": "MONDAY",
  "status": "PENDING",
  "startTime": 1713550400000,
  "endTime": 1714354000000
}
```

### ✅ Success Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": {
        "slug": "percobaan-berkali-kali-9658",
        "nameAppointment": "Percobaan Berkali kali",
        "dayOfWeek": "MONDAY",
        "startTime": 1713550400000,
        "endTime": 1714354000000,
        "status": "PENDING",
        "doctor": {
            "slug": "dr-jane-smith",
            "name": "Dr. Jane Smith",
            "specialization": "Cardiologist"
        },
        "patient": {
            "slug": "patient-001",
            "name": "John Doe"
        }
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

```json
{
  "statusCode": 404,
  "data": null,
  "errors": [
    "Patient Not Found"
  ]
}
```
---

## 📥 Endpoint

## 🔹 PUT /api/v1/appointment/{slug}/{status}

### 📝 Description

Updating status appointment with slug for identifier.

### 📌 Example Request

PUT localhost:8090/api/v1/appointment/percobaan-berkali-kali-9658/CONFIRMED

### ✅ Success Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": "Appointment with slug 'percobaan-berkali-kali-9658' successfully updated status.",
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
    "Appointment Not Found"
  ]
}
```
---

## 📥 Endpoint

## 🔹 DELETE /api/v1/appointment/{slug}

### 📝 Description

Delete an existing appointment entry using their unique slug identifier.

### 📌 Example Request

DELETE localhost:8090/api/v1/appointment/percobaan-berkali-kali-9658

### ✅ Success Response

Status Code: 200 OK

```json
{
    "statusCode": 200,
    "data": "Appointment with slug 'percobaan-berkali-kali-9658' successfully deleted.",
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
    "Appointment Not Found"
  ]
}
```
---
