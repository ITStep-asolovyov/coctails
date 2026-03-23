import SwiftUI

struct HomeView: View {
    @StateObject private var vm = HomeViewModel()
    @State private var navigateTo: String? = nil

    var body: some View {
        NavigationStack {
            ZStack {
                Color.bgPrimary.ignoresSafeArea()

                ScrollView(showsIndicators: false) {
                    VStack(spacing: 0) {
                        heroSection
                        Spacer().frame(height: 28)
                        alcoholSection
                        Spacer().frame(height: 28)
                        categorySection
                        Spacer().frame(height: 28)
                        Divider().overlay(Color.dividerGold).padding(.horizontal, 20)
                        Spacer().frame(height: 28)
                        popularSection
                        Spacer().frame(height: 32)
                    }
                }
            }
            .navigationBarHidden(true)
            // Navigate to detail when random cocktail loaded
            .navigationDestination(for: String.self) { id in
                DetailView(cocktailId: id)
            }
            .onChange(of: vm.randomCocktailId) { id in
                guard let id else { return }
                navigateTo = id
                vm.clearRandomId()
            }
        }
        .alert("Ошибка", isPresented: Binding(
            get: { vm.error != nil },
            set: { if !$0 { vm.clearError() } }
        )) {
            Button("OK") { vm.clearError() }
        } message: {
            Text(vm.error ?? "")
        }
    }

    // MARK: - Hero
    private var heroSection: some View {
        ZStack(alignment: .bottomLeading) {
            LinearGradient.heroGradient

            // Glow orbs
            glowOrbs

            VStack(alignment: .leading, spacing: 8) {
                Text("🍸").font(.system(size: 52))
                Text("МиксМастер")
                    .font(.system(size: 32, weight: .bold, design: .serif))
                    .foregroundColor(.textPrimary)
                Text("Найди свой идеальный коктейль")
                    .font(.system(size: 16))
                    .foregroundColor(.textSecondary)
                Spacer().frame(height: 16)

                // Pulsing gold button
                PulsingButton(isLoading: vm.isLoadingRandom) {
                    Task { await vm.getRandomCocktail() }
                }
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 28)
            .padding(.top, 60)
        }
        .frame(maxWidth: .infinity)
        .frame(minHeight: 310)
    }

    private var glowOrbs: some View {
        ZStack {
            Circle()
                .fill(RadialGradient(
                    colors: [Color.goldAccent.opacity(0.10), .clear],
                    center: .center, startRadius: 0, endRadius: 130
                ))
                .frame(width: 260, height: 260)
                .offset(x: 80, y: -60)
            Circle()
                .fill(RadialGradient(
                    colors: [Color(hex: 0x7C3ADB).opacity(0.06), .clear],
                    center: .center, startRadius: 0, endRadius: 90
                ))
                .frame(width: 180, height: 180)
                .offset(x: -100, y: 60)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topTrailing)
    }

