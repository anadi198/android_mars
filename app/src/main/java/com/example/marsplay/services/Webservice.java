package com.example.marsplay.services;

import com.example.marsplay.pojo.MultipleResource;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Webservice {
    @GET("search?q=title:DNA")
    Call<MultipleResource> doGetListResources();
}
