package lpiemam.com.apppokecards.retrofit

import lpiemam.com.apppokecards.model.Trade
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserCard
import lpiemam.com.apppokecards.retrofit.pokemoncards.PokemonCardsResponse
import lpiemam.com.apppokecards.retrofit.trades.TradeResponse
import lpiemam.com.apppokecards.retrofit.trades.TradesResponse
import lpiemam.com.apppokecards.retrofit.usercards.UserCardResponse
import lpiemam.com.apppokecards.retrofit.usercards.UserCardsResponse
import lpiemam.com.apppokecards.retrofit.users.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface AppPokeCardsWebService {

    // PokemonCards

    @GET("cards/{page}/{name}")
    fun getPokemonCardsForName(@Path("page") page: Int, @Path("name") name: String): Call<PokemonCardsResponse>

    @GET("cards/page/{page}")
    fun getPokemonCardsForPage(@Path("page") page: Int): Call<PokemonCardsResponse>

    // Users

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<UserResponse>

    @GET("users")
    fun getUsers(): Call<UserResponse>

    @PATCH("user")
    fun patchUser(@Body user: User): Call<AffectedRowsResponse>

    // UserCards

    @GET("usercards/{userId}/{name}")
    fun getUserCardsForName(@Path("userId") userId: Int, @Path("name") name: String): Call<UserCardsResponse>

    @POST("usercards")
    fun postUserCard(@Body userCard: UserCard): Call<UserCardResponse>

    @PATCH("usercard")
    fun patchUserCard(@Body userCard: UserCard): Call<AffectedRowsResponse>

    @DELETE("usercard/{id}")
    fun deleteUserCard(@Path("id") userCardId: Int): Call<AffectedRowsResponse>

    // Trades

    @GET("trades/{id}")
    fun getTradesForUser(@Path("id") idUser: Int): Call<TradesResponse>

    @GET("trades/current/{id}")
    fun getCurrentTradeForUser(@Path("id") idUser: Int): Call<TradesResponse>

    @POST("trade")
    fun postTrade(@Body trade: Trade): Call<TradeResponse>

    @PATCH("trade")
    fun patchTrade(@Body trade: Trade): Call<AffectedRowsResponse>

    @PATCH("trade/validated")
    fun patchTradeValidated(@Body trade: Trade): Call<AffectedRowsResponse>

    @DELETE("trade/{id}")
    fun deleteTrade(@Path("id") idTrade: Int): Call<AffectedRowsResponse>

}