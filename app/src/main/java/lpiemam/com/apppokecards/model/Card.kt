package lpiemam.com.apppokecards.model

class Card(private val pokemonName : String, private val pokedexNumber : Int, private val url : String, private val type : String, private val description : String) {
    val version : String = when(pokedexNumber) {
        in 1..151 -> "Première génération"
        in 152..251 -> "Seconde génération"
        in 252..386 -> "Troisime génération"
        in 387..493 -> "Quatrième génération"
        else -> {
            ""
        }
    }
}