// swift-tools-version: 5.9
// ──────────────────────────────────────────────────────────────────────────
// МиксМастер — iOS SwiftUI App (Swift Package)
//
// КАК ОТКРЫТЬ В XCODE:
//   1. Запусти Xcode → File → Open → выбери эту папку (ios/)
//   2. Xcode откроет пакет. Создай новый iOS App target:
//      File → New → Target → iOS App, имя "МиксМастер"
//   3. В настройках target добавь зависимость на этот пакет,
//      или просто перетащи папку Sources/MixMaster в проект.
//   4. В Info.plist добавь: NSAppTransportSecurity → NSAllowsArbitraryLoads = YES
//      (или добавь разрешение для thecocktaildb.com)
// ──────────────────────────────────────────────────────────────────────────
import PackageDescription

let package = Package(
    name: "MixMaster",
    platforms: [.iOS(.v16)],
    products: [
        .library(name: "MixMaster", targets: ["MixMaster"])
    ],
    targets: [
        .target(
            name: "MixMaster",
            path: "Sources/MixMaster"
        )
    ]
)
