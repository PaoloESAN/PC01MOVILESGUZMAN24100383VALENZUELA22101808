package com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.destinations.DestinationsScreen
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.equipaje.EquipmentCalculatorScreen
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.menu.MenuScreen
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.permissions.LocationPermissionScreen
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.presupuesto.TravelBudgetPlannerScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") { MenuScreen(navController) }
        composable("equipaje") { EquipmentCalculatorScreen(navController) }
        composable("presupuesto") { TravelBudgetPlannerScreen(navController) }
        composable("destinos") { DestinationsScreen(navController) }
        composable("ubicacion") { LocationPermissionScreen(navController) }
    }
}
