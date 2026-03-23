package com.mixmaster.app.data.model

import com.mixmaster.app.data.api.ApmixCocktailDto

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
    val name: String,        // display name shown in UI (may be Russian)
    val displayName: String, // same, kept for consistency
    val measure: String,
    val imageUrl: String?    // nullable — derived from reverse English lookup
)

data class CocktailListItem(
    val id: String,
    val name: String,
    val imageUrl: String?
)

fun ApmixCocktailDto.toCocktail(imageUrl: String? = null): Cocktail {
    val typeStr = type?.lowercase() ?: ""
    val isAlcoholic = when {
        typeStr.contains("безалкогол") -> false
        typeStr.contains("алкогол") -> true
        else -> baseType != null && baseType.isNotBlank()
    }
    return Cocktail(
        id = id,
        name = name ?: "Без названия",
        imageUrl = imageUrl,
        isAlcoholic = isAlcoholic,
        alcoholicLabel = type ?: if (isAlcoholic) "Алкогольный" else "Безалкогольный",
        category = typeDrinks ?: "",
        glass = typeCap ?: "",
        instructions = build ?: "",
        ingredients = ingredients?.map { it.parseIngredient() } ?: emptyList()
    )
}

fun ApmixCocktailDto.toListItem(imageUrl: String? = null): CocktailListItem =
    CocktailListItem(id = id, name = name ?: "Без названия", imageUrl = imageUrl)

/**
 * Parses an ingredient string that may contain a measure.
 * Supported formats: "Водка - 50 мл", "Водка (50 мл)", "Водка"
 */
private fun String.parseIngredient(): Ingredient {
    val separatorIdx = indexOf(" - ")
    val (ingredientName, measure) = when {
        separatorIdx != -1 ->
            substring(0, separatorIdx).trim() to substring(separatorIdx + 3).trim()
        contains(" (") && endsWith(")") -> {
            val parenStart = lastIndexOf(" (")
            substring(0, parenStart).trim() to substring(parenStart + 2, length - 1).trim()
        }
        else -> trim() to ""
    }

    // Attempt reverse-lookup: Russian display name → English key → image URL
    val englishKey = ingredientTranslations.entries
        .firstOrNull { it.value.equals(ingredientName, ignoreCase = true) }
        ?.key
    val imgUrl = englishKey?.let {
        "https://www.thecocktaildb.com/images/ingredients/${it.replace(" ", "%20")}-Small.png"
    }

    return Ingredient(
        name = ingredientName,
        displayName = ingredientName,
        measure = measure,
        imageUrl = imgUrl
    )
}
