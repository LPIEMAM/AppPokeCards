package lpiemam.com.apppokecards.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.UserCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object UserCardsRepository {


    private var userCardsForPage = MutableLiveData<ArrayList<UserCard>>()

    private var pokemonCardApi = ApiFactory.POKEMON_CARDS_WEB_SERVICE

    fun fetchUserCardsForName(userId: Int, pokemonName: String) : LiveData<ArrayList<UserCard>> {
        userCardsForPage = MutableLiveData()
        val call = pokemonCardApi.getUserCardsForName(userId, pokemonName)


        call.enqueue(object : Callback<UserCardsResponse> {
            override fun onFailure(call: Call<UserCardsResponse>, t: Throwable) {
                Timber.e(t)
                userCardsForPage.postValue(ArrayList())
            }

            override fun onResponse(call: Call<UserCardsResponse>, response: Response<UserCardsResponse>) {
                if (response.isSuccessful) {
                    userCardsForPage.postValue(response.body()?.userCards)
                } else {
                    userCardsForPage.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }
        })

        return userCardsForPage
    }

    fun postUserCard(userCard: UserCard) : LiveData<Int> {
        var userCardIdLiveData = MutableLiveData<Int>()
        val call = pokemonCardApi.postUserCard(userCard)

        call.enqueue(object : Callback<UserCardResponse> {
            override fun onFailure(call: Call<UserCardResponse>, t: Throwable) {
                Timber.e(t)
                userCardIdLiveData.postValue(-1)
            }

            override fun onResponse(call: Call<UserCardResponse>, response: Response<UserCardResponse>) {
                if (response.isSuccessful) {
                    userCardIdLiveData.postValue(response.body()!!.insertId)
                } else {
                    userCardIdLiveData.postValue(-1)
                    Timber.e(response.message())
                }
            }

        })

        return  userCardIdLiveData
    }


    fun patchUserCard(userCard: UserCard) {
        pokemonCardApi.patchUserCard(userCard).enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {

            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {

            }

        })
    }

    fun deleteUserCard(userCard: UserCard) {
        pokemonCardApi.deleteUserCard(userCard.userCardID).enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {

            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {

            }

        })
    }

}