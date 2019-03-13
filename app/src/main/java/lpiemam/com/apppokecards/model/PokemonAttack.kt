package lpiemam.com.apppokecards.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonAttack(
    val cost: ArrayList<String>,
    val name: String,
    val text: String,
    val damage: String,
    val convertedEnergyCost: Int
) : Parcelable