# 🌿 Plant Pal New - Smart Fertilizer & Gardening Delivery App

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com)
[![SQLite](https://img.shields.io/badge/Database-SQLite-blue.svg)](https://www.sqlite.org)
[![Retrofit](https://img.shields.io/badge/Networking-Retrofit_2.9.0-brightgreen.svg)](https://square.github.io/retrofit/)
[![Google Maps](https://img.shields.io/badge/Maps-Google_Maps_SDK-red.svg)](https://developers.google.com/maps)

**Plant Pal New** is a full-featured native Android mobile application designed to empower gardeners, home growers, and commercial farmers to easily browse, select, and order organic, synthetic, and specialized fertilizer blends with real-time weather integration and branch navigation.

---

## ✨ Key Features

- **🔐 User Authentication & Session Management**:
  - Secure registration (Username, Email, NIC, Password) and Login with SQLite.
  - Automatic session persistence using `SharedPreferences`.
  - Working **Forgot Password** recovery and password reset system.

- **⛅ Real-Time Weather Forecasting**:
  - Live weather updates (Temperature, Rainfall, Humidity) powered by the **Open-Meteo REST API** using **Retrofit 2** & **Gson**.
  - Helps farmers and growers decide the ideal day and conditions for fertilizing.

- **📍 Interactive Branch Locator**:
  - Integrated **Google Maps SDK** displaying Plant Pal physical stores and branches across Sri Lanka (Colombo, Ratnapura, Kalawana).

- **🌱 Comprehensive Product Catalog**:
  - Categorized into **Organic**, **Synthetic**, and **Blends**.
  - Detailed product descriptions, usage instructions, pricing, and visual assets.

- **🛒 Dynamic Shopping Cart**:
  - Full SQLite-backed shopping cart with dynamic item loading.
  - Real-time quantity increment/decrement (`+` / `-`) and item removal (`✕`).
  - Automatic subtotal, shipping, and grand total calculations.

- **💳 Smooth Checkout & Delivery Flow**:
  - Mock payment gateway supporting Card and Cash on Delivery.
  - Form-validated shipping address capture with profile auto-fill.
  - Order logging and instant order confirmation.

- **👤 Profile & Settings**:
  - **Account Management**: View and update profile details (Name, Email, Phone, Address) stored in SQLite.
  - **Dark Mode**: Instant theme toggling between Day and Night modes with state persistence.
  - **Notifications & Reminders**: Configurable app alerts.
  - **Logout**: Safe session clearance.

---

## 🛠️ Technology Stack & Libraries

| Component | Technology |
|---|---|
| **Language** | Java (Java 8 / Java 17) |
| **Platform** | Android Native (Min SDK 24, Target SDK 34) |
| **Local Database** | SQLite (`SQLiteOpenHelper`) |
| **Network & REST API**| Retrofit 2.9.0 + Gson Converter + OkHttp Logging Interceptor |
| **Maps & Location** | Google Play Services Maps SDK (18.2.0) + Maps Utils |
| **UI Components** | AndroidX Material Components, ConstraintLayout, CardView, BottomNavigationView |

---

## 📂 Project Structure

```
plantpal/
├── app/
│   ├── src/main/
│   │   ├── java/com/s22010022/plantpal/
│   │   │   ├── MainActivity.java        # Splash & entry point (session checking)
│   │   │   ├── Forfarmers.java          # Onboarding video player
│   │   │   ├── Login.java               # Authentication & session creation
│   │   │   ├── SignUp.java              # User registration
│   │   │   ├── Fogottenpassword.java    # Password recovery dialog
│   │   │   ├── Dashbord.java            # Home dashboard & Live weather API
│   │   │   ├── Googlemap.java           # Store location map markers
│   │   │   ├── fertilizerPage1.java     # Organic category
│   │   │   ├── fertilizerPage2.java     # Synthetic category
│   │   │   ├── fertilizerPage3.java     # Blends category
│   │   │   ├── productdiscription.java  # Dynamic product view & Add to Cart
│   │   │   ├── cart.java                # Dynamic shopping cart & total calculation
│   │   │   ├── PaymentPage.java         # Payment selection & review
│   │   │   ├── shippingaddress.java     # Shipping details & order recording
│   │   │   ├── EndPageActivity.java     # Order confirmation screen
│   │   │   ├── account.java             # Profile view & editor
│   │   │   ├── setting.java             # Dark mode, notifications & logout
│   │   │   ├── databaseHelper.java      # SQLite schema, queries & CRUD logic
│   │   │   └── SessionManager.java      # SharedPreferences session handler
│   │   └── res/                         # Layouts, vector drawables & media
│   └── build.gradle.kts                 # App-level dependencies
└── settings.gradle.kts                  # Project settings
```

---

## 🚀 Getting Started

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Lakshitha941/plantpal-fertilizer-dilivery-app.git
   ```
2. **Open in Android Studio**:
   - Open Android Studio $\rightarrow$ Click **Open** $\rightarrow$ Select the cloned folder.
3. **Gradle Sync & Build**:
   - Let Gradle download dependencies and sync.
4. **Run the App**:
   - Connect an Android device or start an emulator $\rightarrow$ Click **Run (▶️)**.

---

## 📄 License
This project is developed for educational and portfolio demonstration purposes.
