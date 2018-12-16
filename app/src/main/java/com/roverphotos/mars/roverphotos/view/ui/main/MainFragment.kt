package com.roverphotos.mars.roverphotos.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.constant.State
import com.roverphotos.mars.roverphotos.data.Photo
import com.roverphotos.mars.roverphotos.view.adapter.MainPagedListAdapter
import com.roverphotos.mars.roverphotos.view.callback.PhotoClickListener
import com.roverphotos.mars.roverphotos.viewmodel.MainViewModel
import com.roverphotos.mars.roverphotos.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), PhotoClickListener {

    companion object {
        const val EXTRA_PHOTO_BUNDLE = "photo"
        const val START_TRANSITION_NAME = "mini_rover_photo"
        const val END_TRANSITION_NAME = "full_rover_photo"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainPagedListAdapter
    private lateinit var mainLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureRecyclerView()
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(getSelectedRoverName(), getRoverMaxDate()))
            .get(MainViewModel::class.java)
        viewModel.apply {
            itemPagedList.observe(this@MainFragment, Observer<PagedList<Photo>> {
                mainAdapter.submitList(it)
            })

            getState().observe(this@MainFragment, Observer {
                if (it == null)
                    return@Observer
                configureProgressBar(it)
            })
        }
    }

    private fun configureProgressBar(state: State) {
        progress.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
    }

    private fun configureRecyclerView() {
        mainAdapter = MainPagedListAdapter(context!!, this)
        mainLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            layoutManager = mainLayoutManager
            adapter = mainAdapter
        }
    }

    override fun onPhotoClicked(imageView: ImageView, photo: Photo) {
        imageView.transitionName = START_TRANSITION_NAME + photo.id

        val bundle = Bundle()
        bundle.putSerializable(EXTRA_PHOTO_BUNDLE, photo)
        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement(imageView, END_TRANSITION_NAME + photo.id)
            .build()
        NavHostFragment.findNavController(this@MainFragment)
            .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment().actionId, bundle, null, extras)
    }

    //get selected rover name from navigation safeArgs
    private fun getSelectedRoverName() = MainFragmentArgs.fromBundle(arguments).roverName

    //get selected rover max date from navigation safeArgs
    private fun getRoverMaxDate() = MainFragmentArgs.fromBundle(arguments).roverMaxDate

}
