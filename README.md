🛒 MyCart Calculator – Offline-First Mobile App

A production-oriented mobile application built with Kotlin Multiplatform and Jetpack Compose, focused on receipt scanning, cart generation, and offline-first data synchronization.

The project is designed with real-world constraints in mind: unreliable networks, background sync, scalability, and future iOS support.

✨ What This Project Does

| Feature                  | Description                                               |
|--------------------------|-----------------------------------------------------------|
| 📸 Receipt Scanning      | Uses on-device text recognition to scan purchase receipts |
| 🧠 Intelligent Parsing   | Extracts products and prices from raw OCR text            |
| 🛒 Cart Generation       | Converts scanned items into editable shopping carts       |
| 📦 Offline-First Storage | Carts are always saved locally first                      |
| 🔄 Background Sync       | Automatic, retryable sync with backend                    |
| 📊 History View          | Browse previously saved carts                             |
| 🧪 Strong Test Coverage  | >60% coverage on core domain and ViewModels               |

🧠 Architectural Principles

| Principle           | Implementation                                   |
|---------------------|--------------------------------------------------|
| Offline-First       | Local database is the **single source of truth** |
| Non-blocking UI     | Coroutines + Flow, no work on the main thread    |
| Resilient Sync      | Deferred background jobs with retries            |
| Clean Architecture  | UI → ViewModel → UseCases → Repository           |
| Multiplatform Ready | Shared domain logic via KMP                      |

🏗️ Tech Stack

| Layer           | Technology                   |
|-----------------|------------------------------|
| UI              | Jetpack Compose              |
| Shared Logic    | Kotlin Multiplatform         |
| Local Storage   | SQLDelight                   |
| Networking      | Ktor Client                  |
| Auth            | Firebase Auth (Android only) |
| Background Work | WorkManager                  |
| Async           | Coroutines + Flow            |
| DI              | Koin                         |
| Testing         | JUnit + coroutine testing    |


📐 High-Level Architecture

| Layer              | Responsibility                       |
|--------------------|--------------------------------------|
| UI (Compose)       | State rendering & user interaction   |
| ViewModels         | StateFlow + Intent / Effect handling |
| UseCases           | Business rules, orchestration        |
| Repository         | Local + Remote coordination          |
| Local Data Source  | SQLDelight database                  |
| Remote Data Source | Ktor backend client                  |
| Sync Layer         | Background synchronization           |

🔄 Offline-First Flow

| Step                  | Behavior                        |
|-----------------------|---------------------------------|
| 1️⃣ User action       | User scans receipt              |
| 2️⃣ Local save        | Cart stored locally immediately |
| 3️⃣ UI update         | UI reacts from local state      |
| 4️⃣ Background sync   | WorkManager schedules sync      |
| 5️⃣ Conflict handling | Last-write-wins strategy        |
| 6️⃣ UI feedback       | Sync status exposed to UI       |

🧩 Key Implementation Highlights

| Area                 | Details                                      |
|----------------------|----------------------------------------------|
| ViewModels           | StateFlow + SharedFlow (Effects)             |
| Error Handling       | Explicit UI error states                     |
| Sync Strategy        | Retryable, resilient background jobs         |
| Dependency Injection | Modular Koin setup (Android + Common)        |
| Scalability          | Designed to plug iOS without rewriting logic |


🧪 Testing Strategy

| Scope        | Coverage  |
|--------------|-----------|
| Domain Logic | ✅ High    |
| UseCases     | ✅ Covered |
| ViewModels   | ✅ Covered |
| Repositories | ✅ Covered |
| UI           | 🚧 WIP    |


The project prioritizes deterministic, fast tests on business logic and state management.

📱 Current Platform Support

| Platform | Status                |
|----------|-----------------------|
| Android  | ✅ Fully supported     |
| iOS      | 🧩 Architecture ready |

🚀 How to Run (Android)

| Step | Action                               |
|------|--------------------------------------|
| 1    | Clone the repository                 |
| 2    | Open in Android Studio               |
| 3    | Select an Android device or emulator |
| 4    | Run the `androidApp` configuration   |


Requirements
- Android Studio (Giraffe or newer)
- Android SDK 34+
- Gradle 8+

📊 UI Navigation Overview

| Tab         | Description                    |
|-------------|--------------------------------|
| 🕘 History  | Browse saved carts             |
| 🛒 Cart     | Scan receipts and manage items |
| ⚙️ Settings | App configuration              |

🔮 Roadmap (High-Level)

| Area         | Direction                             |
|--------------|---------------------------------------|
| Backend Sync | Full integration via Ktor             |
| Statistics   | Aggregated spending insights          |
| iOS App      | SwiftUI client using shared KMP logic |

🧑‍💻 Author Notes

This project is intentionally built using patterns and constraints found in real production apps:
Offline resilience
Background synchronization
Clear state management
Testability over convenience

It serves both as a functional product and a reference architecture for offline-first mobile systems.

📄 License
MIT – free to use, adapt, and learn from.