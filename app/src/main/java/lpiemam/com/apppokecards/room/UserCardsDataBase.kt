package lpiemam.com.apppokecards.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserCard
import lpiemam.com.apppokecards.retrofit.Converter


@Database(
    version = 1,
    entities = [
        UserCard::class,
        User::class
    ],
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class UserCardsDataBase : RoomDatabase() {

    abstract fun userCardDAO(): UserCardDAO
    abstract fun userDAO(): UserDAO

}