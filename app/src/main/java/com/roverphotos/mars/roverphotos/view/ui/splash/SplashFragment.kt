package com.roverphotos.mars.roverphotos.view.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashFragment : Fragment(), MotionLayout.TransitionListener {

    companion object {
        const val EXTRA_ROVER_DATA = "roverData"
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.isComplete.observe(this, Observer { data ->
            if (data == null)
                return@Observer

            val roverData = data.getOrElse(true) {
                null
            }

            if (roverData != null) {
                val bundle = Bundle()
                bundle.putSerializable(EXTRA_ROVER_DATA, roverData)
                Navigation.findNavController(
                    activity!!,
                    R.id.motionLayout
                ).navigate(SplashFragmentDirections.actionSplashFragmentToChooseRoverFragment().actionId, bundle)
            } else
                Snackbar
                    .make(motionLayout, resources.getText(R.string.network_error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(resources.getText(R.string.retry)) {
                        viewModel.getRoversData()
                    }
                    .show()
        })
        startAnimation()
    }

    private fun startAnimation() {
        motionLayout.setTransitionListener(this)
        motionLayout.transitionToEnd()
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        viewModel.animationSubject.onNext(true)
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        //to do nothing
    }
}
