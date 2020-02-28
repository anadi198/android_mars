package com.example.marsplay.networking;

import androidx.lifecycle.MutableLiveData;

import com.example.marsplay.clients.ApiClient;
import com.example.marsplay.pojo.MultipleResource;
import com.example.marsplay.services.Webservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DnaRepository {
    private static DnaRepository dnaRepository;

    public static DnaRepository getInstance() {
        if (dnaRepository == null) {
            dnaRepository = new DnaRepository();
        }
        return dnaRepository;
    }

    private Webservice webservice;

    public DnaRepository() {
        webservice = ApiClient.createService(Webservice.class);
    }

    public MutableLiveData<MultipleResource> getArticles() {
        final MutableLiveData<MultipleResource> articleData = new MutableLiveData<>();
        webservice.doGetListResources().enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call,
                                   Response<MultipleResource> response) {
                if (response.isSuccessful()) {
                    articleData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                articleData.setValue(null);
            }
        });
        return articleData;
    }
}
