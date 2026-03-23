import SwiftUI

struct SearchView: View {
    @StateObject private var vm = SearchViewModel()

    var body: some View {
        NavigationStack {
            ZStack {
                Color.bgPrimary.ignoresSafeArea()

                VStack(spacing: 0) {
                    // Header
                    VStack(alignment: .leading, spacing: 4) {
                        Text("Поиск")
                            .font(.system(size: 32, weight: .bold, design: .serif))
                            .foregroundColor(.textPrimary)
                        Text("Найдите любой коктейль")
                            .font(.system(size: 14))
                            .foregroundColor(.textSecondary)

                        Spacer().frame(height: 14)

                        // Search field
                        HStack {
                            Image(systemName: "magnifyingglass")
                                .foregroundColor(.goldAccent)
                            TextField("", text: $vm.query)
                                .placeholder(when: vm.query.isEmpty) {
                                    Text("Введите название...").foregroundColor(.textHint)
                                }
                                .foregroundColor(.textPrimary)
                                .tint(.goldAccent)
                                .autocorrectionDisabled()
                        }
                        .padding(.horizontal, 16).padding(.vertical, 14)
                        .background(Color.bgCard)
                        .clipShape(RoundedRectangle(cornerRadius: 16))
                        .overlay(RoundedRectangle(cornerRadius: 16)
                            .stroke(vm.query.isEmpty ? Color.glassBorder : Color.goldAccent.opacity(0.6),
                                    lineWidth: 1))
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 20)
                    .padding(.bottom, 12)

                    // Content
                    contentArea
                }
            }
            .navigationBarHidden(true)
            .navigationDestination(for: String.self) { id in
                DetailView(cocktailId: id)
            }
        }
    }

    @ViewBuilder
    private var contentArea: some View {
        if vm.isLoading {
            ScrollView {
                VStack(spacing: 16) {
                    ForEach(0..<5, id: \.self) { _ in ShimmerCard() }
                }.padding(.horizontal, 20).padding(.top, 8)
            }
        } else if let err = vm.error {
            errorView(err)
        } else if vm.hasSearched && vm.results.isEmpty {
            emptyView
        } else if !vm.hasSearched {
            promptView
        } else {
            ScrollView(showsIndicators: false) {
                VStack(spacing: 16) {
                    ForEach(Array(vm.results.enumerated()), id: \.element.id) { index, item in
                        NavigationLink(value: item.id) {
                            CocktailCard(item: item, index: index)
                        }
                        .buttonStyle(.plain)
                    }
                }.padding(.horizontal, 20).padding(.top, 8)
            }
        }
    }

    private func errorView(_ msg: String) -> some View {
        VStack(spacing: 12) {
            Text("⚠️").font(.system(size: 48))
            Text(msg).foregroundColor(.textSecondary).multilineTextAlignment(.center)
        }
        .padding(32)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private var emptyView: some View {
        VStack(spacing: 12) {
            Image(systemName: "magnifyingglass").font(.system(size: 64)).foregroundColor(.textHint)
            Text("Ничего не найдено").font(.headline).foregroundColor(.textSecondary)
            Text("Попробуйте другой запрос").foregroundColor(.textHint)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private var promptView: some View {
        VStack(spacing: 8) {
            Text("🍹").font(.system(size: 56))
            Text("Начните вводить название")
                .foregroundColor(.textHint)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

// MARK: - TextField placeholder helper
extension View {
    func placeholder<Content: View>(
        when show: Bool,
        @ViewBuilder placeholder: () -> Content
    ) -> some View {
        ZStack(alignment: .leading) {
            if show { placeholder() }
            self
        }
    }
}
