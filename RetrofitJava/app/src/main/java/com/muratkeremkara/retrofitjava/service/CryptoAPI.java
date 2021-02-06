package com.muratkeremkara.retrofitjava.service;

import com.muratkeremkara.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    // https://api.nomics.com/v1/prices?key=d04b78800d5a60ee266ad86161301724

    @GET("prices?key=d04b78800d5a60ee266ad86161301724")
    Observable<List<CryptoModel>> getData();  // bu daha gelişmişi

    //Call<List<CryptoModel>> getData();

    //Observable<List<CryptoModel>> getData();  // bu daha gelişmişi

}
