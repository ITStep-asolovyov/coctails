import SwiftUI

struct CocktailCard: View {
    let item: CocktailListItem
    var index: Int = 0

    @State private var visible = false
    @State private var isPressed = false

    var body: some View {
        ZStack(alignment: .bottomLeading) {
            // Photo
            AsyncImage(url: item.imageUrl) { phase in
                switch phase {
                case .success(let img):
                    img.resizable().scaledToFill()
                case .failure, .empty:
                    Rectangle().fill(Color.bgCard)
                        .overlay(Image(systemName: "wineglass").font(.system(size: 48)).foregroundColor(.textHint))
                @unknown default:
                    Color.bgCard
                }
            }
            .frame(maxWidth: .infinity)
            .frame(height: 200)
            .clipped()

            // Gradient overlay
            LinearGradient.cardOverlay

            // Name
            Text(item.name)
                .font(.system(size: 18, weight: .bold, design: .serif))
                .foregroundColor(.white)
                .lineLimit(2)
                .padding(16)
        }
        .frame(height: 200)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .scaleEffect(isPressed ? 0.97 : 1.0)
        .opacity(visible ? 1 : 0)
        .animation(.easeOut(duration: 0.35), value: visible)
        .animation(.easeOut(duration: 0.12), value: isPressed)
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + Double(index) * 0.06) {
                visible = true
            }
        }
        ._onButtonGesture(pressing: { isPressed = $0 }, perform: {})
    }
}

// MARK: - Shimmer card placeholder
struct ShimmerCard: View {
    @State private var phase = 0.0

    var body: some View {
        RoundedRectangle(cornerRadius: 16)
            .fill(shimmerGradient(phase: phase))
            .frame(height: 200)
            .onAppear {
                withAnimation(.linear(duration: 1.4).repeatForever(autoreverses: false)) {
                    phase = 1
                }
            }
    }

    private func shimmerGradient(phase: Double) -> LinearGradient {
        LinearGradient(
            stops: [
                .init(color: Color.bgSecondary, location: phase - 0.3),
                .init(color: Color(hex: 0x2E2A18), location: phase),
                .init(color: Color.bgSecondary, location: phase + 0.3),
            ],
            startPoint: .topLeading, endPoint: .bottomTrailing
        )
    }
}
