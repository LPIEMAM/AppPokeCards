package lpiemam.com.apppokecards.model

import java.lang.Exception

/**
 * Created by lpiem on 02/02/2019.
 */
class CardsPack(val name : String, var listCards : ArrayList<Card> = ArrayList(), var isSelected : Boolean = false) {
    val nbCards = when (name) {
        "Petit" -> 5
        "Moyen" -> 9
        "Grand" -> 15
        else -> 0
    }

    val costPack = when (name) {
        "Petit" -> 480
        "Moyen" -> 780
        "Grand" -> 1480
        else -> 0
    }

    fun generateRandomCards() {
        listCards.clear()
        if(nbCards <= Manager.allCardsUserNeeds.size) {
            val usedPositions = ArrayList<Int>()
            for(i in 1..nbCards) {
                var randomPosition = (0..(Manager.allCardsUserNeeds.size-1)).random()
                while(usedPositions.contains(randomPosition)) {
                    randomPosition = (0..(Manager.allCardsUserNeeds.size-1)).random()
                }
                listCards.add(Manager.allCardsUserNeeds[randomPosition])
                usedPositions.add(randomPosition)
            }
        } else {
            throw Exception("Not Enough Cards")
        }
    }

    fun clearCardList() {
        listCards.clear()
    }

}