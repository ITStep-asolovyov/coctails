import SwiftUI

struct SplashView: View {
    var onFinished: () -> Void

    @State private var opacity  = 0.0
    @State private var offsetY  = 40.0

    var body: some View {
        ZStack {
            LinearGradient.screenBackground.ignoresSafeArea()

            VStack(spacing: 12) {
                Text("🍸").font(.system(size: 64))

                Text("МиксМастер")
                    .font(.system(size: 38, weight: .bold, design: .serif))
                    .foregroundColor(.goldAccent)
                    .tracking(2)

                Text("ПРЕМИУМ КОКТЕЙЛИ")
                    .font(.system(size: 11, weight: .semibold))
                    .foregroundColor(.textSecondary)
                    .tracking(4)
            }
            .opacity(opacity)
            .offset(y: offsetY)
        }
        .onAppear {
            withAnimation(.easeOut(duration: 0.7)) {
                opacity = 1; offsetY = 0
            }
            Task {
                try? await Task.sleep(nanoseconds: 1_800_000_000)
                onFinished()
            }
        }
    }
}
