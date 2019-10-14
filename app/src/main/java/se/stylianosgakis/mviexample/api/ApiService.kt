package se.stylianosgakis.mviexample.api

import retrofit2.http.GET
import retrofit2.http.Path
import se.stylianosgakis.mviexample.model.User

interface ApiService {

    @GET("placeholder/blogs")
    fun getUser(): User

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User

}