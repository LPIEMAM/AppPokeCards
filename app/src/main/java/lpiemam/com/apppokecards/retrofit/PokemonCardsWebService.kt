package lpiemam.com.apppokecards.retrofit

import androidx.room.Update
import lpiemam.com.apppokecards.model.Trade
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserCard
import retrofit2.Call
import retrofit2.http.*

interface PokemonCardsWebService {

    @GET("cards/{page}/{name}")
    fun getPokemonCardsForName(@Path("page") page: Int, @Path("name") name: String) : Call<PokemonCardsResponse>


    @GET("cards/page/{page}")
    fun getPokemonCardsForPage(@Path("page") page: Int) : Call<PokemonCardsResponse>
    
    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int) : Call<UserResponse>

    @GET("users/")
    fun getUsers() : Call<UserResponse>

    @GET("usercards/{userId}/{name}")
    fun getUserCardsForName(@Path("userId") userId: Int, @Path("name") name: String) : Call<UserCardsResponse>

    @POST("usercards")
    fun postUserCard(@Body userCard: UserCard) : Call<UserCardResponse>

    @PATCH("usercard")
    fun patchUserCard(@Body userCard: UserCard) : Call<Int>

    @DELETE("usercard/{id}")
    fun deleteUserCard(@Path("id") userCardId: Int): Call<Int>

    @PATCH("user")
    fun patchUser(@Body user: User): Call<Int>

    @POST("trade")
    fun postTrade(@Body trade: Trade): Call<TradeResponse>

    @PATCH("trade")
    fun patchTrade(@Body trade: Trade): Call<Int>

    @PATCH("trade/validated")
    fun patchTradeValidated(@Body trade: Trade) : Call<Int>

    @PATCH("trade/traite")
    fun patchTradeTraite(@Body trade: Trade) : Call<Int>

    @GET("trades/{id}")
    fun getTradesForUser(@Path("id") idUser: Int) : Call<TradesResponse>

    @GET("trades/current/{id}")
    fun getCurrentTradeForUser(@Path("id") idUser: Int) : Call<TradesResponse>

}