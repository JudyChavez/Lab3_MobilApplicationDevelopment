package com.example.recipeexplorer.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.runtime.State


//RecipeViewModel class will manage the state of the UI by holding and updating the RecipeUiState through the _uiState variable.
//manages state of recipes list and selectedRecipe.
class RecipeViewModel : ViewModel() {

    //Recipe UI state, holds state of recipe list
    private val _uiState = // RecipeUiState() is a data class in RecipeUiState.kt.
        MutableStateFlow(RecipeUiState()) //Backing property to uiState to avoid state updates from other classes
    val uiState: StateFlow<RecipeUiState> =
        //asStateFlow() converts MutableStateFlow to a StateFlow.
        _uiState.asStateFlow() // Expose as immutable StateFlow. asStateFlow() makes this mutable state flow a read-only state flow.

    init {
        initializeUiState() //load recipes
    }

    //load recipes list from Datasource
    private fun initializeUiState() {
        _uiState.value = _uiState.value.copy(
            recipes = Datasource().loadRecipes(), //update list of recipes UI state //returns a listOf<Recipe>
            selectedRecipe = null //initializes selectedRecipe as null
        )
    }

    // Updates the selected recipe in the UI state and reflects the change
    fun selectRecipe(recipe: Recipe?) {
        _uiState.update {
            it.copy(selectedRecipe = recipe) // Update the selected recipe in the state
        }
    }

    // State to store the scroll position
    private val _scrollPosition = mutableStateOf(0)
    val scrollPosition: State<Int> = _scrollPosition

    // Function to update the scroll position
    fun updateScrollPosition(position: Int) {
        _scrollPosition.value = position
    }
}






















