package com.example.marsplay.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.marsplay.networking.DnaRepository;
import com.example.marsplay.pojo.MultipleResource;

public class ArticleViewModel extends ViewModel {
    private MutableLiveData<MultipleResource> mutableLiveData;
    private DnaRepository dnaRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        dnaRepository = DnaRepository.getInstance();
        mutableLiveData = dnaRepository.getArticles();
    }

    public LiveData<MultipleResource> getDnaRepository() {
        return mutableLiveData;
    }
}
