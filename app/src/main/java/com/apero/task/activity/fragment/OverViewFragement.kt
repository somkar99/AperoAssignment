package com.apero.task.activity.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apero.task.R
import com.apero.task.activity.activity.WebViewActivity
import com.apero.task.activity.adapter.PhotoAdapter
import com.apero.task.activity.adapter.ReviewAdapter
import com.apero.task.activity.model.OverViewModel
import com.apero.task.activity.utils.Common
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_over_view_fragement.*
import kotlinx.android.synthetic.main.fragment_over_view_fragement.view.*
import org.json.JSONObject


class OverViewFragement : Fragment() {

    private var mainView: View? = null
    var mapView: MapView? = null
    var map: GoogleMap? = null
    var position: LatLng? = null

    var lsEmail = ""
    var lsContact = ""
    var lsRes_Name = ""
    var lsCuisine = ""
    var lsRating = ""
    var lsReview = ""
    var lsWebsite = ""

    var ldLattitude: Double? = 0.0

    var ldLongitude: Double? = 0.0
    var ljReview :JSONObject?= null

    lateinit var PhotoList: MutableList<OverViewModel.Photo>
    lateinit var PhotoAdapter: PhotoAdapter
    lateinit var rcvPhoto: RecyclerView

    lateinit var ReviewList: MutableList<OverViewModel.Review>
    lateinit var ReviewAdapter: ReviewAdapter
    lateinit var rcvReview: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_over_view_fragement, container, false)
        return mainView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var comn = Common().getAsset(requireContext())
        lsRes_Name = comn!!.getString("restaurant_name")
        lsCuisine = comn!!.getString("cuisines")
        lsContact = comn!!.getString("phone_numbers")
        lsRating = comn!!.getString("rating")
        lsReview = comn!!.getString("review_count")
        ldLattitude = comn!!.getString("latitude").toDouble()
        ldLongitude = comn!!.getString("longitude").toDouble()

        position = LatLng(ldLattitude!!, ldLongitude!!)

        ljReview= JSONObject(comn!!.getString("overview"))
        lsEmail = ljReview!!.getString("email")
        lsWebsite = ljReview!!.getString("website")


        mainView.apply {
            tvResName.text = lsRes_Name
            tvCType.text = lsCuisine
            tvCall.text = lsContact

            tvRating.text = lsRating
            tvReviewCount.text = "$lsReview \n Reviews"

            llContact.setOnClickListener {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvCall.text.toString()))
                startActivity(intent)
            }


            llEmail.setOnClickListener {

                var comn = Common().getAsset(requireContext())
                val jObj = JSONObject(comn!!.getString("overview"))
                lsEmail = jObj!!.getString("email")

                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$lsEmail"))
                startActivity(Intent.createChooser(emailIntent, "Send Email Using"), null)
            }


            llWebsite?.setOnClickListener {
                startActivity(Intent(requireActivity(), WebViewActivity::class.java).putExtra("website",lsWebsite))
            }

            mapView = mainView!!.mapview

        }

        // Gets the MapView from the XML layout and creates it
        // Gets the MapView from the XML layout and creates it
        mainView!!.mapview.onCreate(savedInstanceState)

        // Gets to GoogleMap from the MapView and does initialization stuff
        // Gets to GoogleMap from the MapView and does initialization stuff
        mainView!!.mapview.getMapAsync {
            MapsInitializer.initialize(requireActivity())
            setMapLocation(it)
        }

        setUpRCv()
    }

    fun setUpRCv(){
        rcvPhoto = mainView!!.findViewById(R.id.rcvPhoto)
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


        rcvReview = mainView!!.findViewById(R.id.rcvReview)
        rcvReview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


        ReviewList = arrayListOf()

        model.review!!.forEach {

            ReviewList.add(it)

        }
        ReviewAdapter = ReviewAdapter(ReviewList, requireContext())
        rcvReview.adapter = ReviewAdapter
        ReviewAdapter.notifyDataSetChanged()

    }

    private fun setMapLocation(map: GoogleMap) {
        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13f))
            addMarker(MarkerOptions().position(position!!))
            mapType = GoogleMap.MAP_TYPE_NORMAL
            setOnMapClickListener {
                Toast.makeText(requireContext(), "Clicked on map", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

}
