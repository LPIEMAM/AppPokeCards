package lpiemam.com.apppokecards.retrofit.usercards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.UserCard
import lpiemam.com.apppokecards.retrofit.AffectedRowsResponse
import lpiemam.com.apppokecards.retrofit.ApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object UserCardsRepository {


    private var pokemonCardApi = ApiFactory.APP_POKE_CARDS_WEB_SERVICE

    fun fetchUserCardsForName(userId: Int, pokemonName: String): LiveData<ArrayList<UserCard>> {
        var userCardsForPage = MutableLiveData<ArrayList<UserCard>>()
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

    fun postUserCard(userCard: UserCard): LiveData<Int> {
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

        return userCardIdLiveData
    }


    fun patchUserCard(userCard: UserCard): LiveData<Boolean> {
        var rowUpdatedLiveData = MutableLiveData<Boolean>()
        pokemonCardApi.patchUserCard(userCard).enqueue(object : Callback<AffectedRowsResponse> {
            override fun onFailure(call: Call<AffectedRowsResponse>, t: Throwable) {
                rowUpdatedLiveData.postValue(false)
            }

            override fun onResponse(call: Call<AffectedRowsResponse>, response: Response<AffectedRowsResponse>) {
                if (response.isSuccessful) {
                    rowUpdatedLiveData.postValue(true)
                } else {
                    rowUpdatedLiveData.postValue(false)
                }
            }

        })
        return rowUpdatedLiveData
    }

    fun deleteUserCard(userCard: UserCard): LiveData<Boolean> {
        var rowDeletedLiveData = MutableLiveData<Boolean>()
        pokemonCardApi.deleteUserCard(userCard.userCardID).enqueue(object : Callback<AffectedRowsResponse> {
            override fun onFailure(call: Call<AffectedRowsResponse>, t: Throwable) {
                rowDeletedLiveData.postValue(false)
            }

            override fun onResponse(call: Call<AffectedRowsResponse>, response: Response<AffectedRowsResponse>) {
                if (response.isSuccessful) {
                    rowDeletedLiveData.postValue(true)
                } else {
                    rowDeletedLiveData.postValue(false)
                }
            }

        })
        return rowDeletedLiveData
    }

}