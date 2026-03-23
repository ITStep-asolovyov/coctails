import SwiftUI

struct DetailView: View {
    let cocktailId: String
    @StateObject private var vm = DetailViewModel()
    @Environment(\.dismiss) private var dismiss

    var body: some View {
        ZStack {
            LinearGradient.screenBackground.ignoresSafeArea()

            switch vm.state {
            case .loading:
                loadingView

            case .error(let msg):
                errorView(msg)

            case .success(let cocktail, let isFav):
                DetailContent(
                    cocktail: cocktail,
                    isFavorite: isFav,
                    onBack: { dismiss() },
                    onToggle: { vm.toggleFavorite() }
                )
            }
        }
        .navigationBarHidden(true)
        .task { await vm.load(id: cocktailId) }
    }

    private var loadingView: some View {
        VStack(spacing: 16) {
            ProgressView().tint(.goldAccent).scaleEffect(1.4)
            Text("Загрузка...").foregroundColor(.textSecondary)
        }
    }

    private func errorView(_ msg: String) -> some View {
        VStack(spacing: 14) {
            Text("⚠️").font(.system(size: 48))
            Text(msg).foregroundColor(.textSecondary).multilineTextAlignment(.center)
            Button("Назад") { dismiss() }
                .foregroundColor(.goldAccent)
        }.padding(32)
    }
}

// MARK: - Detail content with parallax
private struct DetailContent: View {
    let cocktail: Cocktail
    let isFavorite: Bool
    let onBack: () -> Void
    let onToggle: () -> Void

    @State private var scrollOffset: CGFloat = 0
    private let imageHeight: CGFloat = 400

    var body: some View {
        ZStack(alignment: .top) {
            ScrollView(showsIndicators: false) {
                VStack(spacing: 0) {
                    // Parallax image area
                    GeometryReader { geo in
                        let offset = geo.frame(in: .global).minY
                        AsyncImage(url: cocktail.imageUrl) { phase in
                            switch phase {
                            case .success(let img):
                                img.resizable().scaledToFill()
                            default:
                                Color.bgSurface
                                    .overlay(Image(systemName: "wineglass")
                                        .font(.system(size: 80)).foregroundColor(.textHint))
                            }
                        }
                        .frame(width: geo.size.width,
                               height: imageHeight + max(0, offset))
                        .clipped()
                        .offset(y: min(0, -offset * 0.4))
                        // Bottom fade
                        .overlay(LinearGradient(
                            colors: [.clear, Color.black.opacity(0.85)],
                            startPoint: UnitPoint(x: 0.5, y: 0.4), endPoint: .bottom
                        ))
                    }
                    .frame(height: imageHeight)

                    // Body card
                    VStack(alignment: .leading, spacing: 0) {
                        // Name
                        Text(cocktail.name)
                            .font(.system(size: 28, weight: .bold, design: .serif))
                            .foregroundColor(.textPrimary)
                            .lineSpacing(4)

                        Spacer().frame(height: 18)

                        // 3 glassmorphism info cards
                        HStack(spacing: 10) {
                            if !cocktail.category.isEmpty {
                                InfoCard(label: "КАТЕГОРИЯ", value: cocktail.category)
                            }
                            if !cocktail.glass.isEmpty {
                                InfoCard(label: "БОКАЛ", value: cocktail.glass)
                            }
                            InfoCard(
                                label: "АЛКОГОЛЬ",
                                value: cocktail.alcoholicLabel,
                                valueColor: cocktail.isAlcoholic ? .alcoBadge : .nonAlcoBadge
                            )
                        }

                        goldDivider.padding(.vertical, 26)

                        // Ingredients
                        sectionTitle("ИНГРЕДИЕНТЫ")
                        Spacer().frame(height: 14)
                        VStack(spacing: 10) {
                            ForEach(cocktail.ingredients) { ing in
                                IngredientRow(ingredient: ing)
                            }
                        }

                        if !cocktail.instructions.isEmpty {
                            goldDivider.padding(.vertical, 26)
                            sectionTitle("ПРИГОТОВЛЕНИЕ")
                            Spacer().frame(height: 14)
                            Text(cocktail.instructions)
                                .font(.system(size: 15))
                                .foregroundColor(.textSecondary)
                                .lineSpacing(7)
                                .padding(18)
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .background(Color.bgCard)
                                .clipShape(RoundedRectangle(cornerRadius: 16))
                                .overlay(RoundedRectangle(cornerRadius: 16)
                                    .stroke(Color.glassBorder, lineWidth: 1))
                        }

                        Spacer().frame(height: 100)
                    }
                    .padding(.horizontal, 22)
                    .padding(.top, 26)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(
                        LinearGradient.screenBackground
                            .clipShape(RoundedRectangle(cornerRadius: 0))
                    )
                }
            }

            // Top action bar
            HStack {
                circleButton(icon: "chevron.left", action: onBack)
                Spacer()
                circleButton(
                    icon: isFavorite ? "heart.fill" : "heart",
                    color: isFavorite ? .favActive : .white,
                    action: onToggle
                )
            }
            .padding(.horizontal, 14)
            .padding(.top, 56)
        }
    }

    private var goldDivider: some View {
        Rectangle().fill(Color.dividerGold).frame(height: 1)
    }

    private func sectionTitle(_ text: String) -> some View {
        Text(text)
            .font(.system(size: 11, weight: .bold))
            .tracking(2)
            .foregroundColor(.goldAccent)
    }

    private func circleButton(icon: String, color: Color = .white, action: @escaping () -> Void) -> some View {
        Button(action: action) {
            Image(systemName: icon)
                .font(.system(size: 17, weight: .semibold))
                .foregroundColor(color)
                .frame(width: 44, height: 44)
                .background(Color.black.opacity(0.45))
                .clipShape(Circle())
        }
    }
}

// MARK: - Info Glass Card
private struct InfoCard: View {
    let label: String
    let value: String
    var valueColor: Color = .textPrimary

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(label)
                .font(.system(size: 9, weight: .semibold))
                .tracking(1)
                .foregroundColor(.goldAccent)
            Text(value)
                .font(.system(size: 12, weight: .medium))
                .foregroundColor(valueColor)
                .lineLimit(3)
        }
        .padding(.horizontal, 10).padding(.vertical, 12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color.glassBgGold)
        .clipShape(RoundedRectangle(cornerRadius: 14))
        .overlay(RoundedRectangle(cornerRadius: 14)
            .stroke(Color.glassBorderGold, lineWidth: 1))
    }
}

// MARK: - Ingredient Row
private struct IngredientRow: View {
    let ingredient: Ingredient

    var body: some View {
        HStack(spacing: 12) {
            AsyncImage(url: ingredient.imageUrl) { phase in
                if case .success(let img) = phase {
                    img.resizable().scaledToFill()
                } else {
                    Color.bgSurface
                }
            }
            .frame(width: 42, height: 42)
            .clipShape(Circle())

            Text(ingredient.displayName)
                .font(.system(size: 15, weight: .medium))
                .foregroundColor(.textPrimary)
                .frame(maxWidth: .infinity, alignment: .leading)

            if !ingredient.measure.isEmpty {
                Text(ingredient.measure)
                    .font(.system(size: 14, weight: .semibold))
                    .foregroundColor(.goldAccent)
            }
        }
        .padding(.horizontal, 12).padding(.vertical, 10)
        .background(Color.bgCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.glassBorder, lineWidth: 1))
    }
}
