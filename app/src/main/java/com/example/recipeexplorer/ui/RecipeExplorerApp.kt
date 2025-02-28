package com.example.recipeexplorer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipeexplorer.R


/**
 * Composable that displays an app bar and a list of recipes
 */
//RecipeApp() contains a LazyColumn that displays the RecipeItems.
@Composable
fun RecipeExplorerApp(

) {
    RecipeItem("hello")
}

@Composable
fun RecipeItem(
    recipe: String,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column() {
            Text(
                text = "Spaghetti Bolognese",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(mediumPadding)
            )
            Text(
                text = stringResource(R.string.recipe_description_1),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
            )
        }
    }
}


