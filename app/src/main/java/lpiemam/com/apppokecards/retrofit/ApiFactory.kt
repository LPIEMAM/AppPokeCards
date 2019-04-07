package lpiemam.com.apppokecards.retrofit

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {


    //REMOTE API
    private val baseURL = "https://apppokecards.herokuapp.com/"
    //LOCAL API
//    private val baseURL = "http://10.0.2.2:3003/"


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


    val APP_POKE_CARDS_WEB_SERVICE: AppPokeCardsWebService = retrofit().create(AppPokeCardsWebService::class.java)

}