# 🛍 Zeniba – Fashion E-Commerce App

<div align="center">



Modern fashion shopping experience with seamless technology

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Node.js](https://img.shields.io/badge/Node.js-18+-339933?logo=node.js&logoColor=white)](https://nodejs.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.0+-47A248?logo=mongodb&logoColor=white)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)


🖥 Backend: [https://zeniba-server.onrender.com](https://zeniba-server.onrender.com)

</div>

---

## 🌟 Overview

Zeniba is a full-featured fashion e-commerce platform connecting buyers, sellers, and couriers. Built with Kotlin + Jetpack Compose on Android and powered by Node.js + Express backend with real-time capabilities.

---

## ✨ Features

### 👤 For Buyers
- 🔐 Google OAuth 2.0 and Email/Password authentication
- 🛒 Shopping cart and wishlist management
- 💳 Secure payment gateway integration
- 🔎 Product search and category filters
- 📦 Real-time order tracking
- 📱 Offline mode support

### 🏪 For Sellers
- 📊 Seller dashboard with analytics
- ➕ Product management (Add, Edit, Delete)
- 📈 Sales tracking and inventory management
- 🚚 Order fulfillment tracking

### 🚚 For Couriers
- 📍 View assigned deliveries
- ✅ Update delivery status in real-time
- 📜 Delivery history

---

## 🛠 Tech Stack

### 📱 Android Frontend
- Language: Kotlin
- UI: Jetpack Compose
- Architecture: MVVM
- Network: Retrofit
- Local Database: Room
- Image Loading: Coil
- Authentication: Google Sign-In SDK
- State Management: StateFlow, ViewModel

### 🖥 Backend Server
* Runtime: Node.js + Express.js
* Database: MongoDB
* AI-Powered Recommendations: Product suggestions using embeddings via Pinecone and HuggingFace models
* Containerization: Docker + Docker Compose
* Reverse Proxy: Nginx
* Image Storage: Cloudinary
* Authentication: OAuth 2.0, JWT, Cookies
* Payment Gateway: Razorpay
* Security: CORS, bcrypt

---

## 📸 Screenshots

<div align="center">

### Authentication & Home
<table>
  <tr>
    <td><img src="https://drive.google.com/uc?id=1aKsDsoWQR9bq1IZkapQqUa0vNRJCYoC_" alt="Home" width="250"/><br/><b>Home</b></td>
    <td><img src="https://drive.google.com/uc?id=17cMwM2R_v1275fdK4KPXGxda8XN2U2_t" alt="Signup" width="250"/><br/><b>Signup</b></td>
    <td><img src="https://drive.google.com/uc?id=1rEYBJ36whkSl79kQ34of0_PJPABZrKgm" alt="Category" width="250"/><br/><b>Category</b></td>
  </tr>
</table>

### Shopping Experience
<table>
  <tr>
    <td><img src="https://drive.google.com/uc?id=1_R19roQJOvjHyAln171WzD0XJ7Mv1YQQ" alt="Wishlist" width="250"/><br/><b>Wishlist</b></td>
    <td><img src="https://drive.google.com/uc?id=14zT2RE-Xk7a7L6hEXJ99bm50qV0KZq9P" alt="Cart" width="250"/><br/><b>Cart</b></td>
    <td><img src="https://drive.google.com/uc?id=1AZ6QQNnb2Lz3hA8nC60XgRFSTxMrBlwX" alt="Checkout" width="250"/><br/><b>Checkout</b></td>
  </tr>
</table>

<table>
  <tr>
    <td><img src="https://drive.google.com/uc?id=1PTG1OxLh6ms_--OFBmelb6RLRftGnTr1" alt="Browse" width="250"/><br/><b>Browse</b></td>
    <td><img src="https://drive.google.com/uc?id=1oy2mP4CfsmI5t4_V6fnqQHln8-Z4y0zw" alt="Login" width="250"/><br/><b>Login</b></td>
  </tr>
</table>

</div>

---
## 🚀 Installation & Setup

### Prerequisites
- Node.js 18+
- MongoDB 6.0+
- Android Studio (latest)
- JDK 17+

---

### 1️⃣ Backend Server Setup


```markdown
git clone https://github.com/jyotirmay0/Zeniba.git
cd server
docker compose up --build
```



The backend will start on the default configured port.
Ensure MongoDB, Pinecone, HuggingFace, Razorpay, and OAuth2 Client credentials are properly configured in the environment variables.

---

### 2️⃣ Android App Setup

#### Open in Android Studio
1. Open Android Studio
2. Select Open an Existing Project
3. Navigate to Zeniba/android
4. Wait for Gradle sync

#### Configure API Base URL
Update data/Constants.kt:
kotlin
```markdown
object Constants {

    // For Emulator
    const val BASE_URL = "http://10.0.2.2:5000/api/"
    
    // For Real Device (use your PC's IP)
    // const val BASE_URL = "http://192.168.1.x:5000/api/"
    
    // Production
    // const val BASE_URL = "https://zeniba-server.onrender.com/api/"
}
```

#### Setup Google Sign-In
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create OAuth 2.0 credentials
3. Add SHA-1 fingerprint:
bash
./gradlew signingReport

4. Download google-services.json → place in app/
5. Update res/values/strings.xml:
xml
<string name="default_web_client_id">YOUR_CLIENT_ID.apps.googleusercontent.com</string>


#### Run the App
1. Connect device/emulator (Android 8.0+)
2. Click Run in Android Studio
3. Test authentication and features

---
# 🏗 Architecture

### 🧩 System Architecture
```markdown


┌─────────────────────────────────────────────┐
│            ZENIBA PLATFORM                  │
├─────────────────────────────────────────────┤
│                                             │
│  ┌──────────────┐    ┌──────────────┐      │
│  │   Android    │◄──►│   Backend    │      │
│  │     App      │    │    Server    │      │
│  │  (Compose)   │    │  (Node.js)   │      │
│  └──────┬───────┘    └──────┬───────┘      │
│         │                   │               │
│    ┌────▼────┐       ┌──────▼───────┐      │
│    │  Room   │       │   MongoDB    │      │
│    │   DB    │       │   + Redis    │      │
│    └─────────┘       └──────────────┘      │
│                                             │
│  External: Cloudinary, Socket.IO, WebRTC   │
└─────────────────────────────────────────────┘


### Android MVVM Pattern

UI Layer (Compose Screens)
       ↕
ViewModel Layer (Business Logic)
       ↕
Repository Layer (Data Coordination)
       ↕
Data Sources (Retrofit + Room)
```


## 🔐 Key Features Implementation

### Authentication Flow
1. User signs in with Google OAuth 2.0 or Email
2. Backend validates credentials
3. JWT token generated and stored in HTTP-only cookie
4. Token included in subsequent API requests

### Payment Processing
1. User initiates checkout
2. Razorpay payment gateway opens
3. Payment confirmed via webhook
4. Order status updated in database
5. Confirmation email sent

### Image Upload
1. User selects product images
2. Multer processes multipart form data
3. Images uploaded to Cloudinary
4. CDN URLs stored in MongoDB
5. Optimized images served to app

---

## 🎯 Roadmap

### ✅ Completed (v1.0)
- [x] OAuth 2.0 authentication
- [x] Product browsing and search
- [x] AI recommendations
- [x] Cart and wishlist
- [x] Payment integration
- [x] Order management
- [x] Real-time tracking

### 🚧 In Progress (v1.1)
- [ ] Seller dashboard
- [ ] Product reviews and ratings
- [ ] Push notifications
- [ ] Advanced filters
- [ ] Multi-language support

### 📅 Future (v2.0)
- [ ] iOS app
- [ ] AR try-on feature
- [ ] Live chat support
- [ ] Social sharing

---



<div align="center">

## ⭐ Star this repo if you found it helpful!



[⬆ Back to Top](#-zeniba--fashion-e-commerce-app)

</div>
