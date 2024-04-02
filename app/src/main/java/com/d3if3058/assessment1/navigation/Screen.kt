package com.d3if3058.assessment1.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Welcome: Screen("welcomeScreen")
}