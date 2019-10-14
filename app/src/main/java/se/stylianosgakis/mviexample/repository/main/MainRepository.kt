package se.stylianosgakis.mviexample.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import se.stylianosgakis.mviexample.api.RetrofitBuilder
import se.stylianosgakis.mviexample.ui.main.state.MainViewState
import se.stylianosgakis.mviexample.util.ApiEmptyResponse
import se.stylianosgakis.mviexample.util.ApiErrorResponse
import se.stylianosgakis.mviexample.util.ApiSuccessResponse

object MainRepository {

    fun getBlogPosts(): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                MainViewState() //TODO handle error
                            }
                            is ApiEmptyResponse -> {
                                MainViewState() //TODO handle empty/error
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                MainViewState(
                                    user = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                MainViewState() //TODO handle error
                            }
                            is ApiEmptyResponse -> {
                                MainViewState() //TODO handle empty/error
                            }
                        }
                    }
                }
            }
    }

}