# AMRO

This application is an MVP built to provide an intuitive experience for exploring trending movies, following modern Android best practices and a scalable architecture.

## Core Architecture
The app follows **Clean Architecture** principles using the **MVVM** (Model-View-ViewModel) pattern. It is currently a single-module project organized by layers to ensure a clear separation of concerns:

- **UI Layer**: Handles data presentation using **Jetpack Compose**. ViewModels manage the UI state reactively, ensuring a single source of truth for filtering and sorting logic.
- **Domain Layer**: Contains business logic, including UseCases and Domain Models. It defines the contract for data operations, remaining independent of the UI and data sources.
- **Data Layer**: Responsible for data retrieval from the TMDB API. It handles network communication, data mapping, and caching strategies.

## Project Structure
```
├── data                 # Repositories, API Service, and Data Models
├── di                   # Hilt Modules for Dependency Injection
├── domain               # UseCases, Repository Interfaces, and Entities
├── core                 # Shared utilities (ApiResult, ErrorType)
└── ui                   # Presentation layer
     ├── mapper          # UI-to-Domain mappers
     ├── model           # UI-specific models
     ├── theme           # Compose theme and styling
     └── view
          ├── component  # Reusable UI components
          ├── detail     # Movie detail screen
          └── movie      # Trending movie list screen
```
