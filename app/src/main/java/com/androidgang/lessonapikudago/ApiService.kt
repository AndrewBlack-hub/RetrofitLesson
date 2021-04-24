package com.androidgang.lessonapikudago

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("public-api/v1.4/movies/")
    fun getMovies(@Query("page_size") size: Int): Observable<MoviesResponse>
}