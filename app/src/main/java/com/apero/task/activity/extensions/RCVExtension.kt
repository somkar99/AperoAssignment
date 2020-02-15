package com.apero.task.activity.extensions

/**
 * Created by niranjanshah on 30/01/18.
 */

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener {
    fun onItemClicked(view: View, position: Int)
}

fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {


        override fun onChildViewDetachedFromWindow(p0: View) {
            p0?.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(p0: View) {
            p0?.setOnClickListener({
                val holder = getChildViewHolder(p0)
                onClickListener.onItemClicked(p0, holder.adapterPosition)
            })
        }
    })
}
