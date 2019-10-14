package se.stylianosgakis.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.stylianosgakis.mviexample.ui.main.state.MainViewState

class MainViewModel : ViewModel() {

    //This is the singe mutable live data object that wraps around the MainViewState class and will
    //be observed for any kind of changes
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //This live data will be the one that is exposed to the other classes to be observed.
    //This naming convention is used in Google Samples therefore is common practice
    val viewState: LiveData<MainViewState>
        get() = _viewState

}