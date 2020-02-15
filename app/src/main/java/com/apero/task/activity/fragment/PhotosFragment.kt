package com.apero.task.activity.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apero.task.R
import com.apero.task.activity.adapter.PhotoAdapter
import com.apero.task.activity.model.OverViewModel
import com.apero.task.activity.utils.Common
import com.google.gson.Gson


class PhotosFragment : Fragment() {
    private var mainView: View? = null

    lateinit var PhotoList: MutableList<OverViewModel.Photo>
    lateinit var PhotoAdapter: PhotoAdapter
    lateinit var rcvPhoto: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainView =  inflater.inflate(R.layout.fragment_photos, container, false)


        return  mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRCv()

    }

    fun setUpRCv(){
        rcvPhoto = mainView!!.findViewById(R.id.rcvPhotos)
        rcvPhoto.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        var gson = Gson()

      val comn = Common().getAsset(requireContext()).toString()

        val model:OverViewModel = gson.fromJson(comn,OverViewModel::class.java)
        PhotoList = arrayListOf()

        model.photo!!.forEach {

            PhotoList.add(it)

        }
        PhotoAdapter = PhotoAdapter(PhotoList, requireContext())
        rcvPhoto.adapter = PhotoAdapter
        PhotoAdapter.notifyDataSetChanged()

    }
}
