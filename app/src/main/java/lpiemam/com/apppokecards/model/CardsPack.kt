package lpiemam.com.apppokecards.model

import lpiemam.com.apppokecards.viewmodel.ViewModelPokemon

/**
 * Created by lpiem on 02/02/2019.
 */
class CardsPack(val name : String, var listCards : ArrayList<Card> = ArrayList(), var isSelected : Boolean = false) {
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

    fun generateRandomCards(allCards : ArrayList<Card>) {
        listCards.clear()
        if(nbCards <= allCards.size) {
            val usedPositions = ArrayList<Int>()
            for(i in 1..nbCards) {
                var randomPosition = (0..(allCards.size-1)).random()

                listCards.add(allCards[randomPosition])
                usedPositions.add(randomPosition)
            }
        }

        /*if(nbCards <= ViewModelPokemon.allCardsUserNeeds.size) {
            val usedPositions = ArrayList<Int>()
            for(i in 1..nbCards) {
                var randomPosition = (0..(ViewModelPokemon.allCardsUserNeeds.size-1)).random()
                while(usedPositions.contains(randomPosition)) {
                    randomPosition = (0..(ViewModelPokemon.allCardsUserNeeds.size-1)).random()
                }
                listCards.add(ViewModelPokemon.allCardsUserNeeds[randomPosition])
                usedPositions.add(randomPosition)
            }
        } else {
            throw Exception("Not Enough Cards")
        }*/
    }

    fun clearCardList() {
        listCards.clear()
    }

}