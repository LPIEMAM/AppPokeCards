package lpiemam.com.apppokecards.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class UserCard (val pokemonCard: PokemonCard) : Parcelable {
    @Transient
    var numberOfCard : Int = 1

}