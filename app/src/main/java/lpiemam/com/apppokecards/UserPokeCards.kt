package lpiemam.com.apppokecards

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log

import com.google.gson.GsonBuilder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class UserPokeCards(private val userId: Int, private val loginActivity: LoginActivity) : Callback<List<UserInfo>>, Serializable     {

    val TAG = "Client"
    val BASE_URL = "http://10.0.2.2:8888/"

    fun start() {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val retro = retrofit.create(RetrofitPoke::class.java)

        val call = retro.getUserInfo(userId)
        call.enqueue(this)

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onResponse(call: Call<List<UserInfo>>, response: Response<List<UserInfo>>) {
        if (response.isSuccessful) {
            val infoUserList = response.body()
            loginActivity.callBack(infoUserList!![0]!!)
            for (userInfo in infoUserList) {
                Log.d(TAG, "onResponse:  " + userInfo.pseudo!!)
            }
        } else {
            println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
        t.printStackTrace()
    }


}
