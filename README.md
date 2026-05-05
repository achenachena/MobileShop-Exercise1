# Exercise 1 — Kotlin Jetpack Compose Mobile Shop

**Type:** Individual  
**Points:** 30 points  
**Expected stack:** Kotlin + Jetpack Compose  

## Assignment brief

Design and implement a mobile shop app with these core requirements:

- Use **Kotlin Jetpack Compose**
- No database is required; a **simple in-memory list** is enough
- Let users **pick items**
- Show the **shopping bag total**
- Provide a **README/PDF** explaining how to configure and run the solution
- Upload the **full Android Studio project** to GitHub and submit a ZIP to D2L

## What this solution includes

- A **splash / landing screen** inspired by the provided mockup
- A **home screen** with:
  - featured product banner
  - category chips
  - product grid
  - quick add-to-bag buttons
- A **product detail screen** with:
  - color selection
  - size selection
  - add-to-bag action
- A **bag tab** that shows chosen items and computes the **total price**
- In-memory sample catalog, so the app works without any backend or database

## Project structure

```text
exercise-1/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/comp5450/mobileshop/
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   └── ui/
│       └── res/
├── gradle/wrapper/
├── build.gradle
├── settings.gradle
└── gradlew
```

## How to run

### Option 1 — Android Studio

1. Open this folder in **Android Studio**:
   - `/Users/lmc/repos/mobile_programming/exercises/exercise-1`
2. Make sure Gradle uses **JDK 17**
3. Let Gradle sync finish
4. Start an emulator or connect a physical Android phone
5. Run the `app` configuration

### Option 2 — command line

If your machine has Android SDK + JDK 17 configured:

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
cd /Users/lmc/repos/mobile_programming/exercises/exercise-1
./gradlew :app:assembleDebug
```

## Build output

The debug APK builds successfully at:

`/Users/lmc/repos/mobile_programming/exercises/exercise-1/app/build/outputs/apk/debug/app-debug.apk`

## Important environment notes

- This project is set up for **Gradle on JDK 17**
- `local.properties` points to the Android SDK used on this machine:
  - `/opt/homebrew/share/android-commandlinetools`
- If you open the project on another machine, Android Studio may regenerate `local.properties`

## Submission notes

Before final submission, make sure you also include:

- [ ] Your **public GitHub repository link**
- [ ] A ZIP containing the full Android Studio project
- [ ] Your Kotlin files + image assets + README PDF
- [ ] App screenshots taken from your emulator or physical device

## Rubric alignment

This implementation is designed to cover the visible rubric categories:

- **Programming (Kotlin)** — complete Compose app with navigation and state
- **Functionality** — users can browse products, add items, and see bag total
- **App design / responsive design** — polished layout with adaptive Compose UI

## Integrity note

You should still read through the code and make sure you can explain every screen, model, and state update before submitting.
