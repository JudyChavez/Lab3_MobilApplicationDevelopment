package com.example.recipeexplorer.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipeexplorer.R
import com.example.recipeexplorer.model.Recipe
import com.example.recipeexplorer.ui.utils.RecipeExplorerContentType
import com.example.recipeexplorer.ui.utils.RecipeExplorerNavigationType

//RecipeListScreen() – Displays the list of recipes.

//enum class to define the routes. //Holds constants for the screen.
//The @StringRes annotation indicates that the title parameter is an integer resource ID that points to a string resource.
//The @StringRes annotation is used to specify that the title property should hold
// a reference to a string resource ID (an integer),
// which can later be used to retrieve a string from the strings.xml file in the Android project.
enum class Screen(@StringRes val title: Int) {
    //Each constant represents a different screen in the app.
    RecipeList(title = R.string.recipe_list_screen_top_app_bar), //"Recipe List"
    RecipeDetail(title = R.string.recipe_detail_screen_top_app_bar) //"Recipe Detail..."
}

@Composable
fun RecipeListScreen(
    navController: NavHostController, //we add this since this screens contains the card that will navigate to the RecipeDetailScreen.
    navigationType: RecipeExplorerNavigationType,
    contentType: RecipeExplorerContentType,
    recipeUiState: RecipeUiState,
    recipeViewModel: RecipeViewModel, // = viewModel(),
    windowSize: WindowWidthSizeClass,
    //onBackPressed: () -> Unit,
    selectedRecipe: Recipe?,
    modifier: Modifier = Modifier
) {
    // Retrieve the scroll position from the ViewModel
    val scrollPosition = recipeViewModel.scrollPosition.value
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)
    //Save the scroll position when the user scrolls
    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        recipeViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex) //saves the scroll position when the user scrolls.
    }
    Scaffold(
        topBar = {
            RecipeListScreenTopAppBar()
        }
    ) { it -> //"it" is paddingValues { paddingValues ->
        val displayList = recipeUiState.recipes
        LazyColumn(
            state = scrollState,
            contentPadding = it
        ) {
            //items() method is how you add items to the LazyColumn.
            //for each recipe in the list, call the RecipeCard() composable.
            items(displayList) { it -> //{ it is recipe { recipe ->
                RecipeCard(
                    navController = navController,
                    recipe = it, //recipe
                    recipeUiState = recipeUiState,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .clickable {
                            //update selected recipe in ViewModel
                            recipeViewModel.selectRecipe(it)
                            // Navigate to the RecipeDetailScreen and pass the recipeId
                            navController.navigate("${Screen.RecipeDetail.name}/${it.id}") //it is recipe
                        }
                )
            }
        }
    }
}

