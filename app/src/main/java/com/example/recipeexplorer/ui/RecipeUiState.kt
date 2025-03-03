package com.example.recipeexplorer.ui

import android.app.ActivityManager.TaskDescription


//The RecipeUiState class holds the actual data that will be displayed on the screen.
data class RecipeUiState(
    val recipeTitle: String = "",
    val recipeDescription: String = "",

    val currentSelectedRecipe: String = ""
)

