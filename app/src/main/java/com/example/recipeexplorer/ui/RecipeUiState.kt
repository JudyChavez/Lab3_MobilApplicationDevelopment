package com.example.recipeexplorer.ui

import com.example.recipeexplorer.model.Recipe

//The RecipeUiState class holds the actual data that will be displayed on the screen.
//this class holds a list of Recipe objects.
//recipes list is initialized when recipes are loaded in the RecipeViewModel.
data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(), // Default to an empty list
    val selectedRecipe: Recipe? = null,
    val isShowingListPage: Boolean = true
) {

}

