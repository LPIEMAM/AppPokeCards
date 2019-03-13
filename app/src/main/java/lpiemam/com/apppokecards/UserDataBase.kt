package lpiemam.com.apppokecards

import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserDAO
import retrofit2.Converter


@Database(
    version = 1,
    entities = [
        User::class
    ]
)

@TypeConverters(Converter::class)
abstract class MyCardsDataBase : RoomDatabase() {

    abstract fun rickCardDao(): UserDAO

}