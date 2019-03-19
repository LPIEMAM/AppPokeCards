package lpiemam.com.apppokecards

import lpiemam.com.apppokecards.model.CardsPack

object Utils {

    val cardsPackList = ArrayList<CardsPack>()

    init {

        var petitPack = CardsPack("Petit")
        var moyenPack = CardsPack("Moyen")
        var grandPack = CardsPack("Grand")

        cardsPackList.add(petitPack)
        cardsPackList.add(moyenPack)
        cardsPackList.add(grandPack)
    }
}

