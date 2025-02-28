package com.example.recipeexplorer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current //left to right, or right to left.
    //The Surface() composable will set the padding for the RecipeList composable.
    Surface(
        modifier = Modifier
            .fillMaxSize() //fills the max width and height of its parent.
            .statusBarsPadding() //sets status bar padding.
            .padding( //sets the start and end padding to the layoutDirection.
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ) {
        //call the RecipeList composable, and pass DataSource().loadRecipes() to the recipeList parameter.
        RecipeList(
            recipeList = Datasource().loadRecipes()
        )

    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Text(
                text = LocalContext.current.getString(recipe.title), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun RecipeList(recipeList: List<Recipe>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        //items() method is how you add items to the LazyColumn.
        //for each recipe in the list, call the RecipeCard() composable.
        items(recipeList) { recipe ->
            RecipeCard(
                recipe = recipe,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}