package lpiemam.com.apppokecards.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonCardsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(listCard: List<UserCard>?)

    @Query("SELECT * FROM UserCard")
    fun fetchAll(): LiveData<List<UserCard>>

    @Query("SELECT * FROM User")
    fun fetchAll2(): LiveData<User>

    @Query("SELECT * FROM PokemonCard WHERE id =:id")
    fun get(id: Int): LiveData<PokemonCard>
}