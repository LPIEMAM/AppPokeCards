package lpiemam.com.apppokecards.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class UserCard() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var userCardID: Int = 0
    lateinit var pokemonCard: PokemonCard
    var numberOfCard: Int = 1

    constructor(parcel: Parcel) : this() {
        userCardID = parcel.readInt()
        pokemonCard = parcel.readParcelable(PokemonCard::class.java.classLoader)!!
        numberOfCard = parcel.readInt()
    }

    constructor(pokemonCard: PokemonCard) : this() {
        this.pokemonCard = pokemonCard
    }

    @Ignore
    constructor(userCardId: Int, pokemonCard: PokemonCard, number: Int) : this(pokemonCard) {
        this.userCardID = userCardId
        this.pokemonCard = pokemonCard
        this.numberOfCard = number
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

}