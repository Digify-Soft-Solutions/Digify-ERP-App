# Digify ERP Mobile App (Android)

> **Enterprise GST Billing, Accounting & Inventory App for Indian Small & Medium Businesses**  
> Developed for **Digify Soft Solutions Pvt Ltd** ([digifysoft.in](https://digifysoft.in))  
> Support Contact: **+91 74250 16636** | **support@digifysoft.in**

---

## 📱 Overview

**Digify ERP** is a modern, high-performance Android mobile application built with **100% Native Kotlin and Jetpack Compose (Material 3)**. Inspired by Indian billing standards like Vyapar, it provides business owners with offline-first billing, 50+ GST & business reports, and direct ESC/POS hardware thermal printer integration.

---

## ✨ Features

- **Home & Transaction Ledger**:
  - Segmented control toggling between **Transaction Details** and **Party Details**.
  - One-tap Quick Links for Sales Reports, Adding Transactions, and Importing Parties.
  - Interactive transaction cards showing invoice number, customer name, date, total amount, balance due, and printable thermal receipt preview.
  - Direct WhatsApp invoice sharing.

- **Dashboard & Analytics**:
  - **Digify Reports** hero card with instant access to 50+ accounting reports.
  - KPI summary cards for **"You'll Get"** (Receivables) and **"You'll Give"** (Payables).
  - Monthly Sales Overview with an interactive Canvas-rendered Bezier line chart showing month-over-month growth.
  - Monthly expenses tracking.

- **Inventory Management**:
  - Pre-populated with retail & hardware items (ERP licenses, thermal paper rolls, 2D barcode scanners, POS printers).
  - Low stock alerts and reorder levels.
  - Modal dialog to add new products with sale price, purchase price, opening stock, and units.

- **Menu & Business Operations**:
  - **Carousel Promos**: Deal sales and upcoming desktop edition announcements.
  - **My Business**: Expandable sections for Sales (Invoices, Quotations, Orders, Challans, Returns) and Purchases (Bills, Payment Out, Purchase Orders, Returns).
  - **Cash & Bank**: Bank Accounts and Cash In-Hand registers.
  - **Settings & Support**: Thermal printer configuration (80mm vs 58mm, auto-cutter), company profile, and direct WhatsApp/Phone support to **+91 74250 16636**.

- **Get Desktop (Coming Soon)**:
  - *"We are working on it and we will get back to you very soon!"*
  - Standalone Windows PC software synchronization specs, early access registration, and priority support.

- **Authentication & Login**:
  - Mobile OTP login with `+91` country code.
  - Email and password sign-in option.
  - Demo store bypass for rapid testing.

- **Hardware & Thermal Printing (ESC/POS)**:
  - Built-in ESC/POS byte generator (`generateEscPosThermalReceipt`) for standard 80mm & 58mm Bluetooth and USB thermal receipt printers.
  - Formatted bills with company header, GSTIN, line-item table, totals, and paper cut command.

---

## 🛠️ Tech Stack

- **Language**: Kotlin 2.2
- **UI Framework**: Jetpack Compose (Material 3)
- **Target SDK**: Android 36 (minSdk 24)
- **Build System**: Gradle 9.1 with Gradle Version Catalog (`libs.versions.toml`)
- **Architecture**: Clean Architecture with reactive Kotlin `StateFlow`
- **Offline Resilience**: Offline-first repository with in-memory & local state caching

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug / Koala / Meerkat (or later)
- JDK 17 or JDK 21 (bundled JBR)
- Android SDK Platform 36

### Build & Run
1. Open the project root folder in **Android Studio**.
2. Click **Sync Project with Gradle Files** (Elephant icon).
3. Select the `app` run configuration and click the green **Run (▶)** button, or run from the command line:

```powershell
# Assemble Debug APK
.\gradlew.bat assembleDebug

# Install on connected device / emulator
.\gradlew.bat installDebug
```

---

## 🏢 Crafted by

**Digify Soft Solutions Pvt Ltd**  
- Website: [https://digifysoft.in](https://digifysoft.in)  
- Customer Care & WhatsApp: **+91 74250 16636**  
- Email: **support@digifysoft.in**
