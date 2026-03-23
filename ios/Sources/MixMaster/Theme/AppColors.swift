import SwiftUI

// MARK: - Luxury Bar Palette (mirrors Android Color.kt)
extension Color {
    // Backgrounds
    static let bgPrimary     = Color(hex: 0x0D0D0D)
    static let bgSecondary   = Color(hex: 0x1A1A2E)
    static let bgSurface     = Color(hex: 0x16213E)
    static let bgCard        = Color(hex: 0x1A1A2E).opacity(0.80)

    // Gold
    static let goldAccent    = Color(hex: 0xD4AF37)
    static let goldLight     = Color(hex: 0xF4E5B2)
    static let goldDark      = Color(hex: 0x9C7A1A)
    static let copperAccent  = Color(hex: 0xB87333)

    // Text
    static let textPrimary   = Color(hex: 0xF5F5F5)
    static let textSecondary = Color(hex: 0xA0A0B0)
    static let textOnGold    = Color(hex: 0x0D0D0D)
    static let textHint      = Color(hex: 0x666680)

    // Status
    static let errorRed      = Color(hex: 0xE74C3C)
    static let favActive     = Color(hex: 0xE74C3C)
    static let alcoBadge     = Color(hex: 0xE74C3C)
    static let nonAlcoBadge  = Color(hex: 0x27AE60)

    // Dividers & overlays
    static let dividerGold   = Color(hex: 0xD4AF37).opacity(0.25)
    static let glassBg       = Color.white.opacity(0.08)
    static let glassBorder   = Color.white.opacity(0.15)
    static let glassBorderGold = Color.goldAccent.opacity(0.40)
    static let glassBgGold   = Color.goldAccent.opacity(0.10)
}

extension Color {
    init(hex: UInt32) {
        let r = Double((hex >> 16) & 0xFF) / 255
        let g = Double((hex >>  8) & 0xFF) / 255
        let b = Double( hex        & 0xFF) / 255
        self.init(red: r, green: g, blue: b)
    }
}

// MARK: - Gradient helpers
extension LinearGradient {
    static let heroGradient = LinearGradient(
        colors: [.bgSecondary, Color(hex: 0x0D0820), .bgPrimary],
        startPoint: .top, endPoint: .bottom
    )
    static let cardOverlay = LinearGradient(
        colors: [.clear, Color.black.opacity(0.90)],
        startPoint: .top, endPoint: .bottom
    )
    static let goldGradient = LinearGradient(
        colors: [.goldDark, .goldAccent, .goldLight],
        startPoint: .leading, endPoint: .trailing
    )
    static let screenBackground = LinearGradient(
        colors: [.bgSecondary, .bgPrimary],
        startPoint: .top, endPoint: .bottom
    )
}
