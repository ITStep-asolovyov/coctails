import Foundation
import Combine

// MARK: - Alcohol filter enum
enum AlcoholTypeFilter: CaseIterable, Identifiable {
    case all, vodka, rum, gin, tequila, whiskey, wine, nonAlcoholic

    var id: Self { self }
    var displayName: String {
        switch self {
        case .all:          return "Все"
        case .vodka:        return "Водка"
        case .rum:          return "Ром"
        case .gin:          return "Джин"
        case .tequila:      return "Текила"
        case .whiskey:      return "Виски"
        case .wine:         return "Вино"
        case .nonAlcoholic: return "Без алкоголя"
        }
    }
    var emoji: String {
        switch self {
        case .all:          return "🍹"
        case .vodka:        return "🥃"
        case .rum:          return "🍺"
        case .gin:          return "🍸"
        case .tequila:      return "🌵"
        case .whiskey:      return "🥃"
        case .wine:         return "🍷"
        case .nonAlcoholic: return "🧃"
        }
    }
    var ingredient: String {
        switch self {
        case .all, .nonAlcoholic: return ""
        case .vodka:    return "Vodka"
        case .rum:      return "Rum"
        case .gin:      return "Gin"
        case .tequila:  return "Tequila"
        case .whiskey:  return "Whiskey"
        case .wine:     return "Wine"
        }
    }
}

// MARK: - ViewModel
@MainActor
final class HomeViewModel: ObservableObject {
    @Published var categories: [String] = []
    @Published var selectedAlcohol: AlcoholTypeFilter = .all
    @Published var selectedCategory: String? = nil
    @Published var popularCocktails: [CocktailListItem] = []
    @Published var isLoadingPopular = true
    @Published var isLoadingCategories = true
    @Published var isLoadingRandom = false
    @Published var randomCocktailId: String? = nil
    @Published var error: String? = nil

    private let api = CocktailAPI.shared

    init() {
        Task { await loadCategories() }
        Task { await loadPopular() }
    }

    func loadCategories() async {
        do {
            categories = try await api.getCategories()
        } catch { /* silent */ }
        isLoadingCategories = false
    }

    func loadPopular() async {
        isLoadingPopular = true
        do {
            let items: [CocktailListItem]
            if let cat = selectedCategory {
                items = try await api.filterByCategory(cat)
            } else if selectedAlcohol == .nonAlcoholic {
                items = try await api.filterByAlcohol(false)
            } else if selectedAlcohol != .all {
                items = try await api.filterByIngredient(selectedAlcohol.ingredient)
            } else {
                items = try await api.getPopular(count: 8)
            }
            popularCocktails = Array(items.prefix(20))
        } catch {
            self.error = "Ошибка загрузки. Проверьте соединение."
        }
        isLoadingPopular = false
    }

    func selectAlcohol(_ filter: AlcoholTypeFilter) {
        guard selectedAlcohol != filter else { return }
        selectedAlcohol = filter
        selectedCategory = nil
        Task { await loadPopular() }
    }

    func selectCategory(_ cat: String?) {
        guard selectedCategory != cat else { return }
        selectedCategory = cat
        selectedAlcohol = .all
        Task { await loadPopular() }
    }

    func getRandomCocktail() async {
        isLoadingRandom = true
        do {
            let item = try await api.getRandom()
            randomCocktailId = item.id
        } catch {
            self.error = "Ошибка сети. Попробуйте снова."
        }
        isLoadingRandom = false
    }

    func clearRandomId() { randomCocktailId = nil }
    func clearError()    { error = nil }
}
