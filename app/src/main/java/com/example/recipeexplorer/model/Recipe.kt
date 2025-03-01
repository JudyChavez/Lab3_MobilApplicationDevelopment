package com.example.recipeexplorer.model

import androidx.annotation.StringRes

/**
 * A data class to represent the information presented in the recipe card
 */
data class Recipe(
    //stringResourceId represents an ID for the recipe text stored in a string resource.
    @StringRes val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
)

//// List of recipes and the information that you will use as the data in your app.
//val recipes = listOf(
//    Recipe(R.string.recipe_title_1, R.string.recipe_description_1),
//    Recipe(R.string.recipe_title_2, R.string.recipe_description_2),
//    Recipe(R.string.recipe_title_3, R.string.recipe_description_3)
//)