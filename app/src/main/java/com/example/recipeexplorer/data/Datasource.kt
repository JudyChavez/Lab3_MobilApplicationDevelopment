package com.example.recipeexplorer.data

import com.example.recipeexplorer.R
import com.example.recipeexplorer.model.Recipe

class Datasource() {
    //loadRecipes() method gathers all of the data provided and returns it as a list.
    fun loadRecipes(): List<Recipe> {
        return listOf<Recipe>(
            Recipe(R.string.recipe_title_1, R.string.recipe_description_1),
            Recipe(R.string.recipe_title_2, R.string.recipe_description_2),
            Recipe(R.string.recipe_title_3, R.string.recipe_description_3),

            Recipe(R.string.recipe_title_4, R.string.recipe_description_4),
            Recipe(R.string.recipe_title_5, R.string.recipe_description_5),
            Recipe(R.string.recipe_title_6, R.string.recipe_description_6),


            Recipe(R.string.recipe_title_1, R.string.recipe_description_1),
            Recipe(R.string.recipe_title_2, R.string.recipe_description_2),
            Recipe(R.string.recipe_title_3, R.string.recipe_description_3),

            Recipe(R.string.recipe_title_4, R.string.recipe_description_4),
            Recipe(R.string.recipe_title_5, R.string.recipe_description_5),
            Recipe(R.string.recipe_title_6, R.string.recipe_description_6)
        )
    }
}
