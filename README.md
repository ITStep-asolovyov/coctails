# 🍸 МиксМастер

Премиальное приложение для поиска коктейлей с роскошным дизайном в стиле Luxury Bar.
Доступно на **Android** и **iOS**.

---

## Скриншоты / Экраны

| Сплэш | Главная | Детали | Избранное |
|-------|---------|--------|-----------|
| Золотой логотип на тёмном фоне | Герой + фильтры + популярные | Параллакс + ингредиенты | 2-колоночная сетка |

---

## Возможности

- 🔍 **Поиск** коктейлей с задержкой 500 мс (debounce)
- 🎲 **Случайный коктейль** — кнопка «Мне повезёт»
- 🍹 **Фильтр по типу алкоголя**: Водка, Ром, Джин, Текила, Виски, Вино, Без алкоголя
- 📂 **Категории** из API (Шот, Коктейль, Молочный и др.)
- ❤️ **Избранное** с локальным хранением
- 🇷🇺 **Полностью на русском**: названия, категории, бокалы, ингредиенты, инструкции
- ✨ **Премиальный дизайн**: тёмный фон, золотые акценты, glassmorphism, serif-шрифты

---

## Дизайн

**Цветовая палитра:**
| Цвет | HEX | Применение |
|------|-----|-----------|
| Фон | `#0D0D0D` | Основной фон |
| Тёмно-синий | `#1A1A2E` | Карточки, навбар |
| Золото | `#D4AF37` | Акценты, кнопки |
| Медь | `#B87333` | Вторичные акценты |
| Текст | `#F5F5F5` | Основной текст |

**Шрифты:** Serif (≈ Playfair Display) для заголовков, Sans-serif (≈ Montserrat) для тела.

---

## API

Используется [TheCocktailDB](https://www.thecocktaildb.com/api.php) — бесплатный публичный API.

| Эндпоинт | Назначение |
|----------|-----------|
| `search.php?s=` | Поиск по названию |
| `lookup.php?i=` | Детали коктейля |
| `random.php` | Случайный коктейль |
| `filter.php?i=` | Фильтр по ингредиенту |
| `filter.php?a=` | Фильтр по алкоголю |
| `filter.php?c=` | Фильтр по категории |
| `list.php?c=list` | Список категорий |

---

## Android

### Требования
- Android Studio Hedgehog или новее
- JDK 17+
- Android SDK 24+

### Запуск
```bash
# Клонировать репозиторий
git clone <repo-url>
cd coctails

# Сборка и запуск на подключённом устройстве/эмуляторе
./gradlew installDebug
```

### Стек технологий
| Технология | Версия | Назначение |
|-----------|--------|-----------|
| Jetpack Compose | 2024.x | UI |
| Material 3 | — | Компоненты |
| Navigation Compose | 2.7.x | Навигация |
| Retrofit 2 | 2.9.x | HTTP-клиент |
| Gson | 2.10.x | JSON |
| Coil | 2.x | Загрузка изображений |
| Room | 2.6.x | База данных избранного |
| Coroutines | 1.7.x | Асинхронность |
| ViewModel | 2.7.x | MVVM |

### Структура проекта
```
app/src/main/java/com/mixmaster/app/
├── data/
│   ├── api/          # Retrofit сервис и DTO-модели
│   ├── database/     # Room (FavoriteEntity, FavoriteDao, AppDatabase)
│   ├── model/        # Доменные модели, переводы
│   └── repository/   # CocktailRepository, FavoriteRepository
├── ui/
│   ├── components/   # CocktailCard, ShimmerEffect
│   ├── navigation/   # NavGraph (3 таба + Splash + Detail)
│   ├── screens/      # Home, Search, Detail, Favorites, Splash
│   ├── theme/        # Color, Type, Theme
│   └── viewmodel/    # Home, Search, Detail, Favorites ViewModels
└── MainActivity.kt
```

### Навигация
```
Splash → TabView
           ├── Главная   (Home)
           ├── Поиск     (Search)
           └── Избранное (Favorites)
                    ↓ (из любого экрана)
              Детали     (Detail) — без таббара
```

---

## iOS

### Требования
- macOS 13+
- Xcode 15+
- iOS 16+ (устройство или симулятор)

### Запуск

1. Открой Xcode → **File → New → Project → iOS App**
2. Имя проекта: `МиксМастер`, Bundle ID на своё усмотрение
3. Удали созданные Xcode файлы `ContentView.swift` и `Assets.xcassets`
4. Перетащи папку `ios/Sources/MixMaster/` в навигатор проекта (✅ Copy items if needed)
5. В `Info.plist` добавь разрешение для сети:
   ```xml
   <key>NSAppTransportSecurity</key>
   <dict>
       <key>NSAllowsArbitraryLoads</key>
       <true/>
   </dict>
   ```
6. Выбери симулятор или устройство → **Run (⌘R)**

### Стек технологий
| Технология | Назначение |
|-----------|-----------|
| SwiftUI | UI (декларативный) |
| NavigationStack | Навигация (iOS 16+) |
| URLSession + async/await | HTTP без зависимостей |
| AsyncImage | Загрузка изображений (встроенный) |
| Core Data | Хранение избранного |
| Combine | Debounce для поиска |
| @MainActor ObservableObject | MVVM |

### Структура проекта
```
ios/Sources/MixMaster/
├── MixMasterApp.swift     # Точка входа @main
├── ContentView.swift      # Splash → TabView
├── Models/
│   ├── Cocktail.swift     # Доменные модели
│   └── Translations.swift # 130+ переводов ингредиентов/категорий
├── Networking/
│   ├── APIModels.swift    # Codable DTO + маппинг
│   └── CocktailAPI.swift  # URLSession сервис
├── Storage/
│   └── PersistenceController.swift  # Core Data (без .xcdatamodeld)
├── Theme/
│   └── AppColors.swift    # Те же цвета что на Android
├── ViewModels/
│   ├── HomeViewModel.swift
│   ├── SearchViewModel.swift
│   ├── DetailViewModel.swift
│   └── FavoritesViewModel.swift
└── Views/
    ├── SplashView.swift
    ├── HomeView.swift
    ├── SearchView.swift
    ├── DetailView.swift
    ├── FavoritesView.swift
    └── Components/
        └── CocktailCard.swift
```

---

## Переводы

Все данные из API переводятся на русский через словари в `Translations.kt` / `Translations.swift`:

- **Категории**: Обычный коктейль, Шот, Кофе / Чай, Пунш и др.
- **Бокалы**: Хайбол, Фужер, Мартини, Стопка и др.
- **Алкоголь**: Алкогольный / Безалкогольный / По желанию
- **Ингредиенты**: 130+ позиций — Водка, Гренадин, Мятный ликёр, Лаймовый сок и др.
- **Инструкции**: используется поле `strInstructionsRU` из API (где доступно)

---

## Лицензия

MIT — используй свободно.
