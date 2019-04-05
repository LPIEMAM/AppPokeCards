package lpiemam.com.apppokecards.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class UserCard() : Parcelable, Comparable<UserCard> {


    @SerializedName("id")
    var userCardID: Int = 0
    @SerializedName("iduser")
    var userId: Int = 0
    lateinit var pokemonCard: PokemonCard
    @SerializedName("nbExemplaire")
    var numberOfCard: Int = 1
    var name: String = ""

    constructor(parcel: Parcel) : this() {
        userCardID = parcel.readInt()
        pokemonCard = parcel.readParcelable(PokemonCard::class.java.classLoader)!!
        numberOfCard = parcel.readInt()
    }

    constructor(pokemonCard: PokemonCard) : this() {
        this.pokemonCard = pokemonCard
        this.name = pokemonCard.name
        this.userId = UserManager.user!!.userId
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userCardID)
        parcel.writeParcelable(pokemonCard, flags)
        parcel.writeInt(numberOfCard)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserCard> {
        override fun createFromParcel(parcel: Parcel): UserCard {
            return UserCard(parcel)
        }

        override fun newArray(size: Int): Array<UserCard?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: UserCard): Int {

        return when {
            other.pokemonCard.nationalPokedexNumber == null && this.pokemonCard.nationalPokedexNumber != null -> -1
            this.pokemonCard.nationalPokedexNumber == null && other.pokemonCard.nationalPokedexNumber != null-> 1
            other.pokemonCard.nationalPokedexNumber == null && this.pokemonCard.nationalPokedexNumber == null -> this.pokemonCard.supertype?.compareTo(other.pokemonCard.supertype!!)!!
            else -> this.pokemonCard.nationalPokedexNumber!!.compareTo(other.pokemonCard.nationalPokedexNumber!!)
        }
    }

    override fun toString(): String {
        return "UserCard(userCardID=$userCardID, pokemonCard=$pokemonCard, numberOfCard=$numberOfCard)"
    }


}