package lpiemam.com.apppokecards.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Query("SELECT * FROM User")
    fun getUser(): LiveData<User>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user:User)

}