package lpiemam.com.apppokecards

import android.content.Context
import androidx.room.Room
import lpiemam.com.apppokecards.model.UserCardsDataBase

object DataBaseFactory {

    lateinit var userCardsDataBase: UserCardsDataBase

    fun initialize(appContext : Context) {
        userCardsDataBase = Room.databaseBuilder(appContext, UserCardsDataBase::class.java, "mycardsDB")
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }

}