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

### Пошаговая инструкция открытия в Xcode

---

#### Шаг 1 — Создай новый проект

1. Открой **Xcode**
2. На стартовом экране нажми **Create New Project…**
   _(или в меню: File → New → Project…)_
3. Выбери платформу **iOS**, шаблон **App** → нажми **Next**

   ![Шаблон App](https://developer.apple.com/design/human-interface-guidelines/images/intro/platforms/platform-iOS-intro_2x.png)

4. Заполни поля:

   | Поле | Значение |
   |------|---------|
   | **Product Name** | `МиксМастер` |
   | **Team** | Выбери свой Apple ID (или None для симулятора) |
   | **Organization Identifier** | Любой, например `com.yourname` |
   | **Bundle Identifier** | Заполнится автоматически |
   | **Interface** | **SwiftUI** |
   | **Language** | **Swift** |

   > ⚠️ Убедись, что выбрано **SwiftUI**, а не Storyboard.

5. Нажми **Next**, выбери папку для сохранения → **Create**

---

#### Шаг 2 — Удали лишние файлы Xcode

Xcode создаёт файлы-заглушки, которые конфликтуют с нашим кодом. Удали их:

1. В левой панели (Project Navigator) найди и выдели:
   - `ContentView.swift`
   - `Assets.xcassets`

   > Эти два файла — они сразу видны после создания проекта.

2. Нажми **Delete** на клавиатуре
3. В диалоге выбери **Move to Trash** (не Keep Files)

   > Файл `МиксМастерApp.swift` (точка входа `@main`) тоже нужно удалить — наш `MixMasterApp.swift` заменит его.

4. Удали также `МиксМастерApp.swift` → **Move to Trash**

---

#### Шаг 3 — Добавь исходный код

1. Открой **Finder** и перейди в папку репозитория `coctails/ios/Sources/`
2. Перетащи папку **`MixMaster`** из Finder прямо в Project Navigator Xcode
   _(бросай её под значком проекта, на самый верхний уровень)_
3. В появившемся диалоге настрой так:

   | Опция | Значение |
   |-------|---------|
   | **Destination** | ✅ Copy items if needed |
   | **Added folders** | ◉ Create groups _(не Create folder references!)_ |
   | **Add to targets** | ✅ МиксМастер |

4. Нажми **Finish**

   После этого в Project Navigator появится папка `MixMaster` со всеми подпапками:
   ```
   MixMaster/
   ├── MixMasterApp.swift
   ├── ContentView.swift
   ├── Models/
   ├── Networking/
   ├── Storage/
   ├── Theme/
   ├── ViewModels/
   └── Views/
   ```

---

#### Шаг 4 — Разреши сеть в Info.plist

TheCocktailDB работает по HTTPS, но Xcode по умолчанию блокирует некоторые соединения. Нужно добавить исключение:

**Способ А — через редактор (рекомендуется):**

1. В Project Navigator кликни на файл **Info.plist**
2. Наведи курсор на любую строку — появится кнопка **+**
3. Нажми **+**, в поле ключа напечатай:
   `App Transport Security Settings` → нажми Enter
4. Раскрой появившуюся строку (нажми на треугольник ▶)
5. Снова нажми **+** внутри неё, введи ключ:
   `Allow Arbitrary Loads` → нажми Enter
6. В колонке **Value** выбери **YES**

**Способ Б — через исходник XML:**

1. Правый клик на **Info.plist** → **Open As → Source Code**
2. Перед закрывающим тегом `</dict>` добавь:

   ```xml
   <key>NSAppTransportSecurity</key>
   <dict>
       <key>NSAllowsArbitraryLoads</key>
       <true/>
   </dict>
   ```

---

#### Шаг 5 — Выбери симулятор и запусти

1. В верхней панели Xcode нажми на название устройства рядом со схемой
   _(по умолчанию там что-то вроде «iPhone 15 Pro»)_
2. Выбери любой симулятор **iPhone** с iOS 16 или новее
   _(рекомендуется: iPhone 15 Pro, iPhone 14)_
3. Нажми кнопку **▶ Run** или сочетание клавиш **⌘R**
4. Xcode скомпилирует проект и откроет симулятор (~20–30 секунд при первом запуске)

   > 💡 При первом запуске симулятор загружается дольше обычного — это нормально.

---

#### Шаг 6 — Запуск на реальном iPhone (опционально)

1. Подключи iPhone к Mac кабелем
2. На iPhone появится запрос «Доверять этому компьютеру?» → нажми **Доверять**
3. В Xcode выбери своё устройство в списке (вместо симулятора)
4. Нажми **▶ Run** (⌘R)
5. Если появится ошибка подписи:
   - Кликни на проект в Project Navigator (синяя иконка вверху)
   - Вкладка **Signing & Capabilities**
   - В поле **Team** выбери свой Apple ID
   - Xcode автоматически создаст профиль разработки
6. На iPhone перейди: **Настройки → Основные → VPN и управление устройством**
   → выбери своё имя разработчика → **Доверять**

---

#### Возможные ошибки и решения

| Ошибка | Причина | Решение |
|--------|---------|---------|
| `Cannot find type 'X' in scope` | Файл не добавлен в target | Выдели файл в Navigator → Inspector → поставь галочку у target |
| `@main attribute cannot be applied` | Осталось два `@main` | Удали `МиксМастерApp.swift`, оставь только `MixMasterApp.swift` |
| Изображения не грузятся | Нет разрешения сети | Повтори Шаг 4 |
| `Signing for requires a development team` | Не выбран Team | Signing & Capabilities → выбери Apple ID |
| Симулятор не запускается | Старая версия Xcode | Обнови Xcode до версии 15+ |

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
