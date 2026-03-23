import Foundation

enum DetailState {
    case loading
    case success(cocktail: Cocktail, isFavorite: Bool)
    case error(String)
}

@MainActor
final class DetailViewModel: ObservableObject {
    @Published var state: DetailState = .loading

    private let api         = CocktailAPI.shared
    private let persistence = PersistenceController.shared
    private var cocktailId  = ""

    func load(id: String) async {
        cocktailId = id
        state = .loading
        do {
            let cocktail = try await api.getById(id)
            state = .success(cocktail: cocktail, isFavorite: persistence.isFavorite(id: id))
        } catch {
            state = .error("Не удалось загрузить коктейль. Проверьте соединение.")
        }
    }

    func toggleFavorite() {
        guard case .success(let cocktail, let isFav) = state else { return }
        let item = CocktailListItem(id: cocktail.id, name: cocktail.name, imageUrl: cocktail.imageUrl)
        if isFav {
            persistence.removeFavorite(id: cocktail.id)
        } else {
            persistence.addFavorite(item)
        }
        state = .success(cocktail: cocktail, isFavorite: !isFav)
    }
}
