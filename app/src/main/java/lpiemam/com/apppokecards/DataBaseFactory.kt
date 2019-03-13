package lpiemam.com.apppokecards

import android.content.Context
import androidx.room.Room

object DataBaseFactory {

    lateinit var mycardsDatabase : MyCardsDataBase

    fun initialize(appContext : Context) {
        mycardsDatabase = Room.databaseBuilder(appContext, MyCardsDataBase::class.java, "mycardsDB")
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }

}