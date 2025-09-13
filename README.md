Android News Application 📰
A modern, native Android news application built with Kotlin and Jetpack Compose. It provides a seamless and responsive news-reading experience by fetching real-time headlines from a live news API, allowing for advanced article searching, and featuring a personalized bookmarking system with offline access.

This project is a demonstration of modern Android development best practices, showcasing a clean MVVM architecture, declarative UI, and efficient data handling for a robust and maintainable codebase.

📋 Table of Contents
Features

Tech Stack & Architecture

Setup & Installation

🚀 Features
Real-Time Headlines: Fetches and displays up-to-the-minute news headlines from a live news API.

Modular UI: Built with Jetpack Compose, creating a declarative, reusable, and modern user interface.

Advanced Article Search: Integrates functionality to search for specific news articles using dynamic API queries.

Personalized Bookmarking: Allows users to save or bookmark their favorite articles for later reading.

Offline Storage: Bookmarked articles are saved to a local Room DB database, ensuring persistent offline access.

🛠️ Tech Stack & Architecture
Language: Kotlin - The official, modern language for Android development.

UI: Jetpack Compose - Google's recommended declarative UI toolkit for building native UIs.

Architecture: MVVM (Model-View-ViewModel) - A robust architectural pattern that separates the UI from business logic, improving testability and scalability.

Networking: Retrofit - A type-safe HTTP client for efficient and clean API communication.

Database: Room DB - An abstraction layer over SQLite for reliable and easy-to-manage local data persistence.

Asynchronous Programming: Kotlin Coroutines - For managing background threads to ensure a smooth, non-blocking, and responsive user interface.

⚙️ Setup & Installation
To build and run this project locally, follow these steps:

Clone the repository to your local machine.

Open the project in the latest version of Android Studio.

The IDE will automatically handle the Gradle build and download all necessary dependencies.

Run the application on an Android emulator or a physical device.