package lpiemam.com.apppokecards.retrofit

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lpiemam.com.apppokecards.model.PokemonCard
import java.util.*


class Converter {

    @TypeConverter
    fun pokemonCardToString(pokemonCard: PokemonCard?): String? {
        if (pokemonCard == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<PokemonCard>() {

        }.type
        return gson.toJson(pokemonCard, type)
    }

    @TypeConverter
    fun stringToPokemonCard(string: String?): PokemonCard? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<PokemonCard>() {
        }.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun timeStampToCalendar(timestamp: Long?): Calendar? {
        if (timestamp == null) {
            return null
        } else {
            val returnValue = Calendar.getInstance()
            returnValue.timeInMillis = timestamp
            return returnValue
        }
    }

    @TypeConverter
    fun calendarToTimeStamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }


}