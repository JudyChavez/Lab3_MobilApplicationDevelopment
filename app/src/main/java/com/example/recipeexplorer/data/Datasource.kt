package com.example.recipeexplorer.data

import com.example.recipeexplorer.R
import com.example.recipeexplorer.model.Recipe



class Datasource() {
    var recipeIdCounter = 0 //initialize id counter when loading recipes.

    //loadRecipes() method gathers all of the data provided and returns it as a list.
    //uses R.String resource IDs.
    fun loadRecipes(): List<Recipe> {
        return listOf<Recipe>(
            Recipe(recipeIdCounter++, R.string.recipe_title_null, R.string.recipe_description_null),

            Recipe(recipeIdCounter++, R.string.recipe_title_1, R.string.recipe_description_1),
            Recipe(recipeIdCounter++, R.string.recipe_title_2, R.string.recipe_description_2),
            Recipe(recipeIdCounter++, R.string.recipe_title_3, R.string.recipe_description_3),

            Recipe(recipeIdCounter++, R.string.recipe_title_4, R.string.recipe_description_4),
            Recipe(recipeIdCounter++, R.string.recipe_title_5, R.string.recipe_description_5),
            Recipe(recipeIdCounter++, R.string.recipe_title_6, R.string.recipe_description_6),
            Recipe(recipeIdCounter++, R.string.recipe_title_7, R.string.recipe_description_7),
            Recipe(recipeIdCounter++, R.string.recipe_title_8, R.string.recipe_description_8),
            Recipe(recipeIdCounter++, R.string.recipe_title_9, R.string.recipe_description_9),
            Recipe(recipeIdCounter++, R.string.recipe_title_10, R.string.recipe_description_10),
            Recipe(recipeIdCounter++, R.string.recipe_title_11, R.string.recipe_description_11),
            Recipe(recipeIdCounter++, R.string.recipe_title_12, R.string.recipe_description_12)
        )
    }
}
