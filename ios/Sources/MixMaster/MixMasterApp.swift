import SwiftUI

@main
struct MixMasterApp: App {
    // Core Data container injected into environment
    let persistence = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistence.ctx)
                .preferredColorScheme(.dark)
        }
    }
}
