package lpiemam.com.apppokecards.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class UserCard(): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var userCardID : Int = -1
    @TypeConverters(Converter ::class)
    lateinit var pokemonCard: PokemonCard

    var numberOfCard : Int = 1

    constructor(pokemonCard: PokemonCard) : this() {
        this.pokemonCard = pokemonCard
    }

    constructor(userCardId: Int, pokemonCard: PokemonCard, number: Int ) : this() {
        this.userCardID = userCardId
        this.pokemonCard = pokemonCard
        this.numberOfCard = number
    }

}