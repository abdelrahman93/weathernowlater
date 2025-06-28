# WeatherNowLater

## Project Structure

```
weathernowlater/
├── app/           # Application entry point, DI, navigation, and main activity
├── core/          # Shared resources: colors, constants, types, and utilities
├── data/          # Data layer: models, API, local/remote sources, mappers
├── features/      # Feature modules: UI, domain logic, presentation, theming
```

---

## Module Overview

### 1. `app`
- **Purpose:** The main application module. Sets up the app entry point, dependency injection, and navigation.
- **Key files:**
  - `MainActivity.kt`: Hosts the navigation and top-level theme.
  - `WeatherNowLaterApp.kt`: Application class for Hilt DI.
  - `di/`: Dependency injection setup.

### 2. `core`
- **Purpose:** Contains shared resources and utilities used across modules.
- **Key files:**
  - `common/Color.kt`: Centralized color definitions.
  - `common/Constants.kt`, `common/Type.kt`: Shared constants and types.
  - `common/util/`: Utility functions.

### 3. `data`
- **Purpose:** Handles all data operations, including models, API services, and data sources.
- **Key directories:**
  - `model/`: Data models (e.g., `CityWeather.kt`, `ForecastItem.kt`).
  - `remote/`: API interfaces (`WeatherApiService.kt`, `ForecastApi.kt`), DTOs, and remote data sources.
  - `local/`: Local data sources (e.g., Room, preferences).
  - `mapper/`: Data mappers for transforming between layers.

### 4. `features`
- **Purpose:** Contains all feature-specific code, including UI, domain logic, and theming.
- **Key directories:**
  - `presentation/`: UI screens and view models (e.g., `SplashScreen.kt`, `cityinput/`, `weatherdetails/`, `forecast/`).
  - `domain/`: Business logic, use cases, and repository interfaces.
    - `usecase/`: Application use cases (e.g., `GetForecastUseCase.kt`).
    - `repository/`: Repository interfaces and implementations.
  - `theme/`: Custom Compose theme (`WeathernowlaterTheme`).
  - `di/`: Feature-specific dependency injection.

---

## Example Structure Tree

```
app/
  └── src/main/java/com/task/weathernowlater/
      ├── MainActivity.kt
      ├── WeatherNowLaterApp.kt
      └── di/
core/
  └── src/main/java/com/task/core/
      └── common/
          ├── Color.kt
          ├── Constants.kt
          ├── Type.kt
          └── util/
data/
  └── src/main/java/com/task/data/
      ├── model/
      ├── remote/
      │   └── api/
      │       ├── WeatherApiService.kt
      │       └── ForecastApi.kt
      ├── local/
      └── mapper/
features/
  └── src/main/java/com/task/features/
      ├── presentation/
      │   ├── SplashScreen.kt
      │   ├── cityinput/
      │   ├── weatherdetails/
      │   └── forecast/
      ├── domain/
      │   ├── usecase/
      │   └── repository/
      ├── theme/
      │   └── Theme.kt
      └── di/
```

