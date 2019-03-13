package lpiemam.com.apppokecards.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserCard (val pokemonCard: PokemonCard) : Parcelable {
    @Transient
    var numberOfCard : Int = 1

}