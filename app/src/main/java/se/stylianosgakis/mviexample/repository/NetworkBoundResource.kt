package se.stylianosgakis.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*
import se.stylianosgakis.mviexample.ui.Constants
import se.stylianosgakis.mviexample.util.*

//ResponseObject generic in our case will be either List<BlogPost> or User.
//ViewStateType will be only MainViewState, but we build it in a generic to allow use later.
abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        //Initially we are for sure loading when using this class
        result.value = DataState.loading(true)
        GlobalScope.launch(Dispatchers.IO) {
            //Delay for some time, to simulate the network request for testing purposes
            delay(Constants.TESTING_NETWORK_DELAY)
            withContext(Dispatchers.Main) {
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource: HTTP 204. Returned nothing")
                onReturnError("HTTP 204. Returned nothing.")
            }
        }
    }

    private fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

}