package lpiemam.com.apppokecards.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonResistances(
    val type: String,
    val value: String
) : Parcelable