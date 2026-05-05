# Exercise 1 – Kotlin Jetpack Compose mobile shop

This is my solution for Exercise 1 (30 pts).  
The goal is a small shoe shop built with **Kotlin + Jetpack Compose** where users can pick items and see a running total in the shopping bag.

## What the app does

- Shows a **landing screen** similar to the handout.
- Home screen:
  - simple category chips (Lifestyle/Basketball/Running)
  - grid of sample shoes
  - quick “+” button to drop an item into the bag
- Detail screen for each shoe:
  - colour swatches
  - EU/US/UK size options
  - “Add to Bag” button
- Bag tab:
  - list of chosen items (name, colour, size, quantity)
  - remove button
  - total price at the bottom

All product data lives in an in‑memory list (`SampleData`) – no database or network.

## How to run it

### In Android Studio (recommended)

1. Open this folder as a project: `exercises/exercise-1`.
2. Use **JDK 17** for Gradle.
3. Let Gradle sync finish.
4. Run the `app` configuration on an emulator or phone.

### From the command line (if your SDK is set up)

```bash
export JAVA_HOME=/path/to/jdk-17   # adjust for your machine
cd /Users/lmc/repos/mobile_programming/exercises/exercise-1
./gradlew :app:assembleDebug
```

The debug APK ends up here:

`app/build/outputs/apk/debug/app-debug.apk`

## Files to pay attention to

- `MainActivity.kt` – entry point that wires the theme and navigation.
- `data/Models.kt`, `data/SampleData.kt` – shoe models and hard‑coded catalogue.
- `ui/ShopViewModel.kt` – search, selected category, bag contents, and total.
- `ui/screens/*` – splash, home, detail, and bag screens built with Compose.
- `ui/components/ProductHeroArt.kt` – fake “shoe card” artwork to match the mockup.

## Submission checklist (for me)

- [x] Project builds and runs on an emulator/phone.
- [x] Users can add items to the bag and see the total.
- [x] `README.pdf` in this folder explains how to run the app.
- [x] Repo pushed to GitHub and ZIP exported for D2L.

I’ve kept the design close to the sample images but focused mainly on getting clean, readable Kotlin code that I can walk through in class if asked.
