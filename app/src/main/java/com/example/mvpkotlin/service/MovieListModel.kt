package com.example.mvpkotlin.service

import android.util.Log
import com.example.mvpkotlin.contract.MovieListContract
import com.example.mvpkotlin.model.MovieListResponse
import com.example.mvpkotlin.model.ResultX
import com.example.mvpkotlin.network.ApiClient
import com.example.mvpkotlin.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListModel : MovieListContract.Model {

    private val TAG = "MovieListModel"
    private val API_KEY = "152383ad3c54b80c1dc5968e66cb70a1"
    private var pageNo = 1

    override fun getMovieList(onFinishedListener: MovieListContract.Model.OnFinishedListener,movieType: String, pageNo: Int) {
        val apiService = ApiClient().getClient()?.create(ApiInterface::class.java)

        val call = apiService?.getPopularMovies(movieType, API_KEY, pageNo)

        call?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                val movies: List<ResultX>? = response.body()?.results
                Log.d("ApiClient", "Response received with code: ${response.code()}")

                if (movies != null) {
                    Log.d(TAG, "onResponse: $movies , $movieType")
                    onFinishedListener.onFinished(movies)
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
                onFinishedListener.onFailure(t)
            }
        })
    }

}
