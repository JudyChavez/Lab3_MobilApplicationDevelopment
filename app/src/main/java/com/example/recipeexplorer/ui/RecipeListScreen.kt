package com.example.recipeexplorer.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe
import com.example.recipeexplorer.ui.utils.RecipeExplorerContentType
import com.example.recipeexplorer.ui.utils.RecipeExplorerNavigationType
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.meta.When

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
        // Remember the scroll state to retain the scroll position

        val displayList = recipeUiState.recipes//.drop(1)
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
                            //it.id?.let { id ->
                            navController.navigate("${Screen.RecipeDetail.name}/${it.id/*id*/}") //it is recipe, /*recipe_detail*/
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


    //Use selectedRecipe from the RecipeViewModel
    //val selectedRecipe = recipeViewModel.selectedRecipe //directly from RecipeViewModel

    //State to keep track of the selected recipe
    //val selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

//    val recipeId =
//        selectedRecipe//0//null//backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0
//    // find recipe with given recipeId
//    val recipe = Datasource().loadRecipes().find { it.id == recipeUiState.selectedRecipe?.id }

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
                // Create a list without the first item for display
                //val displayList = recipeList.drop(1)

                //items() method is how you add items to the LazyColumn.
                //for each recipe in the list, call the RecipeCard() composable.
                items(/*recipeUiState.recipes*/recipeList/*displayList*/) { it -> //{ it is recipe on the list
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
            /*val currentRecipe = recipeViewModel.selectedRecipe*/val currentRecipe = recipeUiState.selectedRecipe
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
                    recipeId = currentRecipe.id, //recipeId, //Get recipe ID as a parameter.
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
//                text = LocalContext.current.getString(recipe.title), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
                    text = stringResource(id = recipe.title), // Use stringResource to load the string
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.displayLarge //Material3 typography
                )
                Text(
//                text = LocalContext.current.getString(recipe.description), //LocalContext.current This retrieves the current context (e.g., the current Activity or Context within your composable).
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
        /*val selectedRecipe = recipeViewModel.selectedRecipe*/val selectedRecipe = recipeUiState.selectedRecipe
        val selectedRecipeUi = recipeUiState.selectedRecipe
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route


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
                startDestination = startDestination,///*startDestination,*/Screen.RecipeList.name,
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
                    route = "${Screen.RecipeDetail.name}/{recipeId}",//"recipe_detail/{recipeId}",
                    arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val recipeId/*: Int */= backStackEntry.arguments?.getString("recipeId")?.toInt() ?: null/*0*/
                    //val activity = LocalContext.current as Activity
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




/////////////////////////////////////////start testing//////////////////////////////////////////////
//@Composable
//fun RecipeListScreen(
//    navController: NavHostController, //we add this since this screens contains the card that will navigate to the RecipeDetailScreen.
//    navigationType: RecipeExplorerNavigationType,
//    contentType: RecipeExplorerContentType,
//    recipeUiState: RecipeUiState,
//    modifier: Modifier = Modifier
//) {
//    //call the RecipeList composable, and pass DataSource().loadRecipes() to the recipeList parameter.
//    val recipeList = Datasource().loadRecipes()
//
//    Scaffold(
//        topBar = {
//            RecipeListScreenTopAppBar()
//        }
//    ) { it -> //it is paddingValues { it ->
//        LazyColumn(contentPadding = it) {
//            //items() method is how you add items to the LazyColumn.
//            //for each recipe in the list, call the RecipeCard() composable.
//            items(recipeList) { it -> //{ it is recipe
//                RecipeCard(
//                    navController = navController,
//                    recipe = it, //recipe
//                    modifier = Modifier
//                        .padding(dimensionResource(R.dimen.padding_medium))
//                        .clickable {
//                            // Navigate to the RecipeDetailScreen and pass the recipeId
//                            navController.navigate("recipe_detail/${it.id}") //it is recipe
//                        }
//                )
//            }
//        }
//    }
//}
/////////////////////////////////////////start testing//////////////////////////////////////////////


//    //NavHost() composable needs the navController
//    //NavHost takes a navController to listen to changes and commands from the navController.
//    //It also takes a startDestination.
//    NavHost(
//        navController = navController,
//        startDestination = Screen.RecipeList.name,   /*"recipe_list_screen"*/
//        modifier = modifier
//    ) {
//        composable(route = Screen.RecipeList.name
//        ) {
//            RecipeListScreen(
//                navController = navController,
//                navigationType = navigationType,
//                contentType = contentType,
//                recipeUiState = recipeUiState,
//                modifier = modifier.fillMaxSize()
//            )
//        }
//        composable(route = "recipe_detail/{recipeId}") { backStackEntry ->
//            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0
//            RecipeDetailScreen(
//                recipeId = recipeId,
//                navController = navController,
//                modifier = Modifier.fillMaxSize()
//                )
//        }
//    }
//}











////sealed class is a class that only allows classes inside of it, or inside the same file to inherit from it.
////Each screen needs a route, so we define it.
//sealed class RScreen(val route: String) {
//    object ListScreen : Screen("list_screen") //RecipeListScreen inherits Screen() and takes in a route.
//    object DetailScreen : Screen(route = "detail_screen")
//}







//////////////////////////////////////////////EXAMPLE///////////////////////////////////////////////
//sealed class Screen(val route: String) {
//    object MainScreen : Screen("main_screen")
//    object DetailScreen : Screen("detail_screen")
//
//    //helper function
//    fun withArgs(vararg args: String): String {
//        return buildString {
//            append(route)
//            args.forEach { arg ->
//                append("/$arg")
//            }
//        }
//    }
//}
//@Composable
//fun Navigations() {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = Screen.MainScreen.route
//    ) {
//        composable(route = Screen.MainScreen.route) {
//            MainScreen(navController = navController)
//        }
//        composable(
//            route = Screen.DetailScreen.route + "/{name}", //for mandatory argument use "/{name}" and set nullable = false   //for a default value, (in this case Philipp) and set nullable = false use "?name={name}"
//            arguments = listOf(
//                navArgument("name") {
//                    type = NavType.StringType
//                    defaultValue = "Philipp"
//                    nullable = true
//                }
//            )
//        ) { entry ->
//            DetailScreen(name = entry.arguments?.getString("name"))
//        }
//    }
//}
//@Composable
//fun MainScreen(navController: NavController) { //needs an instance of our navController.
//    var text by remember {
//        mutableStateOf("")
//    }
//    Column(
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 50.dp)
//    ) {
//        TextField(
//            value = text, //value is something we need to create a state for. We create the state with: var text by remember
//            onValueChange = { //onValueChange gives us the new String
//                text = it //and now we update text with our new value string, which is it.
//            },
//            modifier = Modifier.fillMaxWidth() //this fills max width of parent, in this case Column.
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Button(
//            onClick = {
//                //navController.navigate(Screen.DetailScreen.route)    /*TODO*/
//                navController.navigate(Screen.DetailScreen.withArgs(text))  //using helper method  /*TODO*/ //
//            },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text(
//                text = "To DetailScreen"
//            )
//        }
//    }
//}
//@Composable
//fun DetailScreen(name: String?) { //when we navigate to it, we need to pass a name. Make it nullable with String?
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(text = "Hello, $name")
//    }
//}







//        NavHost(
//            navController = navController,
//            startDestination = RecipeScreen.RecipeList.name,
//            //modifier = Modifier.padding(it)
//        ) {
//            composable(route = RecipeScreen.RecipeList.name) {
//                RecipeListScreen(
//                    recipeViewModel = viewModel(),
//                    navController = navController
//                )
//            }
//            composable(route = RecipeScreen.RecipeDetail.name) {
//                // Get the recipeId from the route and pass it to RecipeDetailScreen
//                val recipeId = navController.currentBackStackEntry?.arguments?.getInt("recipeId") ?: 0
//                RecipeDetailScreen(
//                    recipeId = recipeId,
//                    recipeViewModel = viewModel(),
//                    navController = navController
//                )
//            }
//        }
//    }
//}




















