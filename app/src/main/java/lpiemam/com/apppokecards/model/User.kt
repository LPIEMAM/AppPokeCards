package lpiemam.com.apppokecards.model

import android.provider.ContactsContract
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.*

object User{
    lateinit var firstName : String
    lateinit var lastName : String
    lateinit var nickName : String
    lateinit var email : String
    var dateLastQuizzEnded : Calendar? = null
    var coins : Int = 0
    var dusts : Int = 0

    fun setUpUser(firstName: String, lastName: String, nickName: String, email:String, coins: Int, dusts: Int ) {
        this.firstName = firstName
        this.lastName = lastName
        this.nickName = nickName
        this.email = email
        this.coins = coins
        this.dusts = dusts

    }

    fun canBuyAPack(pack: CardsPack) : Boolean {
        return coins >= pack.costPack
    }
}