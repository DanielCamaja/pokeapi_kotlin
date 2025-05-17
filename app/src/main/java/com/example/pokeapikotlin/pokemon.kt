package com.example.pokeapikotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val sprites: Sprites,
    val stats: List<StatWrapper>,
    val types: List<PokeType>
) : Serializable

data class Sprites(
    val other: Other?
) : Serializable

data class Other(
    val home: Home?
) : Serializable

data class Home(
    @SerializedName("front_default")
    val frontDefault: String?
) : Serializable

data class StatWrapper(
    val stat: StatName,
    val base_stat: Int
) : Serializable

data class StatName(
    val name: String
) : Serializable

data class PokeType(
    val type: TypeName
) : Serializable

data class TypeName(
    val name: String
) : Serializable
