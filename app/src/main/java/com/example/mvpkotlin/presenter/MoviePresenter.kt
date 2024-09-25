package com.example.mvpkotlin.presenter

import com.example.mvpkotlin.contract.MovieListContract
import com.example.mvpkotlin.model.ResultX
import com.example.mvpkotlin.service.MovieListModel

class MoviePresenter(private var movieListView: MovieListContract.View?)
    : MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private var movieListModel: MovieListContract.Model = MovieListModel()

    override fun onDestroy() {
        movieListView = null
    }

    override fun getMoreData(movieType: String, pageNo: Int) {
        movieListView?.showProgress()
        movieListModel.getMovieList(this,movieType, pageNo)
    }

    override fun requestDataFromServer(movieType: String, pageNo: Int) {
        movieListView?.showProgress()
        movieListModel.getMovieList(this,movieType, pageNo)
    }

    override fun onFinished(movieArrayList: List<ResultX>) {
        movieListView?.setDataToRecyclerview(movieArrayList)
        movieListView?.hideProgress()
    }

    override fun onFailure(t: Throwable) {
        movieListView?.onResponseFailure(t)
        movieListView?.hideProgress()
    }
}