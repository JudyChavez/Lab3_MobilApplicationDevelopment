package com.example.recipeexplorer.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeexplorer.R
import com.example.recipeexplorer.data.Datasource
import com.example.recipeexplorer.model.Recipe


//RecipeListScreen() – Displays the list of recipes.


@Composable
fun RecipeListScreen(
    navController: NavHostController, //we add this since this screens contains the card that will navigate to the RecipeDetailScreen.
    modifier: Modifier = Modifier
) {
    //call the RecipeList composable, and pass DataSource().loadRecipes() to the recipeList parameter.
    val recipeList = Datasource().loadRecipes()

    Scaffold(
        topBar = {
            RecipeListScreenTopAppBar()
        }
    ) { it -> //it is paddingValues
        LazyColumn(contentPadding = it) {
            //items() method is how you add items to the LazyColumn.
            //for each recipe in the list, call the RecipeCard() composable.
            items(recipeList) { it -> //{ it is recipe
                RecipeCard(
                    navController = navController,
                    recipe = it, //recipe
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .clickable {
                            // Navigate to the RecipeDetailScreen and pass the recipeId
                            navController.navigate("recipe_detail/${it.id}") //it is recipe
                        }
                )
            }
        }
    }
}

//each individual recipe card, it contains: title and description
@Composable
fun RecipeCard(
    navController: NavController,
    recipe: Recipe,
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

@Composable
fun Navigation() {
    //we use navController to control our navHost
    // we can get that by rememberNavController.
    //We can use that to navigate where we want, pass arguments if we and others.
    val navController = rememberNavController() //Initialize the NavController

    //NavHost() composable needs the navController
    //NavHost takes a navController to listen to changes and commands from the navController.
    //It also takes a startDestination.
    NavHost(
        navController = navController,
        startDestination = Screen.RecipeList.name   /*"recipe_list_screen"*/,
    ) {
        composable(route = Screen.RecipeList.name
        ) {
            RecipeListScreen(navController = navController)
        }
        composable(route = "recipe_detail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0
            RecipeDetailScreen(recipeId = recipeId, navController = navController)
        }
    }
}


//    ) {
//        composable(route = Screen.RecipeList.name /*"recipe_list_screen"*/) {
//            RecipeListScreen(navController = navController) //Navigate to the RecipeListScreen
//        }
//        composable(route = Screen.RecipeDetail.name /*"detail_screen"*/) { backStackEntry ->
//            val context = LocalContext.current
//
//            // Get the recipeId argument from the navigation route
//            val recipeId = backStackEntry.arguments?.getInt("recipeId")?.toInt() ?: 0
//            // Navigate to RecipeDetailScreen and pass the recipeId
//            RecipeDetailScreen(recipeId = recipeId, navController = navController)
//        }
//    }
//}


//enum class to define the routes.
enum class Screen(@StringRes val title: Int) {
    RecipeList(title = R.string.recipe_list_screen_top_app_bar), //"Recipe List"
    RecipeDetail(title = R.string.recipe_detail_screen_top_app_bar) //"Recipe Detail..."
}






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




















