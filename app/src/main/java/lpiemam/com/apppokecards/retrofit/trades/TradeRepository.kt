package lpiemam.com.apppokecards.retrofit.trades

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.Trade
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.retrofit.AffectedRowsResponse
import lpiemam.com.apppokecards.retrofit.ApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object TradeRepository {


    private var pokemonCardApi = ApiFactory.APP_POKE_CARDS_WEB_SERVICE


    fun postTrade(trade: Trade): LiveData<Int> {
        val tradeIdLiveData = MutableLiveData<Int>()
        val call = pokemonCardApi.postTrade(trade)

        call.enqueue(object : Callback<TradeResponse> {
            override fun onFailure(call: Call<TradeResponse>, t: Throwable) {
                Timber.e(t)
                tradeIdLiveData.postValue(-1)
            }

            override fun onResponse(call: Call<TradeResponse>, response: Response<TradeResponse>) {
                if (response.isSuccessful) {
                    tradeIdLiveData.postValue(response.body()!!.insertId)
                } else {
                    tradeIdLiveData.postValue(-1)
                    Timber.e(response.message())
                }
            }

        })
        return tradeIdLiveData
    }

    fun patchTrade(trade: Trade) {
        val call = pokemonCardApi.patchTrade(trade)

        call.enqueue(object : Callback<AffectedRowsResponse> {
            override fun onFailure(call: Call<AffectedRowsResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<AffectedRowsResponse>, response: Response<AffectedRowsResponse>) {

            }

        })
    }

    fun patchTradeValidated(trade: Trade) {
        val call = pokemonCardApi.patchTradeValidated(trade)


        call.enqueue(object : Callback<AffectedRowsResponse> {
            override fun onFailure(call: Call<AffectedRowsResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<AffectedRowsResponse>, response: Response<AffectedRowsResponse>) {

            }

        })
    }

    fun deleteTrade(trade: Trade) {
        val call = pokemonCardApi.deleteTrade(trade.id)

        call.enqueue(object : Callback<AffectedRowsResponse> {
            override fun onFailure(call: Call<AffectedRowsResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<AffectedRowsResponse>, response: Response<AffectedRowsResponse>) {

            }

        })
    }

    fun getTradesForUser(user: User): LiveData<ArrayList<Trade>> {
        val tradeListLiveData = MutableLiveData<ArrayList<Trade>>()

        val call = pokemonCardApi.getTradesForUser(user.userId)

        call.enqueue(object : Callback<TradesResponse> {
            override fun onFailure(call: Call<TradesResponse>, t: Throwable) {
                Timber.e(t)
                tradeListLiveData.postValue(ArrayList())
            }

            override fun onResponse(call: Call<TradesResponse>, response: Response<TradesResponse>) {
                if (response.isSuccessful) {
                    tradeListLiveData.postValue(response.body()!!.trades)
                } else {
                    tradeListLiveData.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }

        })

        return tradeListLiveData

    }

    fun getCurrentTradeForUser(user: User): LiveData<ArrayList<Trade>> {
        val tradeListLiveData = MutableLiveData<ArrayList<Trade>>()

        val call = pokemonCardApi.getCurrentTradeForUser(user.userId)

        call.enqueue(object : Callback<TradesResponse> {
            override fun onFailure(call: Call<TradesResponse>, t: Throwable) {
                Timber.e(t)
                tradeListLiveData.postValue(ArrayList())
            }

            override fun onResponse(call: Call<TradesResponse>, response: Response<TradesResponse>) {
                if (response.isSuccessful) {
                    tradeListLiveData.postValue(response.body()!!.trades)
                } else {
                    tradeListLiveData.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }

        })

        return tradeListLiveData

    }


}