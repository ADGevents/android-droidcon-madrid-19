package com.droidcon.info.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidcon.info.R
import com.droidcon.info.presentation.viewmodel.InfoFragmentViewModel
import com.droidcon.info.presentation.viewmodel.InfoFragmentViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfoFragment private constructor() : DaggerFragment() {

    @Inject
    lateinit var infoFragmentViewModelFactory: InfoFragmentViewModelFactory
    private lateinit var infoFragmentViewModel: InfoFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoFragmentViewModel = infoFragmentViewModelFactory.get(this)
    }

    companion object {
        fun build(): InfoFragment = InfoFragment()
        const val TAG = "fragment:InfoFragment"
    }
}