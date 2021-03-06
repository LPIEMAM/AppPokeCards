package lpiemam.com.apppokecards.retrofit

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object ApiFactory {


    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val baseURL = "https://api.pokemontcg.io/v1/"


    private val interceptorLog = HttpLoggingInterceptor().let {
        it.level = HttpLoggingInterceptor.Level.BODY
        it
    }

    //OkhttpClient for building http request url
    private val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        .addInterceptor(interceptorLog)
        //.addInterceptor(interceptor)
        .build()


    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val POKEMON_CARDS_WEB_SERVICE: PokemonCardsWebService = retrofit().create(PokemonCardsWebService::class.java)

}