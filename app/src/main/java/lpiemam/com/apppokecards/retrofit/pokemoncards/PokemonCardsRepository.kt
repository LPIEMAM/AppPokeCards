package lpiemam.com.apppokecards.retrofit.pokemoncards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.retrofit.ApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object PokemonCardsRepository {

    private var pokemonCardsLiveDataForName = MutableLiveData<ArrayList<PokemonCard>>()
    private var pokemonCardsLiveDataForNationalPokedexNumber = MutableLiveData<ArrayList<PokemonCard>>()

    private var pokemonCardApi = ApiFactory.APP_POKE_CARDS_WEB_SERVICE
    var error = MutableLiveData<Boolean>()


    fun fetchPokemonCardsForName(page: Int, pokemonName: String): LiveData<ArrayList<PokemonCard>> {
        pokemonCardsLiveDataForName = MutableLiveData()
        val call = pokemonCardApi.getPokemonCardsForName(page, pokemonName)


        call.enqueue(object : Callback<PokemonCardsResponse> {
            override fun onFailure(call: Call<PokemonCardsResponse>, t: Throwable) {
                Timber.e(t)
                pokemonCardsLiveDataForName.postValue(ArrayList())
                error.postValue(true)
            }

            override fun onResponse(call: Call<PokemonCardsResponse>, response: Response<PokemonCardsResponse>) {
                if (response.isSuccessful) {
                    error.postValue(false)
                    pokemonCardsLiveDataForName.postValue(response.body()?.cards)
                } else {
                    error.postValue(true)
                    pokemonCardsLiveDataForName.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }
        })

        return pokemonCardsLiveDataForName
    }

    fun fetchPokemonCardsForPage(page: Int): LiveData<ArrayList<PokemonCard>> {
        val call = pokemonCardApi.getPokemonCardsForPage(page)


        call.enqueue(object : Callback<PokemonCardsResponse> {
            override fun onFailure(call: Call<PokemonCardsResponse>, t: Throwable) {
                Timber.e(t)
                pokemonCardsLiveDataForNationalPokedexNumber.postValue(ArrayList())
                error.postValue(true)
            }

            override fun onResponse(call: Call<PokemonCardsResponse>, response: Response<PokemonCardsResponse>) {
                if (response.isSuccessful) {
                    error.postValue(false)
                    pokemonCardsLiveDataForNationalPokedexNumber.postValue(response.body()?.cards)
                } else {
                    error.postValue(true)
                    pokemonCardsLiveDataForNationalPokedexNumber.postValue(ArrayList())
                    Timber.e(response.message())
                }
            }
        })

        return pokemonCardsLiveDataForNationalPokedexNumber
    }

}