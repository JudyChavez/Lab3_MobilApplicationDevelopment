package com.example.recipeexplorer.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipeexplorer.ui.utils.RecipeExplorerNavigationType
import com.example.recipeexplorer.ui.utils.RecipeExplorerContentType

//RecipeExplorerApp() – Entry point that sets up adaptive layouts.
@Composable
fun RecipeExplorerApp(
    windowSize: WindowWidthSizeClass, // enum class WindowWidthSizeClass { Compact, Medium, Expanded }
    modifier: Modifier = Modifier
) {
    val recipeViewModel: RecipeViewModel = viewModel() //declares recipeViewModel, holds UI state for the recipe list.

    val recipeUiState by recipeViewModel.uiState.collectAsState() //declares uiState, is observed using .collectAsState()

    val navigationType: RecipeExplorerNavigationType    //located in WindowStateUtils.kt
    val contentType: RecipeExplorerContentType  //located in WindowStateUtils.kt

    //Adjusts UI based on windowSize. For this lab, navigationType is not needed (only there as a reminder for future improvement).
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

    //Create a NavController for navigation
    val navController: NavHostController = rememberNavController() //initialize navController, handles the app's navigation logic.

    Navigation(
        navigationType = navigationType, //not used in this lab
        contentType = contentType,
        recipeUiState = recipeUiState, //State containing recipes and selectedRecipe
        recipeViewModel = recipeViewModel,
        windowSize = windowSize,
        navController = navController, //pass the NavController to Navigation
        modifier = modifier
    )
}

