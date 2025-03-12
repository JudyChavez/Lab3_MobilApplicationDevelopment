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
    modifier: Modifier = Modifier
) {
    //Observe the selected recipe from ViewModel
    val selectedRecipe = recipeViewModel.selectedRecipe

    // Ensure the state is available even after recomposition
    if (selectedRecipe == null) {
        // Update the selected recipe state if it's null
        val recipe = /*null*/Datasource().loadRecipes().find { it.id == recipeId }
        recipe?.let {
            recipeViewModel.selectRecipe(/*null*/it) // Update the selected recipe state in ViewModel
        }
    }

    val recipe = recipeViewModel.uiState.collectAsState().value.recipes.find {it.id == 0/*recipeId*/ }

    // Handle the back button press
    BackHandler {
        // Check if there is a screen to go back to
        if (navController.previousBackStackEntry != null) {
            // Clear selected recipe before popping the back stack
            recipeViewModel.selectRecipe(null)
            // There is a screen to pop back to
            navController.popBackStack()
        } else {
            // Clear selected recipe before navigating to RecipeList
            //recipeViewModel.selectRecipe(null)
            // Navigate to the RecipeList screen
            navController.navigate(Screen.RecipeList.name)
        }
    }

    //structure layout
    Scaffold(
        topBar = {
            if (recipeViewModel.selectedRecipe?.id == 0) {
                RecipeDetailScreenTopAppBar(
                    topBarTitle = "Recipe Detail")
            } else if (/*recipe*/selectedRecipe != null) {
                RecipeDetailScreenTopAppBar(
                    topBarTitle = stringResource(id = /*recipe*/selectedRecipe.title) //LocalContext.current.getString(recipe.title)  //stringResource(id = R.string.recipe_detail_screen_top_app_bar)
//                    topBarTitle = stringResource(id = recipe.title)
                )
            } else {
                RecipeDetailScreenTopAppBar(
                    topBarTitle = "Recipe Is Null"
                )
            }
        },
        content = { paddingValues ->
            //content below top app bar
            Column(
                modifier = Modifier
                    .padding(paddingValues) //to account for top app bar paddingValues
                    .padding(dimensionResource(R.dimen.padding_medium)) //additional padding for content.
            ) {
                // Show the recipe detail card
                /*recipe*/selectedRecipe?.let {   //next line only executes if recipe is not null, otherwise it's skipped. Shorter than using an if statement.
                    RecipeDetailCard(recipe = it, recipeUiState = recipeUiState, recipeViewModel = recipeViewModel)
                }
                if (/*recipe*/selectedRecipe == null) {
                    RecipeDetailCard(
                        recipe = //null,
                            Recipe(
                                id = 0,
                                title = stringResource(R.string.recipe_title_null).toInt(),
                                description = stringResource(R.string.recipe_description_null).toInt()
                            ),
                        recipeUiState = recipeUiState,
                        recipeViewModel = recipeViewModel
                    )
                    //Text(text = "Select a recipe!!!")
                }
            }
        }
    )
}

//each individual recipe card, it contains: title and description
@Composable
fun RecipeDetailCard(
    recipe: Recipe,
    recipeUiState: RecipeUiState,
    recipeViewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    // Display recipe details in a card
//    Card(modifier = modifier.fillMaxWidth()) {
//        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {

    if (recipeViewModel.selectedRecipe?.id == 0) {
            //Recipe description
        Text(
            text = "Select a recipe!!!",
            style = MaterialTheme.typography.bodyLarge
        )
    } else {
        Text(
            text = stringResource(id = recipe.description),//LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

//}}

