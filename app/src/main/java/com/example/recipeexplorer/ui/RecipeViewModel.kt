package com.example.recipeexplorer.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RecipeViewModel : ViewModel() {

    // Recipe UI state
    private val _uiState = MutableStateFlow(RecipeUiState())
}