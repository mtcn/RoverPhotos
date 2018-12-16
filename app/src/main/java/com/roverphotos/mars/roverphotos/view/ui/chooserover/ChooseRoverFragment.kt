package com.roverphotos.mars.roverphotos.view.ui.chooserover

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.data.Rover
import com.roverphotos.mars.roverphotos.view.adapter.ChooseRoverAdapter
import com.roverphotos.mars.roverphotos.view.callback.RoverClickListener
import com.roverphotos.mars.roverphotos.viewmodel.ChooseRoverViewModel
import com.roverphotos.mars.roverphotos.viewmodel.ChooseRoverViewModelFactory
import kotlinx.android.synthetic.main.choose_rover_fragment.*

class ChooseRoverFragment : Fragment(), RoverClickListener {

    companion object {
        const val EXTRA_ROVER_DATA = "roverData"
        fun newInstance() = ChooseRoverFragment()
    }

    private lateinit var viewModel: ChooseRoverViewModel
    private lateinit var chooseRoverAdapter: ChooseRoverAdapter
    private lateinit var chooseRoverLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_rover_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            ChooseRoverViewModelFactory(arguments?.getSerializable(EXTRA_ROVER_DATA) as? ArrayList<Rover>)
        ).get(ChooseRoverViewModel::class.java)
        viewModel.roverLiveData.observe(this, Observer { roverData ->
            if (roverData == null)
                return@Observer

            configureRecyclerView(roverData)
        })
    }

    private fun configureRecyclerView(roverData: ArrayList<Rover>) {
        chooseRoverAdapter = ChooseRoverAdapter(context!!, roverData, this)
        chooseRoverLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            adapter = chooseRoverAdapter
            layoutManager = chooseRoverLayoutManager
        }
    }

    override fun onRoverClicked(rover: Rover) {
        val direction = ChooseRoverFragmentDirections
            .actionChooseRoverFragmentToMainFragment()
            .setRoverName(rover.name)
            .setRoverMaxDate(rover.maxDate)
        NavHostFragment.findNavController(this).navigate(direction)
    }

}
