package com.roverphotos.mars.roverphotos.view.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.data.Rover
import com.roverphotos.mars.roverphotos.util.AppUtility
import com.roverphotos.mars.roverphotos.view.callback.RoverClickListener
import kotlinx.android.synthetic.main.choose_rover_item.view.*

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class ChooseRoverAdapter(
    private val context: Context,
    private val roverData: ArrayList<Rover>,
    private val clickListener: RoverClickListener
) :
    RecyclerView.Adapter<ChooseRoverAdapter.ChooseRoverViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ChooseRoverViewHolder =
        ChooseRoverViewHolder(LayoutInflater.from(context).inflate(R.layout.choose_rover_item, parent, false))

    override fun getItemCount() =
        roverData.size

    override fun onBindViewHolder(holder: ChooseRoverViewHolder, pos: Int) =
        holder.bindViewHolder(roverData[pos])

    inner class ChooseRoverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder(rover: Rover) {
            itemView.roverName.text = rover.name
            itemView.roverStatus.setColorFilter(getRoverStatusColor(rover.status), PorterDuff.Mode.SRC_IN)
            itemView.setOnClickListener {
                clickListener.onRoverClicked(rover)
            }
        }

        private fun getRoverStatusColor(status: String): Int {
            val resourceId =
                if (AppUtility.getInstance().isRoverActive(status)) R.color.rover_status_active
                else R.color.rover_status_complete

            return ContextCompat.getColor(context, resourceId)
        }
    }
}