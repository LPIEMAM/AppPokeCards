package lpiemam.com.apppokecards.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.PokemonCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object PokemonCardsRepository {

    private var pokemonCardsLiveData = MutableLiveData<ArrayList<PokemonCard>>()
    private var pokemonCardApi = ApiFactory.POKEMON_CARDS_WEB_SERVICE
    var error = MutableLiveData<Boolean>()


    fun fetchPokemonCardsForName(pokemonName: String) : LiveData<ArrayList<PokemonCard>> {
        val call = pokemonCardApi.getPokemonCardsForName(pokemonName)


        call.enqueue(object : Callback<PokemonCardsResponse> {
            override fun onFailure(call: Call<PokemonCardsResponse>, t: Throwable) {
                Timber.e(t)
                pokemonCardsLiveData.postValue(ArrayList())
                error.postValue(true)
            }

            override fun onResponse(call: Call<PokemonCardsResponse>, response: Response<PokemonCardsResponse>) {
                if (response.isSuccessful) {
                    error.postValue(false)
                    pokemonCardsLiveData.postValue(response.body()?.cards)
                } else {
                    error.postValue(true)
                    pokemonCardsLiveData.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }
        })

        return pokemonCardsLiveData
    }

}