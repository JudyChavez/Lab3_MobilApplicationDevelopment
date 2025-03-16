package com.example.recipeexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipeexplorer.ui.RecipeExplorerApp
import com.example.recipeexplorer.ui.theme.RecipeExplorerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeExplorerTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier
                        .padding(
                            //The WindowInsets API helps manage system UI elements
                            // like the status bar, navigation bar, etc.
                            // The code here ensures that the app adjusts its padding based on safe areas.
                            start = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateEndPadding(layoutDirection)
                        )
                ) {
                    val windowSize = calculateWindowSizeClass(this) //calculates the size of windowSize
                    RecipeExplorerApp(
                        windowSize = windowSize.widthSizeClass //only the width of windowSize, type: WindowWidthSizeClass
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RecipeExplorerAppCompactPreview() {
    RecipeExplorerTheme {
        RecipeExplorerApp(
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(showBackground = true, widthDp = 600)
@Composable
fun RecipeExplorerAppMediumPreview() {
    RecipeExplorerTheme {
        RecipeExplorerApp(
            windowSize = WindowWidthSizeClass.Medium
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun RecipeExplorerAppExpandedPreview() {
    RecipeExplorerTheme {
        RecipeExplorerApp(
            windowSize = WindowWidthSizeClass.Expanded
        )
    }
}