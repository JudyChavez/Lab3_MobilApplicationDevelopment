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
import com.example.recipeexplorer.data.Datasource




//RecipeExplorerApp() – Entry point that sets up adaptive layouts.


@Composable
fun RecipeExplorerApp(
    modifier: Modifier = Modifier
) {
   // RecipeListScreen()
    RecipeDetailScreen(5)

}




