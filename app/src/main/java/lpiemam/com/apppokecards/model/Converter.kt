package lpiemam.com.apppokecards.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun listToPokemonCard(list: List<PokemonCard>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<PokemonCard>>() {
        }.getType()
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun intListToString(list: List<Int>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {
        }.getType()
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToIntList(countryLangString: String?): List<Int>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {
        }.type
        return gson.fromJson(countryLangString, type)
    }

    @TypeConverter
    fun stringListToString(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {
        }.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToStringList(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {
        }.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun pokemonAttackListToString(list: ArrayList<PokemonAttack>) : String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ArrayList<PokemonAttack>>() {

        }.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToPokemonAttackList(value: String?) : ArrayList<PokemonAttack>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<PokemonAttack>>() {
        }.type
        return gson.fromJson(value, type)
    }

}