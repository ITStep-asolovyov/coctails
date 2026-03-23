import SwiftUI

struct FavoritesView: View {
    @StateObject private var vm = FavoritesViewModel()

    private let columns = [GridItem(.flexible(), spacing: 12), GridItem(.flexible(), spacing: 12)]

    var body: some View {
        NavigationStack {
            ZStack {
                Color.bgPrimary.ignoresSafeArea()

                VStack(alignment: .leading, spacing: 0) {
                    // Header
                    VStack(alignment: .leading, spacing: 4) {
                        Text("Избранное")
                            .font(.system(size: 32, weight: .bold, design: .serif))
                            .foregroundColor(.textPrimary)
                        Text(vm.favorites.isEmpty ? "Нет сохранённых коктейлей"
                             : "\(vm.favorites.count) коктейлей")
                            .font(.system(size: 14))
                            .foregroundColor(.textSecondary)
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 20)
                    .padding(.bottom, 16)

                    if vm.favorites.isEmpty {
                        emptyState
                    } else {
                        ScrollView(showsIndicators: false) {
                            LazyVGrid(columns: columns, spacing: 12) {
                                ForEach(Array(vm.favorites.enumerated()), id: \.element.id) { index, item in
                                    NavigationLink(value: item.id) {
                                        FavoriteGridCell(item: item, index: index) {
                                            vm.remove(id: item.id)
                                        }
                                    }
                                    .buttonStyle(.plain)
                                }
                            }
                            .padding(.horizontal, 16)
                            .padding(.bottom, 24)
                        }
                    }
                }
            }
            .navigationBarHidden(true)
            .navigationDestination(for: String.self) { id in
                DetailView(cocktailId: id)
            }
        }
    }

    private var emptyState: some View {
        VStack(spacing: 14) {
            Image(systemName: "heart")
                .font(.system(size: 72))
                .foregroundColor(.textHint)
            Text("Нет избранного")
                .font(.headline)
                .foregroundColor(.textSecondary)
            Text("Добавляйте коктейли,\nнажимая ♡ на экране деталей")
                .font(.system(size: 14))
                .foregroundColor(.textHint)
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

// MARK: - Grid cell with remove button
private struct FavoriteGridCell: View {
    let item: CocktailListItem
    let index: Int
    let onRemove: () -> Void

    var body: some View {
        ZStack(alignment: .top) {
            CocktailCard(item: item, index: index)

            HStack {
                // Heart badge
                Image(systemName: "heart.fill")
                    .font(.system(size: 12))
                    .foregroundColor(.white)
                    .frame(width: 26, height: 26)
                    .background(Color.favActive.opacity(0.85))
                    .clipShape(Circle())

                Spacer()

                // Remove button
                Button(action: onRemove) {
                    Image(systemName: "xmark")
                        .font(.system(size: 11, weight: .bold))
                        .foregroundColor(.white)
                        .frame(width: 26, height: 26)
                        .background(Color.black.opacity(0.6))
                        .clipShape(Circle())
                }
            }
            .padding(8)
        }
    }
}
