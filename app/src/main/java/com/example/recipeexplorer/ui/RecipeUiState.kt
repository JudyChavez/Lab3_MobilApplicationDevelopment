package com.example.recipeexplorer.ui

import android.app.ActivityManager.TaskDescription
import com.example.recipeexplorer.model.Recipe


//The RecipeUiState class holds the actual data that will be displayed on the screen.
data class RecipeUiState(
    val recipeTitle: String = "",
    val recipeDescription: String = "",

    val recipes: List<Recipe> = emptyList() // Default to an empty list
) {

}

////holds the state for the UI.
////contains a list of Recipe objects.
//data class RecipeUiState(
//    val recipes: List<Recipe> = emptyList(),
//
//)