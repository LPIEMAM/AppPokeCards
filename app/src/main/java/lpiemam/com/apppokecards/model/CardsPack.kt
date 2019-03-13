package lpiemam.com.apppokecards.model

/**
 * Created by lpiem on 02/02/2019.
 */
class CardsPack(val name : String, var listPokemonCards : ArrayList<PokemonCard> = ArrayList(), var isSelected : Boolean = false) {
    val nbCards = when (name) {
        "Petit" -> 3
        "Moyen" -> 6
        "Grand" -> 9
        else -> 0
    }

    val costPack = when (name) {
        "Petit" -> 480
        "Moyen" -> 780
        "Grand" -> 1480
        else -> 0
    }

    fun clearCardList() {
        listPokemonCards.clear()
    }

}