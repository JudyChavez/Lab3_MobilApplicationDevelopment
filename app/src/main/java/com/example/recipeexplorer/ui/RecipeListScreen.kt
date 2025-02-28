package com.example.recipeexplorer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe




//RecipeListScreen() – Displays the list of recipes.


@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier
) {
    //call the RecipeList composable, and pass DataSource().loadRecipes() to the recipeList parameter.
    RecipeList(
        recipeList = Datasource().loadRecipes()
    )
}

//each individual recipe card, it contains: title and description
@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            Text(
                text = LocalContext.current.getString(recipe.title), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.displayLarge //Material3 typography
            )
            Text(
                text = LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeExplorerTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar( //had to add to build.gradle.kts(Module :app): freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically //makes RecipeExplorerTopAppBar content center vertically.
            ) {
                Text(
                    text = stringResource(R.string.recipe_list_screen_top_app_bar),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}


//adds each recipe card to a LazyColumn for display.
@Composable
fun RecipeList(recipeList: List<Recipe>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            RecipeExplorerTopAppBar()
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            //items() method is how you add items to the LazyColumn.
            //for each recipe in the list, call the RecipeCard() composable.
            items(recipeList) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

////adds each recipe card to a LazyColumn for display.
//@Composable
//fun RecipeList(recipeList: List<Recipe>, modifier: Modifier = Modifier) {
//    LazyColumn(modifier = modifier) {
//        //items() method is how you add items to the LazyColumn.
//        //for each recipe in the list, call the RecipeCard() composable.
//        items(recipeList) { recipe ->
//            RecipeCard(
//                recipe = recipe,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//    }
//}









