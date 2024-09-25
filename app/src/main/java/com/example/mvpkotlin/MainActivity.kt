package com.example.mvpkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpkotlin.contract.MovieListContract
import com.example.mvpkotlin.model.ResultX
import com.example.mvpkotlin.presenter.MoviePresenter
import com.example.mvpkotlin.view.MovieListAdapter

class MainActivity : AppCompatActivity(), MovieListContract.View {

    private val TAG = "MainActivity"
    private var movieTypes: List<String> = listOf("movie/popular","movie/now_playing", "movie/top_rated", "movie/upcoming")
    private lateinit var autoCompleteTxt: AutoCompleteTextView
    private var selectedMovieType: String = "movie/popular"
    private lateinit var moviePresenter: MoviePresenter
    private lateinit var rvMovieList: RecyclerView
    private lateinit var movieList: MutableList<ResultX>
    lateinit var movieListAdapter: MovieListAdapter
    private lateinit var pbLoading: ProgressBar

    var pageNo: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Initialize the views
        autoCompleteTxt = findViewById(R.id.autoCompleteTxt)
        rvMovieList = findViewById(R.id.rvMovieList)
        pbLoading = findViewById(R.id.pbLoading)

        // Initialize the movie list and adapter
        movieList = mutableListOf()
        movieListAdapter = MovieListAdapter(this@MainActivity,movieList)
        rvMovieList.adapter = movieListAdapter


        layoutManager = LinearLayoutManager(this)
        rvMovieList.layoutManager = layoutManager
        rvMovieList.setHasFixedSize(true)
//        rvMovieList.adapter = movieListAdapter  // Attach the adapter to the RecyclerView here

        // Initialize presenter
        moviePresenter = MoviePresenter(this)

        // Set up AutoCompleteTextView for movie types
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, movieTypes)
        autoCompleteTxt.setAdapter(adapter)

        // Handle selection from the dropdown
        autoCompleteTxt.setOnItemClickListener { _, _, position, _ ->
            selectedMovieType = movieTypes[position]
            movieList.clear()  // Clear list for new data
            pageNo = 1
            Log.d(TAG, "selectedMovieType: $selectedMovieType")
            moviePresenter.requestDataFromServer(selectedMovieType, pageNo)
        }

        // Initial data load
        moviePresenter.requestDataFromServer(selectedMovieType, pageNo)

        // Handle scroll events for pagination
        rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                        pageNo++
                        moviePresenter.requestDataFromServer(selectedMovieType, pageNo)
                    }
                }
            }
        })
    }

    override fun showProgress() {
        pbLoading.visibility = View.VISIBLE
        isLoading = true
    }

    override fun hideProgress() {
        pbLoading.visibility = View.GONE
        isLoading = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setDataToRecyclerview(movieListArray: List<ResultX>) {

        movieList.addAll(movieListArray)
        movieListAdapter = MovieListAdapter( applicationContext,movieList)
        movieListAdapter.notifyDataSetChanged()
        rvMovieList.adapter = movieListAdapter
        if (movieListArray.isEmpty()) {
            isLastPage = true  //
        }
//        if (movieListAdapter == null) {
//            movieListAdapter = MovieListAdapter(this, movieList)
//            rvMovieList.adapter = movieListAdapter
//        }else{
//            Log.d(TAG, "setDataToRecyclerview: $movieListAdapter")
//        }
//
//        movieList.addAll(movieListArray)
//        movieListAdapter.notifyDataSetChanged()
//        if (movieListArray.isEmpty()) {
//            isLastPage = true
//
//        } else Log.d(TAG, "setDataToRecyclerview: $movieListArray")
    }

    override fun onResponseFailure(throwable: Throwable) {
        Log.e("ERROR:", throwable.message ?: "Unknown error")
        Toast.makeText(this@MainActivity, "Error in getting data", Toast.LENGTH_LONG).show()
    }


}