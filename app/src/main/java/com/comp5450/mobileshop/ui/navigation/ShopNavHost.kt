package com.comp5450.mobileshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comp5450.mobileshop.data.SampleData
import com.comp5450.mobileshop.ui.ShopViewModel
import com.comp5450.mobileshop.ui.screens.MainScreen
import com.comp5450.mobileshop.ui.screens.ProductDetailScreen
import com.comp5450.mobileshop.ui.screens.SplashScreen

@Composable
fun ShopNavHost(
    vm: ShopViewModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
    ) {
        composable("splash") {
            SplashScreen(
                onGetStarted = {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
            )
        }
        composable("main") {
            MainScreen(
                vm = vm,
                onProductClick = { product ->
                    navController.navigate("detail/${product.id}")
                },
            )
        }
        composable("detail/{id}") { entry ->
            val id = entry.arguments?.getString("id").orEmpty()
            val product = SampleData.productById(id)
            if (product == null) {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            } else {
                ProductDetailScreen(
                    product = product,
                    vm = vm,
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
