package lpiemam.com.apppokecards;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitPoke {
    @GET("ApiPokeCards/users.php")
    Call<List<UserInfo>> getUserInfo(@Query("id") int idUser);

    @PUT("/user/info")
    Call<UserInfo> updateUserInfo(
            @Body UserInfo userInfo
    );

    @DELETE("/user")
    Call<Void> deleteUser();

}