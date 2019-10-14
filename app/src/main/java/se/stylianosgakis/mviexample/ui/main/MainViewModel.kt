package se.stylianosgakis.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import se.stylianosgakis.mviexample.model.BlogPost
import se.stylianosgakis.mviexample.model.User
import se.stylianosgakis.mviexample.repository.main.MainRepository
import se.stylianosgakis.mviexample.ui.main.state.MainStateEvent
import se.stylianosgakis.mviexample.ui.main.state.MainStateEvent.*
import se.stylianosgakis.mviexample.ui.main.state.MainViewState
import se.stylianosgakis.mviexample.util.AbsentLiveData

class MainViewModel : ViewModel() {

    //The viewState that contains all the information about the model classes
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //Used to set the state of the event triggered by the viewModel
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        return when (stateEvent) {
            is GetBlogPostsEvent -> {
                return MainRepository.getBlogPosts()
            }
            is GetUserEvent -> {
                return MainRepository.getUser(stateEvent.userId)
            }
            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUserData(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let { mainViewState ->
            mainViewState
        } ?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}