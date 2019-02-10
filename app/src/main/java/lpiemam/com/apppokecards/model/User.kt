package lpiemam.com.apppokecards.model

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.*

class User(val firstName : String, val lastName : String, val nickName : String, val email : String, val url : String){
    lateinit var userCardList : ArrayList<Card>
    var dateLastQuizzEnded : Calendar? = null
    var coins : Int = 0
    var dusts : Int = 0

    fun buyAPack(pack: CardsPack, view: View) {
        userCardList.addAll(pack.listCards)
        userCardList = ArrayList(userCardList.sortedWith(compareBy{it.pokemon.pokedexNumber}))
        coins -= pack.costPack
    }

    fun canBuyAPack(pack: CardsPack) : Boolean {
        return coins >= pack.costPack
    }
}