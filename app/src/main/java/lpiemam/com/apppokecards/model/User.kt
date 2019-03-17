package lpiemam.com.apppokecards.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
class User (var firstName : String?,
            var lastName : String?,
            @PrimaryKey
            @NonNull
            var nickName : String,
            var email : String?,
            @TypeConverters
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