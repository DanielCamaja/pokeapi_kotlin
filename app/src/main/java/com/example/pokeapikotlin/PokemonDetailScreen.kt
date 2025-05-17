package com.example.pokeapikotlin.ui.screen

import StatBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokeapikotlin.model.Pokemon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(pokemonId: Int, navController: NavController) {
    var pokemon by remember { mutableStateOf<Pokemon?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                pokemon =
                    RetrofitInstance.api.getPokemonDetails("https://pokeapi.co/api/v2/pokemon/$pokemonId")
                isLoading = false
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = pokemon?.name?.replaceFirstChar { it.uppercase() } ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            pokemon?.let { pkmn ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    val imageUrl = pkmn.sprites.other?.home?.frontDefault
                    if (imageUrl != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = pkmn.name,
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    Text(
                        text = pkmn.name.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Altura: ${pkmn.height / 10.0} m",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Peso: ${pkmn.weight / 10.0} kg",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Tipos",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row {
                        pkmn.types.forEach { type ->
                            Text(
                                text = type.type.name.uppercase(),
                                color = Color.White,
                                modifier = Modifier
                                    .padding(end = 8.dp, top = 4.dp, bottom = 4.dp)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Estadísticas",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    pkmn.stats.forEach { stat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Nombre del stat
                            Text(
                                text = stat.stat.name.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.width(80.dp)
                            )

                            // Barra de progreso
                            StatBar(
                                value = stat.base_stat,
                                maxValue = 100,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(20.dp)
                                    .padding(horizontal = 8.dp)
                            )

                            // Valor numérico
                            Text(
                                text = "${stat.base_stat}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }


                }
            }
        }
    }
}