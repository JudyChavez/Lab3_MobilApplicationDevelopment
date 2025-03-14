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
import kotlinx.coroutines.flow.update


//RecipeViewModel class will manage the state of the UI by holding and updating the RecipeUiState through the _uiState variable.
//manages state of recipes list and selectedRecipe.
class RecipeViewModel : ViewModel() {

    //Recipe UI state, holds state of recipe list
    private val _uiState = // RecipeUiState() is a data class in RecipeUiState.kt.
        MutableStateFlow(RecipeUiState()) //Backing property to uiState to avoid state updates from other classes
    val uiState: StateFlow<RecipeUiState> = //asStateFlow() converts MutableStateFlow to a StateFlow.
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

//    // Updates the selected recipe in the UI state and reflects the change
//    fun selectRecipe(recipe: Recipe?) {
//        _uiState.update {
//            it.copy(selectedRecipe = recipe) // Update the selected recipe in the state
//        }
//    }









    //Holds the currently selected recipe
    private val _selectedRecipe = mutableStateOf<Recipe?>(null)
    val selectedRecipe: Recipe? get() = _selectedRecipe.value
    // Update the selected recipe and reflect the change in the UI state.
    fun updateCurrentRecipeDetailScreenState(recipe: Recipe) {
        _uiState.update {
            it.copy(
                selectedRecipe = recipe //updates the selected recipe
            )
        }
    }

    //updates selected recipe when clicked
    fun selectRecipe(recipe: Recipe?) {
        _selectedRecipe.value = recipe
    }
//    fun selectRecipe(recipe: Recipe?) {
//        _uiState.update { it.copy(selectedRecipe = recipe) }
//    }




















   /*
    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }


    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }
    */

    //Use mutableStateOf() so that Compose observes this value and sets the initial value to "".
    //Compose observes this value and sets the initial value to "".
    //This will be an observable state that Compose will track for changes.
    // The private setter ensures that the UI can read the selection but cannot modify it directly from outside the ViewModel.
//    var selectedRecipes by mutableStateOf("")
//        private set
}

//}