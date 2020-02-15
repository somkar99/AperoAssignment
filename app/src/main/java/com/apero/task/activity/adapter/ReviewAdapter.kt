package com.apero.task.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apero.task.R
import com.apero.task.activity.extensions.LoadImageFromUrl
import com.apero.task.activity.model.OverViewModel
import com.github.siyamed.shapeimageview.CircularImageView
import kotlinx.android.synthetic.main.fragment_over_view_fragement.view.*
import kotlinx.android.synthetic.main.review_row.view.*

class ReviewAdapter(val dataSet: MutableList<OverViewModel.Review>, val context: Context) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.review_row, parent, false))

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.apply {

            val model = dataSet[position]
            profileImage.LoadImageFromUrl(model.profile, context)
            Name.text = model.name
            Date.text = model.createdAt
            Desc.text = model.review
            Likes.text = model.likeCount.toString()
            Helpful.text = model.helpfulCount.toString()
            RatingCount.text = model.rating.toString()
            Rating.rating = model.rating!!.toFloat()


        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var profileImage: CircularImageView = view.ivReviewProfile
        var Name: TextView = view.tvRevName
        var Date: TextView = view.tvRevDate
        var Desc: TextView = view.tvRevDesc
        var Likes: TextView = view.tvLikes
        var Helpful: TextView = view.tvHelp
        var RatingCount: TextView = view.tvRatCount
        var Rating: RatingBar = view.ctrReviewRatingbar

    }
}