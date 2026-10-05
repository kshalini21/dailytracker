# DailyTrack — Gamified College Productivity App

A native Android application (Java + XML) for students to manage tasks, habits, notes, class schedules, exams, and focus sessions — with a built-in XP/level/streak gamification system.

## Features
- 🏠 **Home Dashboard** — Daily progress, streak, XP bar, quick stats, today's schedule, due-soon tasks
- ✅ **Tasks** — Create, edit, delete tasks with priority (Low/Medium/High), category, due date, search & filter
- 💪 **Habits** — Weekly habit tracker with completion circles, streak tracking, XP rewards
- 📝 **Notes** — Rich notes with categories (College/Personal/Ideas/Projects), search
- 📅 **Schedule** — Class timetable by day of week
- 📆 **Exams** — Exam countdown with days remaining
- ⏱️ **Focus Timer** — Pomodoro-style focus sessions (25/50/90 min) with session logging
- 🏆 **Profile & Achievements** — XP, levels, streak, achievement system
- 🎮 **Gamification** — XP for every action, level progression, achievement unlocks

## Tech Stack
- **Language**: Java
- **Architecture**: MVVM (ViewModel + LiveData + Repository)
- **Database**: Room (SQLite) — offline-first, no cloud backend
- **UI**: XML layouts + Material Components 1.12
- **View Binding**: Yes
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36

## Design System
- Primary: `#7B78E4` (indigo purple)
- Background: `#F8F7FF` (soft lavender white)
- Success: `#4CD080` | Warning: `#FFB020` | Danger: `#FF6B6B`
- 20dp card radius, 28dp pill radius, 8dp spacing system

## Getting Started
1. Clone the repo
2. Open `dailytrack/` in Android Studio (Hedgehog or later)
3. Sync Gradle
4. Run on emulator or physical device (API 24+)

## Project Structure
```
app/src/main/java/com/dailytrack/app/
├── data/
│   ├── db/          # Room entities + DAOs + AppDatabase
│   └── repository/  # AppRepository (single data source)
├── ui/              # Activities + Fragments + ViewModels
├── adapter/         # RecyclerView adapters
└── util/            # DateUtils, XpEngine
```

## Screens
| Screen | Access |
|--------|--------|
| Splash | App launch |
| Onboarding | First launch only |
| Home | Bottom nav tab 1 |
| Tasks | Bottom nav tab 2 |
| Habits | Bottom nav tab 3 |
| Notes | Bottom nav tab 4 |
| Profile | Home avatar tap |
| Schedule | Home → "View all" |
| Exams | FAB → Exam |
| Focus | FAB → Focus |
| Settings | Profile → Settings |
| Progress | Profile → Statistics |
