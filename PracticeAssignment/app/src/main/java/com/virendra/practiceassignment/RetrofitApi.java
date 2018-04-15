package com.virendra.practiceassignment;

import com.virendra.practiceassignment.Model.DisplayData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitApi {
    String BASE_URL = "https://jsonplaceholder.typicode.com";

    @GET("photos")
    Observable<List<DisplayData>> getPhotos();
}
