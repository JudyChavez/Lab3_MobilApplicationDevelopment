package com.example.recipeexplorer.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


//RecipeViewModel class will manage the state of the UI by holding and updating the RecipeUiState through the _uiState variable.
class RecipeViewModel : ViewModel() {

    //Recipe UI state
    private val _uiState = MutableStateFlow(RecipeUiState()) //Backing property to uiState to avoid state updates from other classes
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow() // Expose as immutable StateFlow. asStateFlow() makes this mutable state flow a read-only state flow.
}