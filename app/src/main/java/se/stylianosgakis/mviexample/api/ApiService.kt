package se.stylianosgakis.mviexample.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import se.stylianosgakis.mviexample.model.BlogPost
import se.stylianosgakis.mviexample.model.User
import se.stylianosgakis.mviexample.util.GenericApiResponse

interface ApiService {

    @GET("placeholder/blogs")
    fun getUser(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

}