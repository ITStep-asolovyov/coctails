import Foundation

// MARK: - API Service

final class CocktailAPI {
    static let shared = CocktailAPI()
    private init() {}

    private let base = "https://www.thecocktaildb.com/api/json/v1/1/"
    private let session: URLSession = {
        let cfg = URLSessionConfiguration.default
        cfg.timeoutIntervalForRequest = 15
        return URLSession(configuration: cfg)
    }()

    // MARK: Generic fetch
    private func fetch<T: Decodable>(_ endpoint: String, params: [String: String]) async throws -> T {
        var comps = URLComponents(string: base + endpoint)!
        comps.queryItems = params.map { URLQueryItem(name: $0.key, value: $0.value) }
        let (data, _) = try await session.data(from: comps.url!)
        return try JSONDecoder().decode(T.self, from: data)
    }

    // MARK: - Endpoints

    func searchByName(_ name: String) async throws -> [CocktailListItem] {
        let r: CocktailResponse = try await fetch("search.php", params: ["s": name])
        return r.drinks?.map { $0.toListItem() } ?? []
    }

    func getById(_ id: String) async throws -> Cocktail {
        let r: CocktailResponse = try await fetch("lookup.php", params: ["i": id])
        guard let dto = r.drinks?.first else { throw CocktailError.notFound }
        return dto.toCocktail()
    }

    func getRandom() async throws -> CocktailListItem {
        let r: CocktailResponse = try await fetch("random.php", params: [:])
        guard let dto = r.drinks?.first else { throw CocktailError.notFound }
        return dto.toListItem()
    }

    func filterByIngredient(_ ingredient: String) async throws -> [CocktailListItem] {
        let r: FilterResponse = try await fetch("filter.php", params: ["i": ingredient])
        return r.drinks?.map { $0.toListItem() } ?? []
    }

    func filterByAlcohol(_ alcoholic: Bool) async throws -> [CocktailListItem] {
        let r: FilterResponse = try await fetch(
            "filter.php", params: ["a": alcoholic ? "Alcoholic" : "Non_Alcoholic"]
        )
        return r.drinks?.map { $0.toListItem() } ?? []
    }

    func filterByCategory(_ category: String) async throws -> [CocktailListItem] {
        let r: FilterResponse = try await fetch("filter.php", params: ["c": category])
        return r.drinks?.map { $0.toListItem() } ?? []
    }

    func getCategories() async throws -> [String] {
        let r: CategoryResponse = try await fetch("list.php", params: ["c": "list"])
        return r.drinks?.map(\.strCategory) ?? []
    }

    /// Loads `count` random cocktails in parallel and deduplicates by ID.
    func getPopular(count: Int = 8) async throws -> [CocktailListItem] {
        try await withThrowingTaskGroup(of: CocktailListItem?.self) { group in
            for _ in 0..<count {
                group.addTask { try? await self.getRandom() }
            }
            var seen  = Set<String>()
            var items = [CocktailListItem]()
            for try await item in group {
                guard let item, seen.insert(item.id).inserted else { continue }
                items.append(item)
            }
            return items
        }
    }
}

// MARK: - Errors
enum CocktailError: Error {
    case notFound
    case networkError(String)
}
