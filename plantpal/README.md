# Plant Pal - Mobile Application

**Student Registration No:** s22010022  
**Module:** Mobile Application Development (MAD)  
**Project:** Plant Pal - Fertilizer & Plant Care Android App  

---

## Project Overview

Plant Pal is a native Android application designed to help farmers and home gardeners purchase fertilizers and plant care products online. The app provides product browsing categorized into Organic, Synthetic, and Specialized Blends, live weather forecasting to plan fertilizer application, an interactive Google Map for physical store branches, a shopping cart with local SQLite database storage, user authentication, and profile management.

---

## Key Features

- **User Authentication:** 
  - User Sign Up and Login with credentials saved in local SQLite database (`plantPalDB.db`).
  - Forgot Password option to reset password using username and email.
  - Auto-login session management using SharedPreferences.

- **Dashboard & Weather Forecast:**
  - Real-time temperature and rainfall forecast fetched using Open-Meteo REST API via Retrofit library.
  - Quick navigation to Organic, Synthetic, and Blends fertilizer categories.

- **Store Locations:**
  - Google Maps integration with markers showing Plant Pal branch locations (Kalawana, Ratnapura, Colombo).

- **Product Categories & Details:**
  - Categorized browsing: Organic Fertilizers, Synthetic Fertilizers, and Blends.
  - Product detail view showing description, price, and image with "Add to Cart" and "Buy Now" options.

- **Shopping Cart:**
  - Dynamic shopping cart backed by SQLite database.
  - Ability to increase/decrease product quantities or remove items.
  - Automatic calculation of subtotal and total cost.

- **Checkout & Order Placement:**
  - Mock payment option (Card / Cash on Delivery).
  - Shipping address form with validation.
  - Order details recorded into local SQLite database upon submission.

- **User Profile & Settings:**
  - Account screen allowing users to view and update contact details (Email, Phone, Address).
  - Settings screen with Dark Mode switch, notifications toggle, and Logout button.

---

## Technology Stack

- **Platform:** Android (Java)
- **Min SDK:** 24 (Android 7.0) | **Target SDK:** 34 (Android 14)
- **IDE:** Android Studio
- **Database:** SQLite (Native `SQLiteOpenHelper`)
- **Networking:** Retrofit 2.9.0 with Gson converter
- **APIs:** Open-Meteo Weather API, Google Maps SDK
- **Architecture:** Android Activities, Intent communication, SharedPreferences

---

## How to Run the Project

1. Open Android Studio.
2. Select **Open** and choose the `plantpal` project directory.
3. Allow Gradle to sync the dependencies.
4. Connect an Android phone with USB Debugging enabled, or launch an Android Virtual Device (AVD).
5. Click the **Run** button (or `Shift + F10`) to build and launch the application.

---

## Database Tables (`plantPalDB.db`)

1. **`userCredentials`**: Stores user ID, username, email, nic, password, phone, and address.
2. **`cart`**: Stores cart ID, username, product name, category, price, quantity, and image resource.
3. **`orders`**: Stores order ID, username, customer name, address, city, phone, total amount, and date.
