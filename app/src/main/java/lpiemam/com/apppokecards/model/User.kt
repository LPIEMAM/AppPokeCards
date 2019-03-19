package lpiemam.com.apppokecards.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import lpiemam.com.apppokecards.retrofit.Converter
import java.util.*

@Entity
class User() {

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 1
    var firstName: String? = ""
    var lastName: String? = ""
    @NonNull
    var nickName: String = ""
    var email: String? = ""
    @TypeConverters(Converter::class)
    var dateLastQuizzEnded: Calendar? = null
    var coins: Int = 0
    var dusts: Int = 0

    fun canBuyAPack(pack: CardsPack): Boolean {
        return coins >= pack.costPack
    }

    @Ignore
    constructor(
        firstName: String,
        lastName: String,
        nickName: String,
        email: String,
        dateLastQuizzEnded: Calendar?,
        coins: Int,
        dusts: Int
    ) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.nickName = nickName
        this.email = email
        this.dateLastQuizzEnded = dateLastQuizzEnded
        this.coins = coins
        this.dusts = dusts
    }

}

object UserManager {
    var user: User? = null
}