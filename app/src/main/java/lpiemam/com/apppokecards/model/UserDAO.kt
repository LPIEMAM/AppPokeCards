package lpiemam.com.apppokecards.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(listCard: List<UserCard>?)

    @Query("SELECT * FROM RickCard")
    fun fetchAll(): LiveData<List<RickCard>>

    @Query("SELECT * FROM RickCard")
    fun fetchAll2(): List<RickCard>

    @Query("SELECT * FROM RickCard WHERE id =:rickId")
    fun get(rickId: Int): LiveData<RickCard>
}