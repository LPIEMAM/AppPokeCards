package lpiemam.com.apppokecards.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object UsersRepository {


    private var pokemonCardApi = ApiFactory.POKEMON_CARDS_WEB_SERVICE

    private var usersLiveData = MutableLiveData<ArrayList<User>>()

    fun fetchUsers() : LiveData<ArrayList<User>> {
        val call = pokemonCardApi.getUsers()


        call.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Timber.e(t)
                usersLiveData.postValue(ArrayList())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    usersLiveData.postValue(response.body()?.users)
                } else {
                    usersLiveData.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }
        })

        return usersLiveData
    }

    fun patchUser(user: User) {
        val call = pokemonCardApi.patchUser(user)

        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {

            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {

            }

        })
    }

}