import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokeapikotlin.ui.screen.PokemonDetailScreen
import com.example.pokeapikotlin.ui.screen.PokemonListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "list") {
        composable("list") {
            PokemonListScreen(navController)
        }

        composable("detail/{pokemonId}") { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toIntOrNull() ?: 0
            PokemonDetailScreen(pokemonId = pokemonId, navController = navController)
        }


    }
}
