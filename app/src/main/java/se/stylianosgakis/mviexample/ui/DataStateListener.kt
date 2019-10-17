package se.stylianosgakis.mviexample.ui

import se.stylianosgakis.mviexample.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)

}