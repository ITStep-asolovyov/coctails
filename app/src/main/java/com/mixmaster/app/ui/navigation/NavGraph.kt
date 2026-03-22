package com.mixmaster.app.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mixmaster.app.ui.screens.DetailScreen
import com.mixmaster.app.ui.screens.FavoritesScreen
import com.mixmaster.app.ui.screens.HomeScreen
import com.mixmaster.app.ui.screens.ResultsScreen
import com.mixmaster.app.ui.theme.BackgroundGradientEnd
import com.mixmaster.app.ui.theme.BackgroundGradientStart
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.SurfaceCard
import com.mixmaster.app.ui.theme.TextMuted

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Results : Screen("results?query={query}&filter={filter}") {
        fun createRoute(query: String = "", filter: String = "") =
            "results?query=$query&filter=$filter"
    }
    object Detail : Screen("detail/{cocktailId}") {
        fun createRoute(id: String) = "detail/$id"
    }
    object Favorites : Screen("favorites")
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(Screen.Home.route, Screen.Favorites.route)

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onHomeClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    onFavoritesClick = {
                        navController.navigate(Screen.Favorites.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BackgroundGradientStart, BackgroundGradientEnd)
                    )
                )
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        bottomPadding = innerPadding.calculateBottomPadding(),
                        onSearch = { query, filter ->
                            navController.navigate(Screen.Results.createRoute(query, filter))
                        },
                        onNavigateToDetail = { id ->
                            navController.navigate(Screen.Detail.createRoute(id))
                        }
                    )
                }

                composable(
                    route = Screen.Results.route,
                    arguments = listOf(
                        navArgument("query") { type = NavType.StringType; defaultValue = "" },
                        navArgument("filter") { type = NavType.StringType; defaultValue = "" }
                    )
                ) { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query") ?: ""
                    val filter = backStackEntry.arguments?.getString("filter") ?: ""
                    ResultsScreen(
                        query = query,
                        filter = filter,
                        onCocktailClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("cocktailId") ?: return@composable
                    DetailScreen(
                        cocktailId = id,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        bottomPadding = innerPadding.calculateBottomPadding(),
                        onCocktailClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    currentRoute: String?,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    val isHomeSelected = currentRoute == Screen.Home.route
    val isFavSelected = currentRoute == Screen.Favorites.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = SurfaceCard.copy(alpha = 0.97f),
        tonalElevation = 0.dp
    ) {
        val homeScale by animateFloatAsState(
            targetValue = if (isHomeSelected) 1.15f else 1.0f,
            animationSpec = tween(200),
            label = "homeScale"
        )
        val favScale by animateFloatAsState(
            targetValue = if (isFavSelected) 1.15f else 1.0f,
            animationSpec = tween(200),
            label = "favScale"
        )

        NavigationBarItem(
            selected = isHomeSelected,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = if (isHomeSelected) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Главная",
                    modifier = Modifier.size(26.dp).scale(homeScale)
                )
            },
            label = { Text("Главная") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldAccent,
                selectedTextColor = GoldAccent,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = GoldAccent.copy(alpha = 0.15f)
            )
        )

        NavigationBarItem(
            selected = isFavSelected,
            onClick = onFavoritesClick,
            icon = {
                Icon(
                    imageVector = if (isFavSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Избранное",
                    modifier = Modifier.size(26.dp).scale(favScale)
                )
            },
            label = { Text("Избранное") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldAccent,
                selectedTextColor = GoldAccent,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = GoldAccent.copy(alpha = 0.15f)
            )
        )
    }
}
