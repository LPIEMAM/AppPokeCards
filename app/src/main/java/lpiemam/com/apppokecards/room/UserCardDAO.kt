package lpiemam.com.apppokecards.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lpiemam.com.apppokecards.model.UserCard

@Dao
interface UserCardDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(listCard: List<UserCard>?)

    @Query("DELETE FROM UserCard")
    fun clearTable()

    @Query("SELECT * FROM UserCard")
    fun fetchAll(): LiveData<List<UserCard>>
}

