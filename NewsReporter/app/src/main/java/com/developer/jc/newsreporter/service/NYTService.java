package com.developer.jc.newsreporter.service;

import com.developer.jc.newsreporter.News;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * REST service for New York Times API
 */
public interface NYTService {
    @GET("/svc/mostpopular/v2/mostviewed/all-sections/7.json")
    Call<News> listResults();
}
