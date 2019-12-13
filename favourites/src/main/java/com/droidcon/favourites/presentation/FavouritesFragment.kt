package com.droidcon.favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.favourites.R
import com.droidcon.schedule.ui.schedulelist.model.SessionRow
import com.droidcon.schedule.ui.schedulelist.recyclerview.SessionsAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavouritesFragment : DaggerFragment() {

    @Inject
    lateinit var favouritesViewModelFactory: FavouritesViewModelFactory
    private lateinit var favouritesViewModel: FavouritesViewModel

    @Inject
    lateinit var sessionsAdapter: SessionsAdapter

    private lateinit var favouriteSessions: RecyclerView
    private lateinit var emptyFavouritesDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favouritesViewModel = favouritesViewModelFactory.get(this)
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews(view)
        bindViewModel()
    }

    private fun setUpViews(rootView: View) {
        favouriteSessions = rootView.findViewById(R.id.favouriteSessions)
        emptyFavouritesDescription = rootView.findViewById(R.id.emptyFavouritesDescription)

        favouriteSessions.run {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionsAdapter
        }
    }

    private fun bindViewModel() {
        favouritesViewModel.state.observe(::getLifecycle, ::onFavouritesStateUpdated)
        favouritesViewModel.onFavouritesVisible()
    }

    private fun onFavouritesStateUpdated(favouritesState: FavouritesState) {
        when (favouritesState) {
            FavouritesState.Empty -> onEmptyFavourites()
            is FavouritesState.Content -> onFavouritesContent(favouritesState.sessionRows)
        }
    }

    private fun onEmptyFavourites() {
        favouriteSessions.visibility = View.GONE
        emptyFavouritesDescription.visibility = View.VISIBLE
    }

    private fun onFavouritesContent(sessionRows: List<SessionRow>) {
        emptyFavouritesDescription.visibility = View.GONE
        favouriteSessions.visibility = View.VISIBLE
        sessionsAdapter.submitList(sessionRows)
    }
}