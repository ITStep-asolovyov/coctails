import Foundation

// MARK: - Category translations
let categoryTranslations: [String: String] = [
    "Ordinary Drink":       "Обычный коктейль",
    "Cocktail":             "Коктейль",
    "Milk / Float / Shake": "Молочный / Шейк",
    "Other/Unknown":        "Другое",
    "Cocoa":                "Какао",
    "Shot":                 "Шот",
    "Coffee / Tea":         "Кофе / Чай",
    "Homemade Liqueur":     "Домашний ликёр",
    "Punch / Party Drink":  "Пунш / Вечеринка",
    "Beer":                 "Пиво",
    "Soft Drink":           "Безалкогольный напиток",
]

// MARK: - Glass translations
let glassTranslations: [String: String] = [
    "Highball glass":           "Хайбол",
    "Cocktail glass":           "Коктейльный бокал",
    "Old-fashioned glass":      "Олд Фэшнд",
    "Whiskey Glass":            "Виски бокал",
    "Collins glass":            "Коллинз",
    "Pousse cafe glass":        "Пусс-кафе",
    "Pousse-cafe glass":        "Пусс-кафе",
    "Champagne flute":          "Фужер для шампанского",
    "Whiskey sour glass":       "Бокал для сауэра",
    "Cordial glass":            "Кордиал",
    "Beer mug":                 "Пивная кружка",
    "Margarita/Coupette glass": "Маргарита",
    "Beer pilsner":             "Пильзнер",
    "Beer Glass":               "Пивной бокал",
    "Punch bowl":               "Чаша для пунша",
    "Pitcher":                  "Кувшин",
    "Pint glass":               "Пинта",
    "Copper Mug":               "Медная кружка",
    "Wine Glass":               "Бокал для вина",
    "Shot glass":               "Стопка",
    "Jar":                      "Кружка",
    "Irish coffee cup":         "Ирландский кофе",
    "Hurricane glass":          "Ураган",
    "Coffee mug":               "Кофейная кружка",
    "Balloon Glass":            "Бокал баллон",
    "Brandy snifter":           "Бокал для бренди",
    "White wine glass":         "Бокал для белого вина",
    "Nick and Nora Glass":      "Ник и Нора",
    "Martini Glass":            "Мартини",
    "Parfait glass":            "Парфе",
]

// MARK: - Alcoholic translations
let alcoholicTranslations: [String: String] = [
    "Alcoholic":        "Алкогольный",
    "Non alcoholic":    "Безалкогольный",
    "Non Alcoholic":    "Безалкогольный",
    "Optional alcohol": "По желанию",
]

