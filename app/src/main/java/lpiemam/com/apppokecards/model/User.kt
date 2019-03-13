package lpiemam.com.apppokecards.model

import androidx.room.Entity
import java.util.*

@Entity
class User (var firstName : String?,
            var lastName : String?,
            var nickName : String?,
            var email : String?,
            var dateLastQuizzEnded : Calendar?,
            var coins : Int = 0,
            var dusts : Int = 0) {


    fun canBuyAPack(pack: CardsPack) : Boolean {
        return coins >= pack.costPack
    }
}

object UserManager {
    var user : User? = null

    /*fun setUpUser(firstName: String, lastName: String, nickName: String, email:String, coins: Int, dusts: Int ) {
        user?.firstName = firstName
        user?.lastName = lastName
        user?.nickName = nickName
        user?.email = email
        user?.coins = coins
        user?.dusts = dusts
    }*/



}