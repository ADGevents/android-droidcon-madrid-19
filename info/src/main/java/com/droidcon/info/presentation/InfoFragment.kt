package com.droidcon.info.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.commons.recyclerview.setDivider
import com.droidcon.info.R
import com.droidcon.info.presentation.viewmodel.InfoFragmentViewModel
import com.droidcon.info.presentation.viewmodel.InfoFragmentViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfoFragment : DaggerFragment() {

    @Inject
    lateinit var infoFragmentViewModelFactory: InfoFragmentViewModelFactory
    private lateinit var infoFragmentViewModel: InfoFragmentViewModel

    @Inject
    lateinit var infoOptionsAdapter: InfoOptionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        infoFragmentViewModel = infoFragmentViewModelFactory.get(this)
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.infoOptions).run {
            layoutManager = LinearLayoutManager(context)
            adapter = infoOptionsAdapter
            setDivider(R.drawable.row_divider)
        }

        bindViewModel()
    }

    private fun bindViewModel() {
        infoFragmentViewModel.infoState.observe(::getLifecycle) { state ->
            infoOptionsAdapter.submitList(state.infoOptions)
        }
        infoFragmentViewModel.infoEffects.observe(::getLifecycle, ::onInfoEffect)
        infoFragmentViewModel.onInfoVisible()
    }

    private fun onInfoEffect(infoEffect: InfoEffect) {
        when (infoEffect) {
            is InfoEffect.NavigateToUri -> navigateToUri(infoEffect.url)
            is InfoEffect.NavigateToMaps -> navigateToMaps(infoEffect.mapsInfo)
            InfoEffect.NavigateToContactsOfInterest -> navigateToContactsOfInterest()
        }
    }

    private fun navigateToUri(uri: Uri) {
        Intent(Intent.ACTION_VIEW).run {
            data = uri
            startActivity(this)
        }
    }

    private fun navigateToMaps(mapsInfo: Uri) {
        Intent(Intent.ACTION_VIEW, mapsInfo).run {
            setPackage("com.google.android.apps.maps")
            startActivity(this)
        }
    }

    private fun navigateToContactsOfInterest() {
        findNavController().navigate(
            InfoFragmentDirections.actionInfoFragmentToContactsOfInterestFragment()
        )
    }
}