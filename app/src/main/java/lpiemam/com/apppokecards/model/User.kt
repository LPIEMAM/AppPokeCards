package lpiemam.com.apppokecards.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import java.util.*

class User() {

    @SerializedName("id")
    var userId: Int = 1
    @SerializedName("firstname")
    var firstName: String? = ""
    @SerializedName("lastname")
    var lastName: String? = ""
    @SerializedName("nickname")
    var nickName: String = ""
    @SerializedName("email")
    var email: String? = ""
//    @TypeConverters(Converter::class)
    //var dateLastQuizzEnded: Calendar? = null

    @SerializedName("datelastquizz")
    var dateLastQuizzEndedDate: Date? = null
    @SerializedName("gold")
    var coins: Int = 0
    @SerializedName("dust")
    var dusts: Int = 0

    fun canBuyAPack(pack: CardsPack): Boolean {
        return coins >= pack.costPack
    }

    override fun toString(): String {
        return nickName
    }

    @Ignore
    constructor(
        firstName: String,
        lastName: String,
        nickName: String,
        email: String,
//        dateLastQuizzEnded: Calendar?,
        dateLastQuizzEndedDate: Date,
        coins: Int,
        dusts: Int
    ) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.nickName = nickName
        this.email = email
//        this.dateLastQuizzEnded = dateLastQuizzEnded
        this.dateLastQuizzEndedDate = dateLastQuizzEndedDate
        this.coins = coins
        this.dusts = dusts
    }


}

object UserManager {
    var loggedUser: User? = null
}