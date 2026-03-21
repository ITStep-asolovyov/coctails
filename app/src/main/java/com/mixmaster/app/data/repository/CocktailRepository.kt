package com.mixmaster.app.data.repository

import com.mixmaster.app.data.model.*

object CocktailRepository {

    fun getAllCocktails(): List<Cocktail> = cocktails

    fun getById(id: Int): Cocktail? = cocktails.find { it.id == id }

    fun filter(
        alcoholType: AlcoholType? = null,
        flavors: List<FlavorType> = emptyList(),
        difficulty: Difficulty? = null,
        maxStrength: Int? = null
    ): List<Cocktail> = cocktails.filter { c ->
        (alcoholType == null || c.alcoholType == alcoholType) &&
        (flavors.isEmpty() || flavors.any { it in c.flavors }) &&
        (difficulty == null || c.difficulty == difficulty) &&
        (maxStrength == null || c.strengthPercent <= maxStrength)
    }

    private val cocktails: List<Cocktail> = listOf(
        Cocktail(
            id = 1,
            name = "Мохито",
            description = "Освежающий кубинский коктейль с мятой и лаймом.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.REFRESHING, FlavorType.SWEET, FlavorType.SOUR),
            difficulty = Difficulty.EASY,
            strengthPercent = 10,
            ingredients = listOf("50 мл белого рома", "2 ч.л. сахара", "Сок 1 лайма", "Листья мяты", "Содовая вода", "Лёд"),
            instructions = listOf(
                "Положить мяту и сахар в стакан, слегка помять.",
                "Выдавить сок лайма.",
                "Добавить лёд и ром.",
                "Долить содовой водой и перемешать."
            )
        ),
        Cocktail(
            id = 2,
            name = "Маргарита",
            description = "Классический мексиканский коктейль с текилой и лаймом.",
            alcoholType = AlcoholType.TEQUILA,
            flavors = listOf(FlavorType.SOUR, FlavorType.STRONG),
            difficulty = Difficulty.EASY,
            strengthPercent = 25,
            ingredients = listOf("50 мл текилы", "25 мл трипл-сека", "25 мл сока лайма", "Соль для края бокала", "Лёд"),
            instructions = listOf(
                "Натереть край бокала лаймом и обмакнуть в соль.",
                "В шейкере смешать текилу, трипл-сек и сок лайма со льдом.",
                "Процедить в бокал."
            )
        ),
        Cocktail(
            id = 3,
            name = "Космополитен",
            description = "Элегантный коктейль с водкой и клюквенным соком.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.SOUR, FlavorType.SWEET),
            difficulty = Difficulty.EASY,
            strengthPercent = 20,
            ingredients = listOf("40 мл водки", "15 мл трипл-сека", "30 мл клюквенного сока", "15 мл сока лайма", "Лёд"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Процедить в охлаждённый бокал мартини.",
                "Украсить цедрой лайма."
            )
        ),
        Cocktail(
            id = 4,
            name = "Пина Колада",
            description = "Тропический коктейль с ромом, кокосом и ананасом.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 13,
            ingredients = listOf("50 мл белого рома", "30 мл кокосового крема", "120 мл ананасового сока", "Лёд", "Ананас для украшения"),
            instructions = listOf(
                "Взбить все ингредиенты в блендере со льдом.",
                "Перелить в высокий стакан.",
                "Украсить ломтиком ананаса."
            )
        ),
        Cocktail(
            id = 5,
            name = "Негрони",
            description = "Горький итальянский аперитив с джином.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.BITTER, FlavorType.STRONG),
            difficulty = Difficulty.EASY,
            strengthPercent = 28,
            ingredients = listOf("30 мл джина", "30 мл кампари", "30 мл красного вермута", "Лёд", "Апельсиновая цедра"),
            instructions = listOf(
                "Смешать все ингредиенты в стакане со льдом.",
                "Перемешать барной ложкой.",
                "Украсить апельсиновой цедрой."
            )
        ),
        Cocktail(
            id = 6,
            name = "Дайкири",
            description = "Классический коктейль с ромом и лаймом.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.SOUR, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 20,
            ingredients = listOf("60 мл белого рома", "30 мл сока лайма", "15 мл сахарного сиропа", "Лёд"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Энергично взболтать.",
                "Процедить в охлаждённый бокал."
            )
        ),
        Cocktail(
            id = 7,
            name = "Олд Фэшнд",
            description = "Один из старейших коктейлей на основе виски.",
            alcoholType = AlcoholType.WHISKEY,
            flavors = listOf(FlavorType.STRONG, FlavorType.BITTER),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 35,
            ingredients = listOf("60 мл бурбона", "2-3 капли биттера Ангостура", "1 куб сахара", "Несколько капель воды", "Лёд", "Цедра апельсина"),
            instructions = listOf(
                "Размять сахар с биттером и водой на дне стакана.",
                "Добавить лёд и виски.",
                "Аккуратно перемешать.",
                "Украсить цедрой апельсина."
            )
        ),
        Cocktail(
            id = 8,
            name = "Апероль Шприц",
            description = "Лёгкий игристый итальянский коктейль.",
            alcoholType = AlcoholType.WINE,
            flavors = listOf(FlavorType.BITTER, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 11,
            ingredients = listOf("90 мл просекко", "60 мл апероля", "30 мл содовой", "Лёд", "Долька апельсина"),
            instructions = listOf(
                "Наполнить бокал льдом.",
                "Добавить просекко, затем апероль.",
                "Долить содовой и осторожно перемешать.",
                "Украсить долькой апельсина."
            )
        ),
        Cocktail(
            id = 9,
            name = "Мартини",
            description = "Классический коктейль — символ утончённости.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.STRONG, FlavorType.BITTER),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 30,
            ingredients = listOf("60 мл джина", "10 мл сухого вермута", "Оливка или лимонная цедра"),
            instructions = listOf(
                "Охладить бокал мартини.",
                "Перемешать джин и вермут с льдом в миксинг-гласе.",
                "Процедить в бокал.",
                "Украсить оливкой или цедрой."
            )
        ),
        Cocktail(
            id = 10,
            name = "Сангрия",
            description = "Испанский винный пунш с фруктами.",
            alcoholType = AlcoholType.WINE,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 10,
            ingredients = listOf("750 мл красного вина", "100 мл апельсинового сока", "50 мл коньяка", "2 ст.л. сахара", "Апельсин, лимон, яблоко — нарезанные", "Лёд"),
            instructions = listOf(
                "Смешать вино, сок, коньяк и сахар.",
                "Добавить нарезанные фрукты.",
                "Оставить настаиваться минимум 2 часа.",
                "Подавать со льдом."
            )
        ),
        Cocktail(
            id = 11,
            name = "Московский Мул",
            description = "Острый и освежающий коктейль с имбирным пивом.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.REFRESHING, FlavorType.SOUR),
            difficulty = Difficulty.EASY,
            strengthPercent = 12,
            ingredients = listOf("50 мл водки", "150 мл имбирного пива", "Сок половины лайма", "Лёд", "Листья мяты"),
            instructions = listOf(
                "Наполнить медную кружку льдом.",
                "Добавить водку и сок лайма.",
                "Долить имбирным пивом.",
                "Украсить мятой и долькой лайма."
            )
        ),
        Cocktail(
            id = 12,
            name = "Тequila Sunrise",
            description = "Яркий коктейль, напоминающий восход солнца.",
            alcoholType = AlcoholType.TEQUILA,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 15,
            ingredients = listOf("50 мл текилы", "120 мл апельсинового сока", "15 мл гренадина", "Лёд"),
            instructions = listOf(
                "Наполнить стакан льдом.",
                "Влить текилу и апельсиновый сок, перемешать.",
                "Медленно влить гренадин по краю стакана — он опустится на дно.",
                "Не перемешивать перед подачей."
            )
        ),
        Cocktail(
            id = 13,
            name = "Лонг Айленд Айс Ти",
            description = "Крепкий коктейль, похожий по виду на чай со льдом.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.STRONG, FlavorType.SOUR),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 22,
            ingredients = listOf("15 мл водки", "15 мл рома", "15 мл джина", "15 мл текилы", "15 мл трипл-сека", "25 мл лимонного сока", "Кола", "Лёд"),
            instructions = listOf(
                "Смешать все алкогольные ингредиенты и лимонный сок со льдом.",
                "Процедить в высокий стакан со льдом.",
                "Долить колой.",
                "Украсить долькой лимона."
            )
        ),
        Cocktail(
            id = 14,
            name = "Клубничный Дайкири",
            description = "Фруктовая версия классического Дайкири.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 15,
            ingredients = listOf("50 мл белого рома", "30 мл сока лайма", "15 мл сахарного сиропа", "6 клубник", "Лёд"),
            instructions = listOf(
                "Все ингредиенты взбить в блендере со льдом.",
                "Перелить в бокал.",
                "Украсить клубникой."
            )
        ),
        Cocktail(
            id = 15,
            name = "Вишнёвый Сауэр",
            description = "Кислый коктейль с виски и вишнёвым соком.",
            alcoholType = AlcoholType.WHISKEY,
            flavors = listOf(FlavorType.SOUR, FlavorType.SWEET),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 20,
            ingredients = listOf("50 мл бурбона", "25 мл вишнёвого сока", "25 мл лимонного сока", "15 мл сахарного сиропа", "Белок яйца", "Лёд"),
            instructions = listOf(
                "Все ингредиенты взболтать в шейкере без льда (dry shake).",
                "Добавить лёд и взболтать ещё раз.",
                "Процедить в бокал.",
                "Украсить вишней."
            )
        ),
        Cocktail(
            id = 16,
            name = "Джин Тоник",
            description = "Простой и освежающий классический коктейль.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.BITTER, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 10,
            ingredients = listOf("50 мл джина", "150 мл тоника", "Лёд", "Долька лайма или огурца"),
            instructions = listOf(
                "Наполнить стакан льдом.",
                "Добавить джин.",
                "Долить тоником.",
                "Украсить лаймом или огурцом."
            )
        ),
        Cocktail(
            id = 17,
            name = "Эспрессо Мартини",
            description = "Кофейный коктейль для любителей бодрости.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.BITTER, FlavorType.STRONG),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 22,
            ingredients = listOf("50 мл водки", "30 мл кофейного ликёра", "30 мл свежего эспрессо", "Лёд", "Кофейные зёрна для украшения"),
            instructions = listOf(
                "Приготовить эспрессо и дать немного остыть.",
                "Все ингредиенты смешать в шейкере со льдом.",
                "Энергично взболтать.",
                "Процедить в охлаждённый бокал мартини.",
                "Украсить тремя кофейными зёрнами."
            )
        ),
        Cocktail(
            id = 18,
            name = "Бурбон Сауэр",
            description = "Кислый коктейль на основе бурбона.",
            alcoholType = AlcoholType.WHISKEY,
            flavors = listOf(FlavorType.SOUR, FlavorType.STRONG),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 25,
            ingredients = listOf("60 мл бурбона", "30 мл лимонного сока", "15 мл сахарного сиропа", "Белок яйца (по желанию)", "Лёд"),
            instructions = listOf(
                "Взболтать все ингредиенты в шейкере без льда.",
                "Добавить лёд и взболтать снова.",
                "Процедить в бокал со льдом.",
                "Украсить долькой лимона и вишней."
            )
        ),
        Cocktail(
            id = 19,
            name = "Манхэттен",
            description = "Классический американский коктейль с виски.",
            alcoholType = AlcoholType.WHISKEY,
            flavors = listOf(FlavorType.STRONG, FlavorType.BITTER),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 35,
            ingredients = listOf("60 мл ржаного виски", "30 мл сладкого вермута", "2-3 капли биттера Ангостура", "Лёд", "Вишня мараскино"),
            instructions = listOf(
                "Смешать все ингредиенты в миксинг-гласе со льдом.",
                "Перемешать барной ложкой.",
                "Процедить в охлаждённый бокал.",
                "Украсить вишней."
            )
        ),
        Cocktail(
            id = 20,
            name = "Пало Корво",
            description = "Простой испанский коктейль с ромом и колой.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.SWEET, FlavorType.STRONG),
            difficulty = Difficulty.EASY,
            strengthPercent = 12,
            ingredients = listOf("50 мл тёмного рома", "150 мл колы", "Лёд", "Долька лайма"),
            instructions = listOf(
                "Наполнить стакан льдом.",
                "Добавить ром.",
                "Долить колой и осторожно перемешать.",
                "Украсить долькой лайма."
            )
        ),
        Cocktail(
            id = 21,
            name = "Мимоза",
            description = "Лёгкий игристый коктейль для бранча.",
            alcoholType = AlcoholType.WINE,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 8,
            ingredients = listOf("90 мл шампанского или просекко", "90 мл апельсинового сока"),
            instructions = listOf(
                "Охладить бокал для шампанского.",
                "Налить апельсиновый сок.",
                "Осторожно долить шампанским.",
                "Не перемешивать."
            )
        ),
        Cocktail(
            id = 22,
            name = "Секс на пляже",
            description = "Тропический фруктовый коктейль.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 12,
            ingredients = listOf("40 мл водки", "20 мл персикового шнапса", "80 мл апельсинового сока", "80 мл клюквенного сока", "Лёд"),
            instructions = listOf(
                "Наполнить высокий стакан льдом.",
                "Добавить водку и шнапс.",
                "Влить апельсиновый и клюквенный сок.",
                "Слегка перемешать."
            )
        ),
        Cocktail(
            id = 23,
            name = "Голубая Лагуна",
            description = "Яркий коктейль голубого цвета с водкой.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR),
            difficulty = Difficulty.EASY,
            strengthPercent = 14,
            ingredients = listOf("30 мл водки", "15 мл голубого кюрасао", "90 мл лимонада", "Лёд", "Долька лимона"),
            instructions = listOf(
                "Наполнить стакан льдом.",
                "Добавить водку и кюрасао.",
                "Долить лимонадом.",
                "Украсить долькой лимона."
            )
        ),
        Cocktail(
            id = 24,
            name = "Бамбл",
            description = "Освежающий коктейль с джином и мёдом.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR, FlavorType.REFRESHING),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 18,
            ingredients = listOf("50 мл джина", "25 мл лимонного сока", "15 мл медового сиропа", "Лёд"),
            instructions = listOf(
                "Смешать все ингредиенты в шейкере со льдом.",
                "Хорошо взболтать.",
                "Процедить в охлаждённый бокал.",
                "Украсить долькой лимона."
            )
        ),
        Cocktail(
            id = 25,
            name = "Клевер Клаб",
            description = "Фруктовый коктейль с джином и малиной.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 20,
            ingredients = listOf("50 мл джина", "20 мл лимонного сока", "15 мл малинового сиропа", "Белок яйца", "Лёд"),
            instructions = listOf(
                "Взболтать все ингредиенты в шейкере без льда (dry shake).",
                "Добавить лёд и взболтать снова.",
                "Процедить в бокал.",
                "Украсить несколькими малинами."
            )
        ),
        Cocktail(
            id = 26,
            name = "Анхо Рейес Сауэр",
            description = "Пряный коктейль с текилой и острым перцем.",
            alcoholType = AlcoholType.TEQUILA,
            flavors = listOf(FlavorType.SOUR, FlavorType.STRONG, FlavorType.BITTER),
            difficulty = Difficulty.HARD,
            strengthPercent = 25,
            ingredients = listOf("40 мл текилы бланко", "20 мл Анхо Рейес (перцовый ликёр)", "25 мл лимонного сока", "10 мл сахарного сиропа", "Несколько капель биттера", "Лёд"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Взболтать.",
                "Процедить в стакан.",
                "Украсить кружочком острого перца."
            )
        ),
        Cocktail(
            id = 27,
            name = "Авиация",
            description = "Цветочный коктейль с джином и фиалковым ликёром.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR),
            difficulty = Difficulty.HARD,
            strengthPercent = 22,
            ingredients = listOf("50 мл джина", "15 мл мараскина", "15 мл фиалкового ликёра (Crème de Violette)", "25 мл лимонного сока", "Лёд"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Взболтать.",
                "Процедить в охлаждённый бокал.",
                "Украсить вишней мараскино."
            )
        ),
        Cocktail(
            id = 28,
            name = "Пэйпал Плэйн",
            description = "Простой и приятный коктейль с текилой.",
            alcoholType = AlcoholType.TEQUILA,
            flavors = listOf(FlavorType.SOUR, FlavorType.BITTER),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 20,
            ingredients = listOf("30 мл текилы бланко", "30 мл Апероля", "30 мл лимончелло", "30 мл лимонного сока", "Лёд"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Взболтать.",
                "Процедить в охлаждённый бокал.",
                "Украсить цедрой лимона."
            )
        ),
        Cocktail(
            id = 29,
            name = "Тёмный и бурный",
            description = "Простой тёмный коктейль с ромом и имбирём.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.STRONG, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 15,
            ingredients = listOf("60 мл тёмного рома", "150 мл имбирного пива", "Сок половины лайма", "Лёд"),
            instructions = listOf(
                "Наполнить высокий стакан льдом.",
                "Добавить ром и сок лайма.",
                "Долить имбирным пивом.",
                "Осторожно перемешать."
            )
        ),
        Cocktail(
            id = 30,
            name = "Беллини",
            description = "Итальянский коктейль с персиком и просекко.",
            alcoholType = AlcoholType.WINE,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 8,
            ingredients = listOf("100 мл просекко", "50 мл персикового пюре"),
            instructions = listOf(
                "Охладить бокал для шампанского.",
                "Добавить персиковое пюре.",
                "Осторожно влить просекко.",
                "Слегка перемешать."
            )
        ),
        Cocktail(
            id = 31,
            name = "Смоки Мартини",
            description = "Мартини с дымным оттенком от шотландского виски.",
            alcoholType = AlcoholType.WHISKEY,
            flavors = listOf(FlavorType.STRONG, FlavorType.BITTER),
            difficulty = Difficulty.HARD,
            strengthPercent = 38,
            ingredients = listOf("60 мл джина", "10 мл Айлейского виски", "10 мл сухого вермута", "Лёд", "Лимонная цедра"),
            instructions = listOf(
                "Протереть бокал виски и вылить.",
                "Смешать джин и вермут в миксинг-гласе со льдом.",
                "Процедить в бокал.",
                "Украсить цедрой лимона."
            )
        ),
        Cocktail(
            id = 32,
            name = "Сингапурский слинг",
            description = "Фруктовый коктейль с джином родом из Сингапура.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR, FlavorType.REFRESHING),
            difficulty = Difficulty.HARD,
            strengthPercent = 15,
            ingredients = listOf("30 мл джина", "15 мл вишнёвого ликёра", "7 мл Cointreau", "7 мл DOM Benedictine", "120 мл ананасового сока", "15 мл сока лайма", "10 мл гренадина", "Биттер Ангостура"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Взболтать.",
                "Процедить в высокий стакан со льдом.",
                "Украсить ананасом и вишней."
            )
        ),
        Cocktail(
            id = 33,
            name = "Лимонад",
            description = "Освежающий безалкогольный лимонный напиток.",
            alcoholType = AlcoholType.NON_ALCOHOLIC,
            flavors = listOf(FlavorType.SOUR, FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 0,
            ingredients = listOf("Сок 3 лимонов", "2 ст.л. сахара", "500 мл холодной воды", "Листья мяты", "Лёд"),
            instructions = listOf(
                "Растворить сахар в небольшом количестве воды.",
                "Смешать с лимонным соком и оставшейся водой.",
                "Подавать со льдом и мятой."
            )
        ),
        Cocktail(
            id = 34,
            name = "Мятный Мокко",
            description = "Безалкогольный кофейно-мятный напиток.",
            alcoholType = AlcoholType.NON_ALCOHOLIC,
            flavors = listOf(FlavorType.SWEET, FlavorType.BITTER, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 0,
            ingredients = listOf("200 мл молока", "30 мл кофе эспрессо", "15 мл мятного сиропа", "Лёд", "Взбитые сливки"),
            instructions = listOf(
                "Смешать эспрессо с мятным сиропом.",
                "Добавить молоко и лёд.",
                "Взболтать или перемешать.",
                "Украсить взбитыми сливками."
            )
        ),
        Cocktail(
            id = 35,
            name = "Клубничный Мохито (безалк.)",
            description = "Безалкогольная версия классического Мохито с клубникой.",
            alcoholType = AlcoholType.NON_ALCOHOLIC,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 0,
            ingredients = listOf("6 клубник", "Листья мяты", "Сок 1 лайма", "2 ч.л. сахара", "Содовая вода", "Лёд"),
            instructions = listOf(
                "Помять клубнику с сахаром и мятой.",
                "Добавить сок лайма и лёд.",
                "Долить содовой водой.",
                "Украсить мятой и клубникой."
            )
        ),
        Cocktail(
            id = 36,
            name = "Бурбонный Смэш",
            description = "Освежающий коктейль с бурбоном и мятой.",
            alcoholType = AlcoholType.WHISKEY,
            flavors = listOf(FlavorType.REFRESHING, FlavorType.SWEET, FlavorType.STRONG),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 25,
            ingredients = listOf("60 мл бурбона", "30 мл лимонного сока", "15 мл сахарного сиропа", "8 листьев мяты", "Лёд"),
            instructions = listOf(
                "Мяту слегка помять в шейкере.",
                "Добавить остальные ингредиенты и лёд.",
                "Взболтать.",
                "Процедить в стакан со льдом.",
                "Украсить веточкой мяты."
            )
        ),
        Cocktail(
            id = 37,
            name = "Маи Таи",
            description = "Тики-коктейль с ромом и апельсиновым ликёром.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.SWEET, FlavorType.SOUR, FlavorType.REFRESHING),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 20,
            ingredients = listOf("30 мл белого рома", "30 мл тёмного рома", "20 мл апельсинового ликёра", "15 мл оргеата", "30 мл сока лайма", "Лёд"),
            instructions = listOf(
                "Смешать все ингредиенты в шейкере со льдом.",
                "Взболтать.",
                "Процедить в стакан с дроблёным льдом.",
                "Украсить мятой и фруктами."
            )
        ),
        Cocktail(
            id = 38,
            name = "Зомби",
            description = "Мощный тики-коктейль с тремя видами рома.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.STRONG, FlavorType.SWEET, FlavorType.SOUR),
            difficulty = Difficulty.HARD,
            strengthPercent = 28,
            ingredients = listOf("30 мл белого рома", "30 мл золотого рома", "30 мл тёмного рома", "15 мл абрикосового бренди", "60 мл ананасового сока", "30 мл сока грейпфрута", "15 мл сока лайма", "10 мл гренадина", "Лёд"),
            instructions = listOf(
                "Все ингредиенты смешать в шейкере со льдом.",
                "Взболтать.",
                "Перелить в высокий стакан.",
                "Украсить ломтиками фруктов."
            )
        ),
        Cocktail(
            id = 39,
            name = "Кайпиринья",
            description = "Национальный коктейль Бразилии с кашасой.",
            alcoholType = AlcoholType.RUM,
            flavors = listOf(FlavorType.SOUR, FlavorType.SWEET, FlavorType.STRONG),
            difficulty = Difficulty.EASY,
            strengthPercent = 20,
            ingredients = listOf("50 мл кашасы", "1 лайм", "2 ч.л. сахара", "Дроблёный лёд"),
            instructions = listOf(
                "Лайм нарезать кубиками и помять с сахаром в стакане.",
                "Добавить дроблёный лёд.",
                "Влить кашасу и перемешать."
            )
        ),
        Cocktail(
            id = 40,
            name = "Томатный Сок Мери",
            description = "Пряный и острый коктейль с водкой и томатным соком.",
            alcoholType = AlcoholType.VODKA,
            flavors = listOf(FlavorType.STRONG, FlavorType.BITTER, FlavorType.SOUR),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 12,
            ingredients = listOf("50 мл водки", "100 мл томатного сока", "15 мл сока лимона", "2-3 капли соуса Табаско", "5 мл соуса Вустершир", "Соль, перец", "Лёд", "Сельдерей, маслина"),
            instructions = listOf(
                "Смешать водку и томатный сок.",
                "Добавить сок лимона, табаско и вустершир.",
                "Приправить солью и перцем.",
                "Перелить в стакан со льдом.",
                "Украсить стеблем сельдерея и маслиной."
            )
        ),
        Cocktail(
            id = 41,
            name = "Кофейный Негрони",
            description = "Современная версия Негрони с кофейным биттером.",
            alcoholType = AlcoholType.GIN,
            flavors = listOf(FlavorType.BITTER, FlavorType.STRONG),
            difficulty = Difficulty.MEDIUM,
            strengthPercent = 28,
            ingredients = listOf("30 мл джина", "30 мл кампари", "30 мл сладкого вермута", "5 мл кофейного биттера", "Лёд", "Цедра апельсина"),
            instructions = listOf(
                "Смешать все ингредиенты в стакане со льдом.",
                "Перемешать барной ложкой.",
                "Украсить цедрой апельсина."
            )
        ),
        Cocktail(
            id = 42,
            name = "Персиковый Беллини Санрайз",
            description = "Красочный безалкогольный напиток с персиком.",
            alcoholType = AlcoholType.NON_ALCOHOLIC,
            flavors = listOf(FlavorType.SWEET, FlavorType.REFRESHING),
            difficulty = Difficulty.EASY,
            strengthPercent = 0,
            ingredients = listOf("100 мл персикового нектара", "100 мл апельсинового сока", "50 мл гранатового сока", "Лёд"),
            instructions = listOf(
                "Наполнить бокал льдом.",
                "Влить смесь персикового и апельсинового сока.",
                "Медленно добавить гранатовый сок.",
                "Не перемешивать — он осядет на дно, создавая эффект заката."
            )
        )
    )
}