@Composable
fun RecipeListAndDetail(
    navController: NavHostController, //we add this since this screens contains the card that will navigate to the RecipeDetailScreen.
    navigationType: RecipeExplorerNavigationType,
    contentType: RecipeExplorerContentType,
    recipeUiState: RecipeUiState,
    recipeViewModel: RecipeViewModel,
    selectedRecipe: Recipe?,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val recipeList = recipeUiState.recipes//Datasource().loadRecipes()

    // Retrieve the scroll position from the ViewModel
    val scrollPosition = recipeViewModel.scrollPosition.value
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)
    //Save the scroll position when the user scrolls
    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        recipeViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex) //saves the scroll position when the user scrolls.
    }
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //RecipeListScreen made up of RecipeCard()s, on the left
        Column(modifier = Modifier.weight(1f)) {
            RecipeListScreenTopAppBar()
            LazyColumn(
                state = scrollState,
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                //items() method is how you add items to the LazyColumn.
                //for each recipe in the list, call the RecipeCard() composable.
                items(recipeList) { it -> //{ it is recipe on the list
                    RecipeCard(
                        navController = navController,
                        recipe = it, //recipe
                        recipeUiState = recipeUiState,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .clickable {
                                //When recipe card is clicked, update selectedRecipe in the ViewModel
                                recipeViewModel.selectRecipe(it)
                            }
                    )
                }
            }
        }
        //RecipeDetailScreen.kt, on the right.
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_medium)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            val currentRecipe = recipeUiState.selectedRecipe
            if (currentRecipe == null) {
                RecipeDetailScreen(
                    recipeId = null,//null, //recipeId, //Get recipe ID as a parameter.
                    recipeViewModel = recipeViewModel,
                    navController = navController,
                    recipeUiState = recipeUiState,
                    windowSize = windowSize,
                    onBackPressed = { navController.navigate(Screen.RecipeList.name) },
                    contentType = contentType,
                    modifier = modifier
                )
            } else {
                RecipeDetailScreen(
                    recipeId = currentRecipe.id, //Get recipe ID as a parameter.
                    recipeViewModel = recipeViewModel,
                    navController = navController,
                    recipeUiState = recipeUiState,
                    windowSize = windowSize,
                    onBackPressed = { navController.navigate(Screen.RecipeList.name) },
                    contentType = contentType,
                    modifier = modifier
                )
            }

        }
    }
}

    //each individual recipe card, it contains: title and description
    @Composable
    fun RecipeCard(
        navController: NavHostController,
        recipe: Recipe,
        recipeUiState: RecipeUiState,
        modifier: Modifier = Modifier
    ) {
        Card(modifier = modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = stringResource(id = recipe.title), // Use stringResource to load the string
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.displayLarge //Material3 typography
                )
                Text(
                    text = stringResource(id = recipe.description),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeListScreenTopAppBar(modifier: Modifier = Modifier) {
        CenterAlignedTopAppBar( //had to add to build.gradle.kts(Module :app): freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically //makes RecipeExplorerTopAppBar content center vertically.
                ) {
                    Text(
                        text = stringResource(R.string.recipe_list_screen_top_app_bar), //"Recipe List"
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            },
            modifier = modifier
        )
    }

    @SuppressLint("ContextCastToActivity")
    @Composable
    fun Navigation(
        navigationType: RecipeExplorerNavigationType,
        contentType: RecipeExplorerContentType,
        recipeUiState: RecipeUiState,
        recipeViewModel: RecipeViewModel,
        windowSize: WindowWidthSizeClass,
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        val selectedRecipe = recipeUiState.selectedRecipe
        //val backStackEntry by navController.currentBackStackEntryAsState()

        //NavHost() composable needs the navController
        //NavHost takes a navController to listen to changes and commands from the navController.
        if (contentType == RecipeExplorerContentType.LIST_AND_DETAIL) {
            RecipeListAndDetail(
                navController = navController,
                navigationType = navigationType,
                contentType = contentType,
                recipeUiState = recipeUiState,
                recipeViewModel = recipeViewModel,
                windowSize = windowSize,
                selectedRecipe = selectedRecipe,
                modifier = modifier//.weight(1f).fillMaxHeight()
            )

        } else {   //for Compact screens, Uses Navigation.

            // Dynamically determine the startDestination
            val startDestination =
                if (selectedRecipe == null) {
                    // If no recipe is selected start at RecipeListScreen
                    Screen.RecipeList.name
                } else {
                    // If a recipe is selected start at RecipeDetailScreen
                    "${Screen.RecipeDetail.name}/${selectedRecipe.id}"
                }
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier
            ) {
                //val selectedRecipe = recipeViewModel.selectedRecipe
                composable(
                    route = Screen.RecipeList.name
                ) {
                    //val context = LocalContext.current
                    RecipeListScreen(
                        navController = navController,
                        navigationType = navigationType,
                        contentType = contentType,
                        recipeUiState = recipeUiState,
                        recipeViewModel = recipeViewModel,//viewModel(),
                        windowSize = windowSize,
                        selectedRecipe = selectedRecipe,//recipeViewModel.selectedRecipe,
                        modifier = modifier//.fillMaxSize()
                    )
                }
                composable(
                    route = "${Screen.RecipeDetail.name}/{recipeId}",
                    arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val recipeId/*: Int */= backStackEntry.arguments?.getString("recipeId")?.toInt() ?: null
                    RecipeDetailScreen(
                        recipeId = recipeId,
                        navController = navController,
                        recipeViewModel = recipeViewModel,
                        recipeUiState = recipeUiState,
                        windowSize = windowSize,
                        //selectedRecipe = selectedRecipe,
                        onBackPressed = {
                            //recipeViewModel.selectRecipe(null)
                            //navController.navigate(Screen.RecipeList.name)
                        },
                        contentType = contentType,
                        modifier = modifier
                    )
                }
            }
        }
    }
