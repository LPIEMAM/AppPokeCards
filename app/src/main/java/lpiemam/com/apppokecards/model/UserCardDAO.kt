package lpiemam.com.apppokecards.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserCardsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(listCard: List<UserCard>?)

    @Query("SELECT * FROM UserCard")
    fun fetchAll(): LiveData<ArrayList<UserCard>>

}