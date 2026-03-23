import Foundation

// MARK: - Domain models

struct Cocktail: Identifiable {
    let id: String
    let name: String
    let imageUrl: URL?
    let isAlcoholic: Bool
    let alcoholicLabel: String
    let category: String
    let glass: String
    let instructions: String
    let ingredients: [Ingredient]
}

struct Ingredient: Identifiable {
    var id: String { name }
    let name: String          // English — used for the image URL
    let displayName: String   // Russian — shown in UI
    let measure: String

    var imageUrl: URL? {
        let encoded = name
            .addingPercentEncoding(withAllowedCharacters: .urlPathAllowed) ?? name
        return URL(string:
            "https://www.thecocktaildb.com/images/ingredients/\(encoded)-Small.png"
        )
    }
}

struct CocktailListItem: Identifiable, Hashable {
    let id: String
    let name: String
    let imageUrl: URL?
}
