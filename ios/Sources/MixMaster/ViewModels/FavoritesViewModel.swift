import Foundation
import Combine

@MainActor
final class FavoritesViewModel: ObservableObject {
    @Published var favorites: [CocktailListItem] = []

    private let persistence = PersistenceController.shared
    private var cancellable: AnyCancellable?

    init() {
        fetch()
        // Re-fetch whenever Core Data context saves
        cancellable = NotificationCenter.default
            .publisher(for: .NSManagedObjectContextDidSave)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] _ in self?.fetch() }
    }

    func fetch() {
        favorites = persistence.fetchAllFavorites()
    }

    func remove(id: String) {
        persistence.removeFavorite(id: id)
        fetch()
    }
}
