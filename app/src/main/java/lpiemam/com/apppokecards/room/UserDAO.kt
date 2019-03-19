package lpiemam.com.apppokecards.room

import androidx.lifecycle.LiveData
import androidx.room.*
import lpiemam.com.apppokecards.model.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM User")
    fun getUser(): LiveData<User>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}