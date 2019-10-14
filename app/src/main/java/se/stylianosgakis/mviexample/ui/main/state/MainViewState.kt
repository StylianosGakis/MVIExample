package se.stylianosgakis.mviexample.ui.main.state

import se.stylianosgakis.mviexample.model.BlogPost
import se.stylianosgakis.mviexample.model.User

data class MainViewState(
    var user: User?,
    var blogPosts: List<BlogPost>? = null
)