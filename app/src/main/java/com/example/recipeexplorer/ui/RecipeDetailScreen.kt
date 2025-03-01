package com.example.recipeexplorer.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe


//RecipeDetailScreen() – Displays detailed information about a selected recipe.


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreenTopAppBar(topBarTitle: String, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar( //had to add to build.gradle.kts(Module :app): freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        title = {
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
    @StringRes recipeId: Int, //Get recipe ID as a parameter.
    modifier: Modifier = Modifier
) {
    // Sample data for the recipe (you can replace this with actual data based on recipeId)
    val recipe = Datasource().loadRecipes().find { it.id == recipeId }

    //structure layout
    Scaffold(
        topBar = {
            if (recipe != null) {
                RecipeDetailScreenTopAppBar(
                    topBarTitle = LocalContext.current.getString(recipe.title)  //stringResource(id = R.string.recipe_detail_screen_top_app_bar)
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
                recipe?.let {   //next line only executes if recipe is not null, otherwise it's skipped. Shorter than using an if statement.
                    RecipeDetailCard(recipe = it)
                }

            }
        }
    )
}

    //each individual recipe card, it contains: title and description
    @Composable
    fun RecipeDetailCard(
        recipe: Recipe,
        modifier: Modifier = Modifier
    ) {
        // Display recipe details in a card
        Card(modifier = modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                //Recipe description
                Text(
                    text = LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

