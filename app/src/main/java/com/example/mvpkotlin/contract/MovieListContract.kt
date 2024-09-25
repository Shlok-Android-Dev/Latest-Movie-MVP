package com.example.mvpkotlin.contract

import com.example.mvpkotlin.model.ResultX

open class MovieListContract {

        interface Model {

            interface OnFinishedListener {
                fun onFinished(movieArrayList: List<ResultX>)
                fun onFailure(t: Throwable)
            }

            fun getMovieList(onFinishedListener: OnFinishedListener, movieType: String, pageNo: Int)
        }

        interface View {

            fun showProgress()
            fun hideProgress()
            fun setDataToRecyclerview(movieListArray: List<ResultX>)
            fun onResponseFailure(throwable: Throwable)
        }

        interface Presenter {

            fun onDestroy()
            fun getMoreData(SelectMovieType: String,pageNo: Int)
            fun requestDataFromServer(SelectMovieType: String,pageNo: Int)
        }
    }