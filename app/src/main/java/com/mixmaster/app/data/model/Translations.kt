package com.mixmaster.app.data.model

val categoryTranslations = mapOf(
    "Ordinary Drink" to "Обычный коктейль",
    "Cocktail" to "Коктейль",
    "Milk / Float / Shake" to "Молочный / Шейк",
    "Other/Unknown" to "Другое",
    "Cocoa" to "Какао",
    "Shot" to "Шот",
    "Coffee / Tea" to "Кофе / Чай",
    "Homemade Liqueur" to "Домашний ликёр",
    "Punch / Party Drink" to "Пунш / Вечеринка",
    "Beer" to "Пиво",
    "Soft Drink" to "Безалкогольный напиток"
)

val glassTranslations = mapOf(
    "Highball glass" to "Хайбол",
    "Cocktail glass" to "Коктейльный бокал",
    "Old-fashioned glass" to "Олд Фэшнд",
    "Whiskey Glass" to "Виски бокал",
    "Collins glass" to "Коллинз",
    "Pousse cafe glass" to "Пусс-кафе",
    "Champagne flute" to "Фужер для шампанского",
    "Whiskey sour glass" to "Бокал для сауэра",
    "Cordial glass" to "Кордиал",
    "Beer mug" to "Пивная кружка",
    "Margarita/Coupette glass" to "Маргарита",
    "Beer pilsner" to "Пильзнер",
    "Beer Glass" to "Пивной бокал",
    "Punch bowl" to "Чаша для пунша",
    "Pitcher" to "Кувшин",
    "Pint glass" to "Пинта",
    "Copper Mug" to "Медная кружка",
    "Wine Glass" to "Бокал для вина",
    "Shot glass" to "Стопка",
    "Jar" to "Кружка",
    "Irish coffee cup" to "Ирландский кофе",
    "Hurricane glass" to "Ураган",
    "Coffee mug" to "Кофейная кружка",
    "Balloon Glass" to "Бокал баллон",
    "Brandy snifter" to "Бокал для бренди",
    "White wine glass" to "Бокал для белого вина",
    "Nick and Nora Glass" to "Ник и Нора",
    "Martini Glass" to "Мартини",
    "Pousse-cafe glass" to "Пусс-кафе",
    "Parfait glass" to "Парфе"
)

val alcoholicTranslations = mapOf(
    "Alcoholic" to "Алкогольный",
    "Non alcoholic" to "Безалкогольный",
    "Non Alcoholic" to "Безалкогольный",
    "Optional alcohol" to "По желанию"
)

fun String.translateCategory() = categoryTranslations[this] ?: this
fun String.translateGlass() = glassTranslations[this] ?: this
fun String.translateAlcoholic() = alcoholicTranslations[this] ?: this
