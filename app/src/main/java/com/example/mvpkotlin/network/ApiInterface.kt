package com.example.mvpkotlin.network

import com.example.mvpkotlin.model.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("{movieType}")
    fun getPopularMovies(
                        @Path("movieType") movieType: String,
                        @Query("api_key") apiKey: String,
                        @Query("page") page: Int): Call<MovieListResponse>
}