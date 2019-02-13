package lpiemam.com.apppokecards.model

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.*

class User(val firstName : String, val lastName : String, val nickName : String, val email : String, val url : String){
    lateinit var userCardList : ArrayList<UserCard>
    var dateLastQuizzEnded : Calendar? = null
    var coins : Int = 0
    var dusts : Int = 0

    fun buyAPack(pack: CardsPack, view: View) {
        for(card in pack.listCards) {

            if(card.isCardInArray(userCardList)) {
                var userCard = card.getInstanceOfUserCard(userCardList)
                userCard.numberOfCard++
            } else {
                userCardList.add(UserCard(card))
            }
        }
        userCardList = ArrayList(userCardList.sortedWith(compareBy{it.card.pokemon.pokedexNumber}))
        coins -= pack.costPack
    }

    fun canBuyAPack(pack: CardsPack) : Boolean {
        return coins >= pack.costPack
    }
}