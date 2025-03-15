package com.example.recipeexplorer.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe
import com.example.recipeexplorer.ui.utils.RecipeExplorerContentType
import com.example.recipeexplorer.ui.utils.RecipeExplorerNavigationType

//RecipeDetailScreen() – Displays detailed information about a selected recipe.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreenTopAppBar(topBarTitle: String, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar( //had to add to build.gradle.kts(Module :app): freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        title = { //lambda function parameter that defines the content of the top app bar.
            Row(
                verticalAlignment = Alignment.CenterVertically //makes RecipeExplorerTopAppBar content center vertically.
            ) {
                Text(
                    text = topBarTitle, //stringResource(R.string.recipe_detail_screen_top_app_bar),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun RecipeDetailScreen(
    @StringRes recipeId: Int?, //Get recipe ID as a parameter.
    recipeViewModel: RecipeViewModel, // = viewModel(),
    navController: NavHostController,
    recipeUiState: RecipeUiState,
    windowSize: WindowWidthSizeClass,
    contentType: RecipeExplorerContentType,
    onBackPressed: () -> Unit,
    //selectedRecipe: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    //Observe the selected recipe from ViewModel
    /*val selectedRecipe = recipeViewModel.selectedRecipe*/val selectedRecipe = recipeUiState.selectedRecipe



    // Ensure the state is available even after recomposition
    if (selectedRecipe == null) {
        // Update the selected recipe state if it's null
        val recipe: Recipe? = /*null*/Datasource().loadRecipes().find { it.id == recipeId }
        recipe?.let {
            recipeViewModel.selectRecipe(/*null*/it) // Update the selected recipe state in ViewModel
        }
    }

    val recipeO = recipeViewModel.uiState.collectAsState().value.recipes.find {it.id == 0/*recipeId*/ }

    // Handle the back button press
    BackHandler {
        // Clear the selected recipe before navigating back
        recipeViewModel.selectRecipe(null)

        // Then, handle the navigation back
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            navController.navigate(Screen.RecipeList.name) // Navigate to the list if no back stack
        }
    }

    //structure layout
    Scaffold(
        topBar = {
            when {
                //when selectedRecipe is null
                /*recipeViewModel.selectedRecipe == null -> {*/recipeUiState.selectedRecipe == null -> {
                    RecipeDetailScreenTopAppBar(topBarTitle = "Recipe Details")
                }
                //when selectedRecipe is not null and has a valid title
                selectedRecipe != null -> {
                    RecipeDetailScreenTopAppBar(topBarTitle = stringResource(id = /*recipe*/selectedRecipe.title))
                }
                //Default case
                else -> {
                    RecipeDetailScreenTopAppBar(topBarTitle = "Unexpected!")
                }
            }
        },

        content = { paddingValues ->
            //content below top app bar
            Column(
                modifier = Modifier
                    .padding(paddingValues) //to account for top app bar paddingValues
                    .padding(dimensionResource(R.dimen.padding_medium)) //additional padding for content.
            ) {
                //val currentRecipe = recipeViewModel.selectedRecipe
                RecipeDetailCard(
                    recipe = selectedRecipe,//currentRecipe,
                    recipeUiState = recipeUiState,
                    recipeViewModel = recipeViewModel,
                    selectedRecipe = selectedRecipe
                )
            }
        }
    )
}

//Description for selected recipe.
@Composable
fun RecipeDetailCard(
    recipe: Recipe?,
    recipeUiState: RecipeUiState,
    recipeViewModel: RecipeViewModel,
    selectedRecipe: Recipe?,
    modifier: Modifier = Modifier
) {
    if (selectedRecipe == null ) {
        Text(
            text = "Select a Recipe!",//stringResource(id = recipe.description),//LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
            style = MaterialTheme.typography.bodyLarge
        )
    } else {
        Text(
            text = stringResource(id = selectedRecipe.description),//LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
            style = MaterialTheme.typography.bodyLarge
        )
    }
}



