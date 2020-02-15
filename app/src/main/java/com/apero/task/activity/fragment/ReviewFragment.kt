package com.apero.task.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apero.task.R
import com.apero.task.activity.adapter.ReviewAdapter

import com.apero.task.activity.model.OverViewModel
import com.apero.task.activity.utils.Common
import com.google.gson.Gson


class ReviewFragment : Fragment() {
    private var mainView: View? = null

    lateinit var ReviewList: MutableList<OverViewModel.Review>
    lateinit var ReviewAdapter: ReviewAdapter
    lateinit var rcvReview: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainView =  inflater.inflate(R.layout.fragment_review, container, false)
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        setUpRCv()
    }

    fun setUpRCv(){
        rcvReview = mainView!!.findViewById(R.id.rcvReviews)
        rcvReview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        var gson = Gson()

        val comn = Common().getAsset(requireContext()).toString()

        val model:OverViewModel = gson.fromJson(comn,OverViewModel::class.java)
        ReviewList = arrayListOf()

        model.review!!.forEach {

            ReviewList.add(it)

        }
        ReviewAdapter = ReviewAdapter(ReviewList, requireContext())
        rcvReview.adapter = ReviewAdapter
        ReviewAdapter.notifyDataSetChanged()

    }
}
