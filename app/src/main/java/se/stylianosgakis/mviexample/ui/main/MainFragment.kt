package se.stylianosgakis.mviexample.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_main.*
import se.stylianosgakis.mviexample.R
import se.stylianosgakis.mviexample.model.User
import se.stylianosgakis.mviexample.ui.DataStateListener
import se.stylianosgakis.mviexample.ui.main.state.MainStateEvent
import se.stylianosgakis.mviexample.util.TopSpacingItemDecoration

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateListener: DataStateListener
    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initViewModel()
        subscribeObservers()
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_blogs -> {
                triggerGetBlogsEvent()
            }
            R.id.action_get_user -> {
                triggerGetUserEvent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: DataState: $dataState")

            //Handle loading and message
            dataStateListener.onDataStateChange(dataState)

            //Handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let { blogPosts ->
                        viewModel.setBlogListData(blogPosts)
                    }
                    mainViewState.user?.let { user ->
                        viewModel.setUserData(user)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let { blogPosts ->
                println("DEBUG: Setting blog posts to RecyclerView: $blogPosts")
                mainRecyclerAdapter.submitList(blogPosts)
            }
            viewState.user?.let { user ->
                println("DEBUG: Setting user data: $user")
                setUserProperties(user)
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            mainRecyclerAdapter = MainRecyclerAdapter()
            adapter = mainRecyclerAdapter
        }
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun setUserProperties(user: User) {
        email.text = user.email
        username.text = user.username
        view?.let {
            Glide.with(it.context)
                .load(user.image)
                .into(image)
        }
    }

}