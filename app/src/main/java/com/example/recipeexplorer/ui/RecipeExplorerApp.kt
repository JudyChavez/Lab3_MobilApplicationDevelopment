package com.example.recipeexplorer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.ui.utils.RecipeExplorerNavigationType

import com.example.recipeexplorer.ui.utils.RecipeExplorerContentType


//RecipeExplorerApp() – Entry point that sets up adaptive layouts.
@Composable
fun RecipeExplorerApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: RecipeViewModel = viewModel()
    val recipeUiState = viewModel.uiState.collectAsState().value


    val navigationType: RecipeExplorerNavigationType
    val contentType: RecipeExplorerContentType


    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = RecipeExplorerNavigationType.BOTTOM_NAVIGATION
            contentType = RecipeExplorerContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = RecipeExplorerNavigationType.NAVIGATION_RAIL
            contentType = RecipeExplorerContentType.LIST_AND_DETAIL
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = RecipeExplorerNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = RecipeExplorerContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = RecipeExplorerNavigationType.BOTTOM_NAVIGATION
            contentType = RecipeExplorerContentType.LIST_ONLY
        }
    }

    //Create a NavController
    val navController = rememberNavController()

    if (
        windowSize == WindowWidthSizeClass.Medium || windowSize == WindowWidthSizeClass.Expanded
        ) {
        Row(modifier = modifier.fillMaxSize()) {
            //Navigation Host
            NavHost(
                navController = navController,
                startDestination = "recipeList",
                modifier = Modifier.weight(1f)
            ) {
                //define RecipeListScreen() route
                composable("recipeList") {
                    RecipeListScreen(
                        navController = navController,
                        navigationType = navigationType,
                        contentType = contentType,
                        recipeUiState = recipeUiState,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                //define RecipeDetailScreen() route with an argument for recipeId
                composable(
                    "recipeDetail/{recipeId}",
                    arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                    RecipeDetailScreen(
                        recipeId = recipeId,
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    } else {
        Navigation(
            navigationType = navigationType,
            contentType = contentType,
            recipeUiState = recipeUiState,
            modifier = modifier
        )
    }
}

//            RecipeListScreen(
//                navController = navController,
//                navigationType = navigationType,
//                contentType = contentType,
//                recipeUiState = recipeUiState,
//                modifier = Modifier)
//            RecipeDetailScreen(
//                recipeId = backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0,
//                recipeId = "3",
//                navController = navController,
//                modifier = modifier
//            )



