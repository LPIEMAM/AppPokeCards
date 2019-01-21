package lpiemam.com.apppokecards.model

class Card(val pokemonName : String, val pokedexNumber : Int, val url : String, val type : String, val description : String) {

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