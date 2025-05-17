import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapikotlin.model.Pokemon
import kotlinx.coroutines.launch

// PokemonListViewModel.kt
class PokemonListViewModel : ViewModel() {
    var pokemonList by mutableStateOf<List<Pokemon>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPokemonList(limit = 151)
                val detailedList = response.results.map { result ->
                    RetrofitInstance.api.getPokemonDetails(result.url)
                }
                pokemonList = detailedList
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
