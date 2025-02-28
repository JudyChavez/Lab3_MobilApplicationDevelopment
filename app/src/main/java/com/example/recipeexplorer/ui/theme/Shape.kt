package com.example.recipeexplorer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(50.dp), //used to shape the dog image into a circle.
    medium = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp) //used for the Card, a Card by default is a medium component.
)