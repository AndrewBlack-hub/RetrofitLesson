package com.androidgang.lessonapikudago

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("public-api/v1.4/movies/")
    fun getMovies(): Observable<MoviesResponse>
}