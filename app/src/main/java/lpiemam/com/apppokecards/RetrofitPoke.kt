package lpiemam.com.apppokecards

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface RetrofitPoke {
    @GET("ApiPokeCards/users.php")
    fun getUserInfo(@Query("id") idUser: Int): Call<List<UserInfo>>

    @PUT("/user/info")
    fun updateUserInfo(
            @Body userInfo: UserInfo
    ): Call<UserInfo>

    @DELETE("/user")
    fun deleteUser(): Call<Void>

}