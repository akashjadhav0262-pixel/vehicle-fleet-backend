# FleetMS Pro - Multi-Vendor Fleet & Rental Management Backend

FleetMS Pro is a robust, enterprise-grade backend REST API built with **Spring Boot** and **MySQL** that powers a multi-vendor vehicle rental and fleet management platform. It allows vendors to list and manage their vehicles across various categories (such as two-wheelers, four-wheelers, and goods vehicles with drivers), while enabling customers to browse, calculate flexible rental rates (hourly, daily, monthly), and book vehicles seamlessly.

---

## Key Features

- **Multi-Role Authentication & Security:** Secure user registration and login utilizing Spring Security and BCrypt password encryption with role-based access (`CUSTOMER`, `VENDOR_ADMIN`, `SYSTEM_ADMIN`).
- **Vendor Management:** Vendors can manage their business profile, including contact details, business name, and physical address.
- **Advanced Vehicle Categorization:** Support for multiple vehicle types—**Two-Wheelers**, **Four-Wheelers**, and **Goods Vehicles** (with optional dedicated driver allocation and contact details).
- **Flexible Pricing Engine:** Dynamic cost calculation supporting **Hourly**, **Daily**, and **Monthly** rental rates, including separate driver fees where applicable.
- **Booking & Reservation System:** Customers can check vehicle availability, calculate estimated rental costs, and place bookings.
- **Account Management:** Built-in password change and secure token-based password reset features.

---

## Tech Stack

- **Java 17**
- **Spring Boot** (Spring Web, Spring Data JPA, Spring Security)
- **MySQL Database** & Hibernate ORM
- **Maven** (Dependency Management)
- **Postman** (API Testing & Client)

---

## Project Architecture

```text
com.akashjadhav.fleetms
│
├── config         # Security and application configurations
├── controller     # REST API endpoints (Auth, Vendors, Vehicles, Bookings)
├── dto            # Data Transfer Objects for clean request/response payloads
├── model          # JPA Entities (User, VendorProfile, Vehicle, Booking, Enums)
├── repository     # Spring Data JPA repositories for database operations
└── service        # Business logic implementation layer