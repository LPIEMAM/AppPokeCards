package lpiemam.com.apppokecards.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by lpiem on 21/01/2019.
 */
@Parcelize
class Pokemon(val name: String, val pokedexNumber: Int, val type: String) : Parcelable {

    val generation: String = when (pokedexNumber) {
        in 1..151 -> "Première génération"
        in 152..251 -> "Seconde génération"
        in 252..386 -> "Troisime génération"
        in 387..493 -> "Quatrième génération"
        else -> {
            ""
        }
    }


}