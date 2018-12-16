package com.roverphotos.mars.roverphotos.view.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.data.Photo
import com.roverphotos.mars.roverphotos.view.callback.PhotoClickListener
import kotlinx.android.synthetic.main.main_photo_item.view.*

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class MainPagedListAdapter constructor(val context: Context, val clickListener: PhotoClickListener) :
    PagedListAdapter<Photo, MainPagedListAdapter.MainViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bindViews(photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MainViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.main_photo_item, parent, false)
        return MainViewHolder(v)
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViews(photo: Photo?) {
            if (photo == null)
                return

            itemView.textView.text = photo.camera.fullName
            val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(itemView).load(photo.image).apply(options).into(itemView.marsImageView)

            itemView.setOnClickListener {
                clickListener.onPhotoClicked(itemView.marsImageView, photo)
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }

}