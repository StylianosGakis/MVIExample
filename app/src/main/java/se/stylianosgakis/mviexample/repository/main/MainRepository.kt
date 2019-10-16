package se.stylianosgakis.mviexample.repository.main

import androidx.lifecycle.LiveData
import se.stylianosgakis.mviexample.api.RetrofitBuilder
import se.stylianosgakis.mviexample.model.BlogPost
import se.stylianosgakis.mviexample.model.User
import se.stylianosgakis.mviexample.repository.NetworkBoundResource
import se.stylianosgakis.mviexample.ui.main.state.MainViewState
import se.stylianosgakis.mviexample.util.ApiSuccessResponse
import se.stylianosgakis.mviexample.util.DataState
import se.stylianosgakis.mviexample.util.GenericApiResponse

object MainRepository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId = userId)
            }

        }.asLiveData()
    }

}