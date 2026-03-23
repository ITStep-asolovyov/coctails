package com.mixmaster.app.data.model

import com.mixmaster.app.data.api.DrinkDto
import com.mixmaster.app.data.api.FilterDrinkDto

data class Cocktail(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val isAlcoholic: Boolean,
    val alcoholicLabel: String,
    val category: String,
    val glass: String,
    val instructions: String,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,         // original English name — used for image URL
    val displayName: String,  // translated name shown in UI
    val measure: String
) {
    val imageUrl: String
        get() = "https://www.thecocktaildb.com/images/ingredients/${name.replace(" ", "%20")}-Small.png"
}

data class CocktailListItem(
    val id: String,
    val name: String,
    val imageUrl: String?
)

fun DrinkDto.toCocktail(): Cocktail = Cocktail(
    id = id,
    name = name,
    imageUrl = thumb,
    isAlcoholic = alcoholic?.lowercase()?.contains("non") == false &&
            alcoholic.lowercase().contains("alcoholic"),
    alcoholicLabel = (alcoholic ?: "").translateAlcoholic(),
    category = (category ?: "").translateCategory(),
    glass = (glass ?: "").translateGlass(),
    instructions = instructionsRu?.takeIf { it.isNotBlank() } ?: (instructions ?: ""),
    ingredients = getIngredients().map { (n, m) -> Ingredient(n, n.translateIngredient(), m) }
)

fun DrinkDto.toListItem(): CocktailListItem = CocktailListItem(
    id = id,
    name = name,
    imageUrl = thumb
)

fun FilterDrinkDto.toListItem(): CocktailListItem = CocktailListItem(
    id = id,
    name = name,
    imageUrl = thumb
)
