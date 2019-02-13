package lpiemam.com.apppokecards.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Card(val pokemon: Pokemon, val url : String, val description : String, val version : String) : Parcelable {
    val costDustToCraft : Int = 650
    val dustGivenByDecraft : Int = 400

    fun isCardInArray(array : ArrayList<UserCard>) : Boolean {
        var isInArray = false
        for (userCard in array) {
            if(userCard.card == this) {
                isInArray = true
            }
        }
        return isInArray
    }

    fun getInstanceOfUserCard(array: ArrayList<UserCard>) : UserCard {
        var userCardInstance : UserCard? = null
        for (userCard in array) {
            if(userCard.card == this) {
                userCardInstance = userCard
            }
        }
        return userCardInstance!!
    }
}