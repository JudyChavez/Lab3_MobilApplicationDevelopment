package com.example.recipeexplorer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


//RecipeViewModel class will manage the state of the UI by holding and updating the RecipeUiState through the _uiState variable.
class RecipeViewModel : ViewModel() {

    //Recipe UI state
    private val _uiState = // RecipeUiState() is a data class in RecipeUiState.kt.
        MutableStateFlow(RecipeUiState()) //Backing property to uiState to avoid state updates from other classes

    val uiState: StateFlow<RecipeUiState> = //asStateFlow() converts MutableStateFlow to a StateFlow.
        _uiState.asStateFlow() // Expose as immutable StateFlow. asStateFlow() makes this mutable state flow a read-only state flow.

    init {
        loadRecipes() //load recipes
    }

    private fun loadRecipes() {
        _uiState.value = _uiState.value.copy(
            recipes = Datasource().loadRecipes() //update recipes UI state
        )
    }

    //Use mutableStateOf() so that Compose observes this value and sets the initial value to "".
    //Compose observes this value and sets the initial value to "".
    //This will be an observable state that Compose will track for changes.
    // The private setter ensures that the UI can read the selection but cannot modify it directly from outside the ViewModel.
    var userSelection by mutableStateOf("")
        private set


}

//// ViewModel for Recipe UI
//class RecipeViewModel : ViewModel() {
//
//    // Backing property for the UI state (mutable)
//    private val _uiState = MutableStateFlow(RecipeUiState()) // Initially an empty list of recipes
//    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow() // Exposed as read-only StateFlow
//
//    private val datasource = Datasource()
//
//    // Function to load recipes from Datasource and update the state
//    fun loadRecipes() {
//        // Get the recipes from the Datasource (could be static or dynamic)
//        val recipes: List<Recipe> = datasource.loadRecipes()
//
//        // Update the state with the recipes
//        _uiState.value = RecipeUiState(
//            recipes = recipes // Set the recipes in the state
//        )
//    }
//}