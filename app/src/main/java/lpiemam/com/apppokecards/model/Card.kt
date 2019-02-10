package lpiemam.com.apppokecards.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Card(val pokemon: Pokemon, val url : String, val description : String, val version : String) : Parcelable {
    val costDustToCraft : Int = 650
    val dustGivenByDecraft : Int = 400
}