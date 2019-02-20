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

    fun generateRandomCards(allPokemonCards : ArrayList<PokemonCard>) {
        listPokemonCards.clear()
        if(nbCards <= allPokemonCards.size) {
            val usedPositions = ArrayList<Int>()
            for(i in 1..nbCards) {
                var randomPosition = (0..(allPokemonCards.size-1)).random()

                listPokemonCards.add(allPokemonCards[randomPosition])
                usedPositions.add(randomPosition)
            }
        }

        /*if(nbCards <= PokemonCardsViewModel.allCardsUserNeeds.size) {
            val usedPositions = ArrayList<Int>()
            for(i in 1..nbCards) {
                var randomPosition = (0..(PokemonCardsViewModel.allCardsUserNeeds.size-1)).random()
                while(usedPositions.contains(randomPosition)) {
                    randomPosition = (0..(PokemonCardsViewModel.allCardsUserNeeds.size-1)).random()
                }
                listPokemonCards.add(PokemonCardsViewModel.allCardsUserNeeds[randomPosition])
                usedPositions.add(randomPosition)
            }
        } else {
            throw Exception("Not Enough Cards")
        }*/
    }

    fun clearCardList() {
        listPokemonCards.clear()
    }

}