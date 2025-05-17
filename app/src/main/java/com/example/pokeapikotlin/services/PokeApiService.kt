import com.example.pokeapikotlin.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 151
    ): PokemonListResponse

    @GET
    suspend fun getPokemonDetails(@Url url: String): Pokemon
}

data class PokemonListResponse(
    val results: List<NamedAPIResource>
)

data class NamedAPIResource(
    val name: String,
    val url: String
)
