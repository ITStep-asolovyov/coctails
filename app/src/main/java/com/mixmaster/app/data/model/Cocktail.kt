package com.mixmaster.app.data.model

data class Cocktail(
    val id: Int,
    val name: String,
    val description: String,
    val alcoholType: AlcoholType,
    val flavors: List<FlavorType>,
    val difficulty: Difficulty,
    val strengthPercent: Int,
    val ingredients: List<String>,
    val instructions: List<String>
)

enum class AlcoholType(val displayName: String) {
    VODKA("Водка"),
    RUM("Ром"),
    GIN("Джин"),
    TEQUILA("Текила"),
    WHISKEY("Виски"),
    WINE("Вино"),
    NON_ALCOHOLIC("Безалкогольный")
}

enum class FlavorType(val displayName: String) {
    SWEET("Сладкий"),
    SOUR("Кислый"),
    BITTER("Горький"),
    NEUTRAL("Нейтральный"),
    REFRESHING("Освежающий"),
    STRONG("Крепкий")
}

enum class Difficulty(val displayName: String) {
    EASY("Легко"),
    MEDIUM("Средне"),
    HARD("Сложно")
}
