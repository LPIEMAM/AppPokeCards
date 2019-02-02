package lpiemam.com.apppokecards.model

/**
 * Created by lpiem on 02/02/2019.
 */
class CardsPack(val name : String, val listCards : ArrayList<Card>) {
    val nbCards = when (name) {
        "Petit" -> 4
        "Moyen" -> 9
        "Grand" -> 14
        else -> 0
    }

    val costPack = when (name) {
        "Petit" -> 480
        "Moyen" -> 780
        "Grand" -> 1480
        else -> 0
    }
}