// MARK: - Ingredient translations (keys are lowercase)
let ingredientTranslations: [String: String] = [
    // Spirits
    "vodka": "Водка", "gin": "Джин", "rum": "Ром", "tequila": "Текила",
    "whiskey": "Виски", "whisky": "Виски",
    "bourbon whiskey": "Бурбон", "bourbon": "Бурбон",
    "scotch": "Скотч", "blended whiskey": "Купажированный виски",
    "irish whiskey": "Ирландский виски", "rye whiskey": "Ржаной виски",
    "canadian whisky": "Канадский виски", "tennessee whiskey": "Теннессийский виски",
    "jack daniels": "Джек Дэниелс",
    "brandy": "Бренди", "cognac": "Коньяк", "pisco": "Писко",
    "sake": "Саке", "mezcal": "Мескаль", "applejack": "Эпплджек",
    "absinthe": "Абсент", "pernod": "Пастис", "sloe gin": "Терновый джин",
    // Flavoured spirits
    "peach vodka": "Персиковая водка", "raspberry vodka": "Малиновая водка",
    "vanilla vodka": "Ванильная водка", "citrus vodka": "Цитрусовая водка",
    "pepper vodka": "Перечная водка",
    "dark rum": "Тёмный ром", "light rum": "Светлый ром",
    "spiced rum": "Пряный ром", "gold rum": "Золотой ром", "coconut rum": "Кокосовый ром",
    "malibu": "Малибу",
    "silver tequila": "Серебряная текила", "gold tequila": "Золотая текила",
    "reposado tequila": "Текила репосадо", "blue agave tequila": "Текила из голубой агавы",
    // Liqueurs
    "triple sec": "Трипл Сек", "cointreau": "Куантро", "grand marnier": "Гран Марнье",
    "blue curacao": "Голубой Кюрасао", "amaretto": "Амаретто", "kahlua": "Калуа",
    "baileys irish cream": "Бейлис", "midori melon liqueur": "Мидори",
    "peach schnapps": "Персиковый шнапс", "peppermint schnapps": "Мятный шнапс",
    "butterscotch schnapps": "Ирисовый шнапс", "blueberry schnapps": "Черничный шнапс",
    "creme de menthe": "Мятный ликёр", "creme de cacao": "Шоколадный ликёр",
    "creme de cassis": "Смородиновый ликёр",
    "peach liqueur": "Персиковый ликёр", "strawberry liqueur": "Клубничный ликёр",
    "cherry liqueur": "Вишнёвый ликёр", "blackberry liqueur": "Ежевичный ликёр",
    "raspberry liqueur": "Малиновый ликёр", "melon liqueur": "Дынный ликёр",
    "lychee liqueur": "Ликёр личи", "maraschino liqueur": "Мараскино",
    "galliano": "Галлиано", "frangelico": "Франджелико",
    "chambord raspberry liqueur": "Шамбор", "chambord": "Шамбор",
    "st-germain": "Сент-Жермен", "chartreuse": "Шартрёз",
    "benedictine": "Бенедиктин", "drambuie": "Дрэмбуи",
    "aperol": "Апероль", "campari": "Кампари", "lillet blanc": "Лилле Блан",
    "cherry heering": "Хееринг",
    // Vermouth & wine
    "vermouth": "Вермут", "dry vermouth": "Сухой вермут", "sweet vermouth": "Сладкий вермут",
    "champagne": "Шампанское", "dry champagne": "Сухое шампанское",
    "prosecco": "Просекко", "sparkling wine": "Игристое вино",
    "white wine": "Белое вино", "red wine": "Красное вино",
    "rose wine": "Розовое вино", "port wine": "Портвейн",
    "sherry": "Херес", "madeira": "Мадера",
    // Beer
    "beer": "Пиво", "hard cider": "Алкогольный сидр", "apple cider": "Яблочный сидр",
    // Juices
    "lime juice": "Лаймовый сок", "lemon juice": "Лимонный сок",
    "orange juice": "Апельсиновый сок", "cranberry juice": "Клюквенный сок",
    "pineapple juice": "Ананасовый сок", "grapefruit juice": "Грейпфрутовый сок",
    "tomato juice": "Томатный сок", "apple juice": "Яблочный сок",
    "grape juice": "Виноградный сок", "mango juice": "Манговый сок",
    "passion fruit juice": "Сок маракуйи", "coconut juice": "Кокосовый сок",
    "oj": "Апельсиновый сок",
    // Soft drinks
    "soda water": "Содовая", "club soda": "Содовая", "tonic water": "Тоник",
    "cola": "Кола", "coca-cola": "Кока-Кола",
    "ginger beer": "Имбирное пиво", "ginger ale": "Имбирный эль",
    "sprite": "Спрайт", "7-up": "7-Ап", "water": "Вода",
    "coconut water": "Кокосовая вода",
    // Dairy
    "cream": "Сливки", "heavy cream": "Жирные сливки",
    "whipped cream": "Взбитые сливки", "half-and-half": "Полусливки",
    "milk": "Молоко", "coconut cream": "Кокосовые сливки",
    "coconut milk": "Кокосовое молоко", "ice cream": "Мороженое",
    // Syrups
    "sugar syrup": "Сахарный сироп", "simple syrup": "Простой сироп",
    "grenadine": "Гренадин", "orgeat syrup": "Оршад",
    "agave syrup": "Агавовый сироп", "maple syrup": "Кленовый сироп",
    "raspberry syrup": "Малиновый сироп", "honey syrup": "Медовый сироп",
    // Bitters
    "bitters": "Биттер", "angostura bitters": "Биттер Ангостура",
    "peach bitters": "Персиковый биттер", "orange bitters": "Апельсиновый биттер",
    "aromatic bitters": "Ароматный биттер",
    // Fruits & garnishes
    "lime": "Лайм", "lemon": "Лимон", "orange": "Апельсин",
    "pineapple": "Ананас", "strawberry": "Клубника", "raspberry": "Малина",
    "blackberry": "Ежевика", "blueberry": "Черника",
    "cherry": "Вишня", "maraschino cherry": "Коктейльная вишня",
    "mango": "Манго", "passion fruit": "Маракуйя", "watermelon": "Арбуз",
    "peach": "Персик", "apple": "Яблоко", "banana": "Банан",
    "kiwi": "Киви", "lychee": "Личи", "coconut": "Кокос",
    "olive": "Оливка", "olives": "Оливки",
    "lemon peel": "Цедра лимона", "orange peel": "Цедра апельсина", "lime peel": "Цедра лайма",
    "mint": "Мята", "basil": "Базилик", "rosemary": "Розмарин",
    "lavender": "Лаванда", "cucumber": "Огурец", "celery": "Сельдерей", "ginger": "Имбирь",
    // Pantry
    "sugar": "Сахар", "salt": "Соль", "honey": "Мёд",
    "cinnamon": "Корица", "nutmeg": "Мускатный орех", "pepper": "Перец",
    "tabasco": "Табаско", "worcestershire sauce": "Вустерширский соус",
    "ice": "Лёд", "egg": "Яйцо",
    "egg white": "Яичный белок", "egg yolk": "Яичный желток",
    "cream of coconut": "Кокосовые сливки",
    "chocolate syrup": "Шоколадный сироп", "vanilla extract": "Экстракт ванили",
    "coffee": "Кофе", "espresso": "Эспрессо", "tea": "Чай",
]

// MARK: - Extension helpers
extension String {
    func translateCategory()   -> String { categoryTranslations[self]   ?? self }
    func translateGlass()      -> String { glassTranslations[self]      ?? self }
    func translateAlcoholic()  -> String { alcoholicTranslations[self]  ?? self }

    func translateIngredient() -> String {
        let lower = self.lowercased().trimmingCharacters(in: .whitespaces)
        if let exact = ingredientTranslations[lower] { return exact }
        // Partial match — longest key wins
        return ingredientTranslations
            .filter { lower.contains($0.key) }
            .max(by: { $0.key.count < $1.key.count })
            .map(\.value) ?? self
    }
}
