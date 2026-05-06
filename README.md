# Mobile Shop (Exercise 1)

Kotlin + Jetpack Compose implementation of a simple shoe shop app for COMP5450.

The app lets users browse products, open product details, choose color/size, add items to a bag, and view the bag total.

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- Android ViewModel + StateFlow
- Gradle (Android project)

## Features

- Landing/splash screen
- Home screen with category chips and product grid
- Product detail screen:
  - color selection
  - EU/US/UK size selection
  - add-to-bag action
- Bag screen:
  - selected items list
  - remove item action
  - running total price

> Data source is in-memory sample data (`SampleData`), with no backend or database.

## Project Structure

- `app/src/main/java/com/comp5450/mobileshop/MainActivity.kt`  
  App entry point.
- `app/src/main/java/com/comp5450/mobileshop/data/`  
  Product/cart models and sample catalog.
- `app/src/main/java/com/comp5450/mobileshop/ui/ShopViewModel.kt`  
  Search, category selection, bag state, and total calculation.
- `app/src/main/java/com/comp5450/mobileshop/ui/screens/`  
  Compose screens (home, detail, bag, splash, profile placeholder).
- `app/src/main/java/com/comp5450/mobileshop/ui/navigation/`  
  Navigation host and routes.

## Getting Started

### Requirements

- Android Studio (latest stable)
- JDK 17 for Gradle
- Android SDK + emulator (or a physical Android device)

### Run in Android Studio

1. Open `exercises/exercise-1` as a project.
2. Set Gradle JDK to 17.
3. Sync Gradle.
4. Run the `app` configuration on an emulator/device.

### Build from Terminal

```bash
cd /Users/lmc/repos/mobile_programming/exercises/exercise-1
export JAVA_HOME="$(brew --prefix openjdk@17)"
export PATH="$JAVA_HOME/bin:$PATH"
./gradlew :app:assembleDebug
```

APK output:

- `app/build/outputs/apk/debug/app-debug.apk`

## Screenshots

Runtime screenshots are included in `README.pdf`:

- Home
- Product detail
- Bag with total
- Profile placeholder

## Notes

This project was built for course exercise requirements and focuses on clean app flow and functional shopping-bag behavior rather than production backend integration.
