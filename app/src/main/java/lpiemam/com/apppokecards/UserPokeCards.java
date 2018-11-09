package lpiemam.com.apppokecards;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPokeCards implements Callback<List<UserInfo>> {

    static final String TAG = "Client";
    static final String BASE_URL = "http://10.0.2.2:8888/";

    private int userId;
    private MainActivity mainActivity;

    public UserPokeCards(int userId, MainActivity activity) {
        this.userId = userId;
        this.mainActivity = activity;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitPoke retro = retrofit.create(RetrofitPoke.class);

        Call<List<UserInfo>> call = retro.getUserInfo(userId);
        call.enqueue(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
        if(response.isSuccessful()) {
            List<UserInfo> infoUserList = response.body();
            mainActivity.callBack(infoUserList.get(0).getPseudo());
            for (UserInfo userInfo : infoUserList) {
                Log.d(TAG, "onResponse:  " + userInfo.getPseudo());
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<UserInfo>> call, Throwable t) {
        t.printStackTrace();
    }
}
