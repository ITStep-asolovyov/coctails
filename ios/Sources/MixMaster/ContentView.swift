import SwiftUI

// MARK: - Root view: Splash → TabView
struct ContentView: View {
    @State private var showSplash = true

    var body: some View {
        Group {
            if showSplash {
                SplashView {
                    withAnimation(.easeOut(duration: 0.4)) {
                        showSplash = false
                    }
                }
            } else {
                MainTabView()
            }
        }
    }
}

// MARK: - 3-tab navigation
struct MainTabView: View {
    init() {
        // Style tab bar to match dark luxury theme
        let appearance = UITabBarAppearance()
        appearance.configureWithOpaqueBackground()
        appearance.backgroundColor = UIColor(red: 0.10, green: 0.10, blue: 0.18, alpha: 0.97)
        UITabBar.appearance().standardAppearance   = appearance
        UITabBar.appearance().scrollEdgeAppearance = appearance
        UITabBar.appearance().unselectedItemTintColor =
            UIColor(red: 0.40, green: 0.40, blue: 0.50, alpha: 1.0)
    }

    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Label("Главная", systemImage: "house.fill")
                }

            SearchView()
                .tabItem {
                    Label("Поиск", systemImage: "magnifyingglass")
                }

            FavoritesView()
                .tabItem {
                    Label("Избранное", systemImage: "heart.fill")
                }
        }
        .tint(.goldAccent)
    }
}
