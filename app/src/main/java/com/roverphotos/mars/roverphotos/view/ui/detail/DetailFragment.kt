package com.roverphotos.mars.roverphotos.view.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.data.Photo
import com.roverphotos.mars.roverphotos.util.AppUtility
import com.roverphotos.mars.roverphotos.view.ui.main.MainFragment.Companion.END_TRANSITION_NAME
import com.roverphotos.mars.roverphotos.view.ui.main.MainFragment.Companion.EXTRA_PHOTO_BUNDLE
import com.roverphotos.mars.roverphotos.viewmodel.DetailViewModel
import com.roverphotos.mars.roverphotos.viewmodel.DetailViewModelFactory
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private val selectedPhoto by lazy { (arguments?.getSerializable(EXTRA_PHOTO_BUNDLE) as Photo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        with(TransitionInflater.from(context).inflateTransition(R.transition.move)) {
            sharedElementEnterTransition = this
            // sharedElementReturnTransition not working because
            // there is problem with back shared transitions to RecyclerView when using
            // setReorderingAllowed=true on FragmentTransaction,
            // the problem that in Navigation component this flag is always set true
            // https://issuetracker.google.com/issues/118475573
            sharedElementReturnTransition = this
        }
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, DetailViewModelFactory(selectedPhoto)).get(DetailViewModel::class.java)
        viewModel.photoLiveData.observe(this, Observer { selectedPhoto ->
            if (selectedPhoto == null)
                return@Observer

            configureViews(selectedPhoto)
        })
    }

    private fun configureViews(selectedPhoto: Photo) {
        detailImageView.transitionName = END_TRANSITION_NAME + selectedPhoto.id
        Glide.with(this).load(selectedPhoto.image).into(detailImageView)
        photoDescription.text = AppUtility.getInstance().getPhotoDescription(context!!, selectedPhoto)
    }

}
