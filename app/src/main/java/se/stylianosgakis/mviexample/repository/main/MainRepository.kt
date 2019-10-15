package se.stylianosgakis.mviexample.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import se.stylianosgakis.mviexample.api.RetrofitBuilder
import se.stylianosgakis.mviexample.ui.main.state.MainViewState
import se.stylianosgakis.mviexample.util.ApiEmptyResponse
import se.stylianosgakis.mviexample.util.ApiErrorResponse
import se.stylianosgakis.mviexample.util.ApiSuccessResponse
import se.stylianosgakis.mviexample.util.DataState

object MainRepository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(
                                    data = MainViewState(
                                        blogPosts = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }
                            is ApiEmptyResponse -> {
                                DataState.error(
                                    message = "HTTP 204. Returned nothing"
                                )
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(
                                    data = MainViewState(
                                        user = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }
                            is ApiEmptyResponse -> {
                                DataState.error(
                                    message = "HTTP 204. Returned nothing"
                                )
                            }
                        }
                    }
                }
            }
    }

}