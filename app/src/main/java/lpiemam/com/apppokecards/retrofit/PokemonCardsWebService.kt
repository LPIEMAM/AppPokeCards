package lpiemam.com.apppokecards.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonCardsWebService {

    @GET("cards?pageSize=100")
    fun getPokemonCardsForName(@Query("page") page: Int, @Query("name") name: String) : Call<PokemonCardsResponse>


    @GET("cards?pageSize=500")
    fun getPokemonCardsForPage(@Query("page") page: Int) : Call<PokemonCardsResponse>

}