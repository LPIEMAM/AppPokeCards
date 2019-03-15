package lpiemam.com.apppokecards.model

import android.os.Parcelable
import android.view.inputmethod.InputMethodSubtype
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity
data class PokemonCard(val id: String,
                  val name: String,
                  val nationalPokedexNumber: Int,
                  val hp: String,
                  val imageUrlHiRes: String,
                       @TypeConverters(Converter::class)
                  val types: ArrayList<String>,
                  val number: String,
                  val subtype: String,
                  val supertype: String,
                       @TypeConverters(Converter::class)
                  val attacks: ArrayList<PokemonAttack>,
//                       @TypeConverters(Converter::class)
//                  val text: ArrayList<String>,
//                       @TypeConverters(Converter::class)
//                  val weaknesses: ArrayList<PokemonWeaknesses>,
//                       @TypeConverters(Converter::class)
//                  val resistances: ArrayList<PokemonResistances>,
//                       @TypeConverters(Converter::class)
//                  val retreatCost: ArrayList<String>,
                  val convertedRetreatCost: Int,
                  val artist: String,
                  val set: String,
                  val setCode: String,
                  val rarity: String
) : Parcelable {


    fun isCardInArray(array : ArrayList<UserCard>) : Boolean {
        var isInArray = false
        for (userCard in array) {
            if(userCard.pokemonCard.id == this.id) {
                isInArray = true
            }
        }
        return isInArray
    }

    fun getInstanceOfUserCard(array: ArrayList<UserCard>) : UserCard {
        var userCardInstance : UserCard? = null
        for (userCard in array) {
            if(userCard.pokemonCard.id == this.id) {
                userCardInstance = userCard
            }
        }
        return userCardInstance!!
    }

    fun getCostToCraft() : Int {
        return when(rarity) {
            "", "Common" -> 400
            "Uncommon" -> 500
            "Rare" -> 600
            "Rare Holo" -> 700
            "Rare Holo EX" -> 800
            "Rare Ultra" -> 1000
            else -> 400
        }
    }

    fun getCostForDecraft() : Int {
        return when(rarity) {
            "", "Common" -> 200
            "Uncommon" -> 300
            "Rare" -> 400
            "Rare Holo" -> 500
            "Rare Holo EX" -> 600
            "Rare Ultra" -> 800
            else -> 200
        }
    }

}