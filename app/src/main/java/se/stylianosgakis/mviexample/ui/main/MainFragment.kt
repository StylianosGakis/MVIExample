package se.stylianosgakis.mviexample.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import se.stylianosgakis.mviexample.R

class MainFragment : Fragment() {
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> {
                //TODO functionality for clicking get user menu option
            }
            R.id.action_get_blogs -> {
                //TODO functionality for clicking get blogs menu option
            }
        }
        return super.onOptionsItemSelected(item)
    }
}