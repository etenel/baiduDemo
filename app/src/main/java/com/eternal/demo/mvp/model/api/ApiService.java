package com.eternal.demo.mvp.model.api;

import com.eternal.demo.mvp.model.entity.AccessTokenEntity;
import com.eternal.demo.mvp.model.entity.FaceResultEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    String clientId = "P5oZl6GyQ4R8T6qrwOrldLR1";
    String clientSecret = "44BqrtHU0jm8TCygcg8p7e3iC49cuTAI";
    String grant_type = "client_credentials";

    //获取token
    @POST("oauth/2.0/token")
    Observable<AccessTokenEntity> getAuth(@Query("grant_type") String grantType, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

    @POST("rest/2.0/face/v3/detect")
    Observable<FaceResultEntity> uploadFile(@Query("access_token") String token, @Body RequestBody path);
}
