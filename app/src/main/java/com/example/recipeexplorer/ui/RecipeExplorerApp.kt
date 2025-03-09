package com.example.recipeexplorer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
    windowSize: WindowWidthSizeClass, // enum class WindowWidthSizeClass { Compact, Medium, Expanded }
    modifier: Modifier = Modifier
) {
    val recipeViewModel: RecipeViewModel = viewModel() //declares viewModel
    val recipeUiState = recipeViewModel.uiState.collectAsState().value //declares uiState


    val navigationType: RecipeExplorerNavigationType    //located in WindowStateUtils.kt
    val contentType: RecipeExplorerContentType  //located in WindowStateUtils.kt

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


//    //Create a NavController for navigation
//    val navController = rememberNavController() //initialize NavController
//
//
//    //Create ViewModel instance
//    //val recipeViewModel = RecipeViewModel : ViewModel()
//    // For medium or expanded window size, use the Navigation() composable
//    if (windowSize == WindowWidthSizeClass.Medium || windowSize == WindowWidthSizeClass.Expanded) {
//        //Use Navigation composable to handle the layout and screens.
//        Navigation(
//            navigationType = navigationType,
//            contentType = contentType,
//            recipeUiState = recipeUiState,
//            recipeViewModel = recipeViewModel,
//            modifier = Modifier//.weight(1f).fillMaxHeight()
//        )
//    } else {
//        // For compact window sizes, handle navigation differently (you can add bottom navigation or another type here)
        Navigation(
            navigationType = navigationType,
            contentType = contentType,
            recipeUiState = recipeUiState,
            recipeViewModel = recipeViewModel,
            windowSize = windowSize,
            modifier = modifier
        )
//    }
}

