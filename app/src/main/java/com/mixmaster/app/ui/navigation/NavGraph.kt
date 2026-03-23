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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
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
import com.mixmaster.app.ui.screens.SearchScreen
import com.mixmaster.app.ui.screens.SplashScreen
import com.mixmaster.app.ui.theme.BgCard
import com.mixmaster.app.ui.theme.BgPrimary
import com.mixmaster.app.ui.theme.BgSecondary
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.TextHint

sealed class Screen(val route: String) {
    object Splash    : Screen("splash")
    object Home      : Screen("home")
    object Search    : Screen("search")
    object Favorites : Screen("favorites")
    object Detail    : Screen("detail/{cocktailId}") {
        fun createRoute(id: String) = "detail/$id"
    }
}

private val bottomNavRoutes = listOf(Screen.Home.route, Screen.Search.route, Screen.Favorites.route)

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomNavRoutes

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
                    onSearchClick = {
                        navController.navigate(Screen.Search.route) {
                            popUpTo(Screen.Home.route)
                            launchSingleTop = true
                        }
                    },
                    onFavoritesClick = {
                        navController.navigate(Screen.Favorites.route) {
                            popUpTo(Screen.Home.route)
                            launchSingleTop = true
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
                    Brush.verticalGradient(listOf(BgSecondary, BgPrimary))
                )
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Splash.route) {
                    SplashScreen(
                        onFinished = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen(
                        bottomPadding = innerPadding.calculateBottomPadding(),
                        onNavigateToDetail = { id ->
                            navController.navigate(Screen.Detail.createRoute(id))
                        }
                    )
                }

                composable(Screen.Search.route) {
                    SearchScreen(
                        bottomPadding = innerPadding.calculateBottomPadding(),
                        onCocktailClick = { id ->
                            navController.navigate(Screen.Detail.createRoute(id))
                        }
                    )
                }

                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        bottomPadding = innerPadding.calculateBottomPadding(),
                        onCocktailClick = { id ->
                            navController.navigate(Screen.Detail.createRoute(id))
                        }
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
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    currentRoute: String?,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    val isHome = currentRoute == Screen.Home.route
    val isSearch = currentRoute == Screen.Search.route
    val isFav = currentRoute == Screen.Favorites.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = BgCard.copy(alpha = 0.97f),
        tonalElevation = 0.dp
    ) {
        val homeScale by animateFloatAsState(
            targetValue = if (isHome) 1.15f else 1f,
            animationSpec = tween(200), label = "hScale"
        )
        val searchScale by animateFloatAsState(
            targetValue = if (isSearch) 1.15f else 1f,
            animationSpec = tween(200), label = "sScale"
        )
        val favScale by animateFloatAsState(
            targetValue = if (isFav) 1.15f else 1f,
            animationSpec = tween(200), label = "fScale"
        )

        NavigationBarItem(
            selected = isHome,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = if (isHome) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Главная",
                    modifier = Modifier.size(26.dp).scale(homeScale)
                )
            },
            label = { Text("Главная") },
            colors = navItemColors()
        )

        NavigationBarItem(
            selected = isSearch,
            onClick = onSearchClick,
            icon = {
                Icon(
                    imageVector = if (isSearch) Icons.Filled.Search else Icons.Outlined.Search,
                    contentDescription = "Поиск",
                    modifier = Modifier.size(26.dp).scale(searchScale)
                )
            },
            label = { Text("Поиск") },
            colors = navItemColors()
        )

        NavigationBarItem(
            selected = isFav,
            onClick = onFavoritesClick,
            icon = {
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Избранное",
                    modifier = Modifier.size(26.dp).scale(favScale)
                )
            },
            label = { Text("Избранное") },
            colors = navItemColors()
        )
    }
}

@Composable
private fun navItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = GoldAccent,
    selectedTextColor = GoldAccent,
    unselectedIconColor = TextHint,
    unselectedTextColor = TextHint,
    indicatorColor = GoldAccent.copy(alpha = 0.15f)
)
