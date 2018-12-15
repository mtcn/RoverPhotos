package com.roverphotos.mars.roverphotos.view.ui.chooserover

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.viewmodel.ChooseRoverViewModel

class ChooseRoverFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseRoverFragment()
    }

    private lateinit var viewModel: ChooseRoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_rover_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChooseRoverViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
