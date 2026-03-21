package com.mixmaster.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mixmaster.app.ui.screens.DetailScreen
import com.mixmaster.app.ui.screens.FavoritesScreen
import com.mixmaster.app.ui.screens.HomeScreen
import com.mixmaster.app.ui.screens.ResultsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Results : Screen("results?alcohol={alcohol}&flavors={flavors}&difficulty={difficulty}&maxStrength={maxStrength}") {
        fun createRoute(
            alcohol: String = "",
            flavors: String = "",
            difficulty: String = "",
            maxStrength: Int = 100
        ) = "results?alcohol=$alcohol&flavors=$flavors&difficulty=$difficulty&maxStrength=$maxStrength"
    }
    object Detail : Screen("detail/{cocktailId}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    object Favorites : Screen("favorites")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onSearch = { alcohol, flavors, difficulty, maxStrength ->
                    navController.navigate(
                        Screen.Results.createRoute(alcohol, flavors, difficulty, maxStrength)
                    )
                },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) }
            )
        }

        composable(
            route = Screen.Results.route,
            arguments = listOf(
                navArgument("alcohol") { type = NavType.StringType; defaultValue = "" },
                navArgument("flavors") { type = NavType.StringType; defaultValue = "" },
                navArgument("difficulty") { type = NavType.StringType; defaultValue = "" },
                navArgument("maxStrength") { type = NavType.IntType; defaultValue = 100 }
            )
        ) { backStackEntry ->
            val alcohol = backStackEntry.arguments?.getString("alcohol") ?: ""
            val flavors = backStackEntry.arguments?.getString("flavors") ?: ""
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
            val maxStrength = backStackEntry.arguments?.getInt("maxStrength") ?: 100
            ResultsScreen(
                alcohol = alcohol,
                flavors = flavors,
                difficulty = difficulty,
                maxStrength = maxStrength,
                onCocktailClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("cocktailId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("cocktailId") ?: return@composable
            DetailScreen(
                cocktailId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onCocktailClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
