import Foundation
import Combine

@MainActor
final class SearchViewModel: ObservableObject {
    @Published var query = ""
    @Published var results: [CocktailListItem] = []
    @Published var isLoading = false
    @Published var error: String? = nil
    @Published var hasSearched = false

    private let api = CocktailAPI.shared
    private var cancellables = Set<AnyCancellable>()

    init() {
        // Debounce 500ms — mirrors Android SearchViewModel
        $query
            .debounce(for: .milliseconds(500), scheduler: DispatchQueue.main)
            .removeDuplicates()
            .sink { [weak self] q in
                Task { await self?.performSearch(q) }
            }
            .store(in: &cancellables)
    }

    private func performSearch(_ q: String) async {
        let trimmed = q.trimmingCharacters(in: .whitespaces)
        guard !trimmed.isEmpty else {
            results = []; isLoading = false; hasSearched = false; return
        }
        isLoading = true; error = nil
        do {
            results = try await api.searchByName(trimmed)
            hasSearched = true
        } catch {
            self.error = "Ошибка сети. Проверьте соединение."
            hasSearched = true
        }
        isLoading = false
    }
}
