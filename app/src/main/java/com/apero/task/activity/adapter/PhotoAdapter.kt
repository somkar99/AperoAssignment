package com.apero.task.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apero.task.R
import com.apero.task.activity.extensions.LoadImageFromUrl
import com.apero.task.activity.model.OverViewModel
import kotlinx.android.synthetic.main.photo_row.view.*
import kotlinx.android.synthetic.main.review_row.view.*

class PhotoAdapter(val dataSet:MutableList<OverViewModel.Photo>, val context: Context):RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_row, parent, false))


    override fun getItemCount(): Int = dataSet.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.let {

            val model = dataSet.get(position)

            holder.Images.LoadImageFromUrl(model.photo,context)


        }
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {

        var Images: ImageView = view.ivImg

    }
}