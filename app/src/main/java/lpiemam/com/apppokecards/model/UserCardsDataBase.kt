package lpiemam.com.apppokecards.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    version = 1,
    entities = [
        UserCard::class,
        User::class
    ]
)

@TypeConverters(Converter::class)
abstract class UserCardsDataBase : RoomDatabase() {

    abstract fun userCardDAO(): UserCardsDAO
    abstract fun userDAO(): UserDAO

}