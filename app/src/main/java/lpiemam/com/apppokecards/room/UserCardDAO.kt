package lpiemam.com.apppokecards.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lpiemam.com.apppokecards.model.UserCard

@Dao
interface UserCardDAO {

    @Query("DELETE FROM UserCard WHERE UserCard.userCardID = :id")
    fun deleteCard(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(userCard: UserCard)

    @Query("SELECT * FROM UserCard")
    fun fetchAll(): LiveData<List<UserCard>>
}