    // MARK: - Alcohol filter
    private var alcoholSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            sectionLabel("ТИП НАПИТКА")
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    ForEach(AlcoholTypeFilter.allCases) { filter in
                        AlcoholCircleChip(
                            filter: filter,
                            isSelected: vm.selectedAlcohol == filter && vm.selectedCategory == nil
                        ) { vm.selectAlcohol(filter) }
                    }
                }
                .padding(.horizontal, 20)
            }
        }
    }

    // MARK: - Category chips
    private var categorySection: some View {
        VStack(alignment: .leading, spacing: 12) {
            sectionLabel("КАТЕГОРИИ").padding(.horizontal, 20)
            if vm.isLoadingCategories {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 10) {
                        ForEach(0..<6, id: \.self) { _ in
                            RoundedRectangle(cornerRadius: 20)
                                .fill(Color.bgCard)
                                .frame(width: 90, height: 34)
                        }
                    }.padding(.horizontal, 20)
                }
            } else {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 10) {
                        ForEach(vm.categories, id: \.self) { cat in
                            CategoryChip(
                                text: cat,
                                isSelected: vm.selectedCategory == cat
                            ) {
                                vm.selectCategory(vm.selectedCategory == cat ? nil : cat)
                            }
                        }
                    }.padding(.horizontal, 20)
                }
            }
        }
    }

    // MARK: - Popular
    private var popularSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            sectionLabel("ПОПУЛЯРНЫЕ").padding(.horizontal, 20)

            if vm.isLoadingPopular {
                VStack(spacing: 16) {
                    ForEach(0..<3, id: \.self) { _ in ShimmerCard() }
                }.padding(.horizontal, 20)
            } else if vm.popularCocktails.isEmpty {
                Text("Нет коктейлей")
                    .foregroundColor(.textHint)
                    .frame(maxWidth: .infinity)
                    .frame(height: 100)
            } else {
                VStack(spacing: 16) {
                    ForEach(Array(vm.popularCocktails.enumerated()), id: \.element.id) { index, item in
                        NavigationLink(value: item.id) {
                            CocktailCard(item: item, index: index)
                        }
                        .buttonStyle(.plain)
                    }
                }.padding(.horizontal, 20)
            }
        }
    }

    private func sectionLabel(_ text: String) -> some View {
        Text(text)
            .font(.system(size: 11, weight: .bold))
            .tracking(2)
            .foregroundColor(.goldAccent)
    }
}

// MARK: - Pulsing Button
private struct PulsingButton: View {
    let isLoading: Bool
    let action: () -> Void

    @State private var pulsing = false

    var body: some View {
        Button(action: action) {
            ZStack {
                LinearGradient.goldGradient
                HStack(spacing: 8) {
                    if isLoading {
                        ProgressView().tint(.black).scaleEffect(0.8)
                        Text("Загрузка...").fontWeight(.bold).foregroundColor(.textOnGold)
                    } else {
                        Image(systemName: "dice").foregroundColor(.textOnGold)
                        Text("Мне повезёт").fontWeight(.bold).foregroundColor(.textOnGold)
                    }
                }
            }
            .frame(height: 52)
            .clipShape(RoundedRectangle(cornerRadius: 14))
        }
        .disabled(isLoading)
        .scaleEffect((!isLoading && pulsing) ? 1.05 : 1.0)
        .animation(.easeInOut(duration: 0.9).repeatForever(autoreverses: true), value: pulsing)
        .onAppear { pulsing = true }
    }
}

// MARK: - Alcohol Circle Chip
private struct AlcoholCircleChip: View {
    let filter: AlcoholTypeFilter
    let isSelected: Bool
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            VStack(spacing: 6) {
                ZStack {
                    Circle()
                        .fill(isSelected ? Color.glassBgGold : Color.glassBg)
                        .overlay(Circle().stroke(
                            isSelected ? Color.goldAccent : Color.glassBorder, lineWidth: 1.5
                        ))
                    Text(filter.emoji).font(.system(size: 28))
                }
                .frame(width: 64, height: 64)
                Text(filter.displayName)
                    .font(.system(size: 10, weight: isSelected ? .bold : .regular))
                    .foregroundColor(isSelected ? .goldAccent : .textSecondary)
                    .lineLimit(1)
            }
        }
        .buttonStyle(.plain)
    }
}

// MARK: - Category Glass Chip
private struct CategoryChip: View {
    let text: String
    let isSelected: Bool
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            Text(text)
                .font(.system(size: 13, weight: isSelected ? .bold : .regular))
                .foregroundColor(isSelected ? .goldAccent : .textSecondary)
                .padding(.horizontal, 16).padding(.vertical, 8)
                .background(isSelected ? Color.glassBgGold : Color.glassBg)
                .clipShape(Capsule())
                .overlay(Capsule().stroke(
                    isSelected ? Color.glassBorderGold : Color.glassBorder, lineWidth: 1
                ))
        }
        .buttonStyle(.plain)
    }
}
