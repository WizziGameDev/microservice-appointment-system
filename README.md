
# 🏥 Microservice Appointment System with Service Discovery

Selamat datang di proyek **Microservice Appointment System**! 🎉  
Proyek ini merupakan implementasi arsitektur **microservices** menggunakan **Spring Boot**, **Service Discovery (Eureka)**, dan **Redis** sebagai sistem caching. Cocok untuk kebutuhan sistem reservasi seperti rumah sakit, klinik, atau layanan serupa.

---

## 🧩 Arsitektur Umum

```
+-------------+         +--------------+         +-------------------+
|  Patient    | <-----> |  Appointment | <-----> |     Doctor        |
+-------------+         +--------------+         +-------------------+
        |                      |                          |
        v                      v                          v
     Redis                Redis                       Redis
        |                      |                          |
     MySQL                 MySQL                      MySQL
```

---

## 📦 Service List

### 🔹 1. **Patient Service**
- 📍 Port: `8085`
- 🗄 Database: `MySQL` (db_service_patient)
- ⚡ Cache: `Redis` (redis_patient)
- Fungsi: Menyimpan dan mengelola data pasien.

### 🔹 2. **Doctor Service**
- 📍 Port: `8082`
- 🗄 Database: `MySQL` (db_service_doctor)
- ⚡ Cache: `Redis` (redis_doctor)
- Fungsi: Menyimpan data dokter dan jadwal ketersediaan.

### 🔹 3. **Appointment Service**
- 📍 Port: `8083`
- 🗄 Database: `MySQL` (db_service_appointments)
- ⚡ Cache: `Redis` (redis_appointments)
- Fungsi: Mengelola janji temu antara pasien dan dokter.

---

## 🔍 Service Discovery - Eureka

Semua service otomatis terdaftar ke **Eureka Server**, sehingga antar-service dapat saling mengenali dan berkomunikasi tanpa mengandalkan hardcoded URL.

- 💡 **Keuntungan**:
  - Auto registration & discovery
  - Scalability-friendly
  - Simplified inter-service communication

---

## 🚀 Fitur Unggulan

- ✅ **Microservice Clean Separation**
- ✅ **Caching dengan Redis**
- ✅ **Service Discovery dengan Eureka**
- ✅ **Restful API per service**
- ✅ **Error Handling dan Response yang konsisten**
- ✅ **Scalable dan Maintainable**

---

## 🛠️ Teknologi yang Digunakan

- `Java 21`
- `Spring Boot`
- `Spring Web`
- `Spring Data JPA`
- `Eureka Discovery Server`
- `Redis`
- `MySQL`
- `Docker`

---

## 👨‍💻 Kontribusi

Pull request terbuka! Jangan ragu untuk fork dan kontribusi ke proyek ini.

---

## 📬 Kontak

Dikembangkan oleh [WizziGameDev](https://github.com/WizziGameDev)  
Untuk pertanyaan atau saran, silakan buka issue atau hubungi langsung 🙌

---

## 📄 License

Proyek ini berlisensi MIT — wizzi.gamedev
