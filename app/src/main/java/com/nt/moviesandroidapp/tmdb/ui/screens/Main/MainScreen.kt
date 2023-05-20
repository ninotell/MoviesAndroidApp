package com.nt.moviesandroidapp.tmdb.ui.screens.Main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nt.moviesandroidapp.tmdb.ui.screens.Trending.TrendingViewModel
import com.nt.moviesandroidapp.tmdb.ui.navigation.HomeNavGraph
import com.nt.moviesandroidapp.tmdb.ui.navigation.HomeNavScreen
import com.nt.moviesandroidapp.tmdb.ui.navigation.HomeNavScreen.*
import com.nt.moviesandroidapp.ui.theme.CustomYellow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, trendingViewModel: TrendingViewModel) {
    val items = listOf(
        Trending,
        Search,
        Favorites
    )
    Scaffold(
        bottomBar = { MainBottomNavigationBar(navController = navController, items = items) }
    ) {
        HomeNavGraph(navController = navController, trendingViewModel = trendingViewModel)
    }
}

@Composable
fun MainBottomNavigationBar(
    navController: NavHostController,
    items: List<HomeNavScreen>
) {
    val currentRoute = currentRoute(navController = navController)

    val bottomBarDestination = items.any { it.route == currentRoute }

    if (!bottomBarDestination) {
        return
    }

    BottomNavigation(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier.background(Color.Black.copy(alpha = 0.8f)),
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "navItemIcon") },
                label = { Text(text = screen.title, style = MaterialTheme.typography.caption) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = false,
                selectedContentColor = CustomYellow,
                unselectedContentColor = Color.LightGray.copy(0.7f)
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}