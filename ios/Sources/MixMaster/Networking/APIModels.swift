import Foundation

// MARK: - Codable DTOs

struct CocktailResponse: Codable {
    let drinks: [DrinkDTO]?
}

struct FilterResponse: Codable {
    let drinks: [FilterDrinkDTO]?
}

struct CategoryResponse: Codable {
    let drinks: [CategoryDTO]?
}

struct DrinkDTO: Codable {
    let idDrink: String
    let strDrink: String
    let strDrinkThumb: String?
    let strCategory: String?
    let strAlcoholic: String?
    let strGlass: String?
    let strInstructions: String?
    let strInstructionsRU: String?
    // Ingredients 1–15
    let strIngredient1: String?;  let strIngredient2: String?
    let strIngredient3: String?;  let strIngredient4: String?
    let strIngredient5: String?;  let strIngredient6: String?
    let strIngredient7: String?;  let strIngredient8: String?
    let strIngredient9: String?;  let strIngredient10: String?
    let strIngredient11: String?; let strIngredient12: String?
    let strIngredient13: String?; let strIngredient14: String?
    let strIngredient15: String?
    // Measures 1–15
    let strMeasure1: String?;  let strMeasure2: String?
    let strMeasure3: String?;  let strMeasure4: String?
    let strMeasure5: String?;  let strMeasure6: String?
    let strMeasure7: String?;  let strMeasure8: String?
    let strMeasure9: String?;  let strMeasure10: String?
    let strMeasure11: String?; let strMeasure12: String?
    let strMeasure13: String?; let strMeasure14: String?
    let strMeasure15: String?

    // Collect non-empty (name, measure) pairs
    var ingredientPairs: [(String, String)] {
        let names: [String?] = [
            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
            strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
            strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15
        ]
        let measures: [String?] = [
            strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
            strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
            strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15
        ]
        return zip(names, measures).compactMap { name, measure in
            guard let name, !name.trimmingCharacters(in: .whitespaces).isEmpty else { return nil }
            return (name.trimmingCharacters(in: .whitespaces),
                    measure?.trimmingCharacters(in: .whitespaces) ?? "")
        }
    }

    func toCocktail() -> Cocktail {
        let alcoholicRaw = strAlcoholic ?? ""
        let isAlcoholic  = !alcoholicRaw.lowercased().contains("non") &&
                            alcoholicRaw.lowercased().contains("alcoholic")
        return Cocktail(
            id:             idDrink,
            name:           strDrink,
            imageUrl:       strDrinkThumb.flatMap(URL.init),
            isAlcoholic:    isAlcoholic,
            alcoholicLabel: alcoholicRaw.translateAlcoholic(),
            category:       (strCategory ?? "").translateCategory(),
            glass:          (strGlass ?? "").translateGlass(),
            instructions:   strInstructionsRU?.nilIfBlank ?? strInstructions?.nilIfBlank ?? "",
            ingredients:    ingredientPairs.map { name, measure in
                Ingredient(name: name,
                           displayName: name.translateIngredient(),
                           measure: measure)
            }
        )
    }

    func toListItem() -> CocktailListItem {
        CocktailListItem(id: idDrink, name: strDrink, imageUrl: strDrinkThumb.flatMap(URL.init))
    }
}

struct FilterDrinkDTO: Codable {
    let idDrink: String
    let strDrink: String
    let strDrinkThumb: String?

    func toListItem() -> CocktailListItem {
        CocktailListItem(id: idDrink, name: strDrink, imageUrl: strDrinkThumb.flatMap(URL.init))
    }
}

struct CategoryDTO: Codable {
    let strCategory: String
}

// MARK: - Helpers
private extension String {
    var nilIfBlank: String? { trimmingCharacters(in: .whitespacesAndNewlines).isEmpty ? nil : self }
}
