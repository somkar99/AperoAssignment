package com.apero.task.activity.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.apero.task.R
import com.apero.task.activity.adapter.AllpagerAdaptorr
import com.apero.task.activity.extensions.LoadImageFromUrl
import com.apero.task.activity.fragment.OverViewFragement
import com.apero.task.activity.fragment.PhotosFragment
import com.apero.task.activity.fragment.ReviewFragment
import com.apero.task.activity.utils.Common
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()
    private var mGoogleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        mGoogleApiClient =
            GoogleApiClient.Builder(applicationContext) //Use app context to prevent leaks using activity
//.enableAutoManage(this /* FragmentActivity */, connectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build()
        /*sign_out_button.setOnClickListener {
            signOut()

        }*/


        var comn = Common().getAsset(this)
        val imgString = comn!!.getString("restaurant_image")


        iv_bg.LoadImageFromUrl(imgString,this@HomeActivity)

      val  bitmap =iv_bg.drawable.toBitmap()

        Palette.from(bitmap).generate(object : Palette.PaletteAsyncListener {

            override fun onGenerated(palette: Palette?) {
                val darkMutedSwatch = palette?.darkMutedSwatch
                    ?: palette?.darkVibrantSwatch ?: return

                val lightMutedSwatch = palette?.lightMutedSwatch
                    ?: palette?.lightVibrantSwatch ?: return




                img_bg.setBackgroundColor(darkMutedSwatch.rgb)


            }
        })





       /* Picasso.with(this@HomeActivity)
            .load(imgString)
            .into(iv_bg)*/


        setupViewPager(pager)
        tlTabLayout.setupWithViewPager(pager)


        ivlogout.setOnClickListener {

            signOut()
        }

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val mainAdapter = AllpagerAdaptorr(supportFragmentManager).apply {
            addFragment(OverViewFragement(), "Overview")
            addFragment(PhotosFragment(), "Photos")
            addFragment(ReviewFragment(), "Reviews")
        }
        viewPager.adapter = mainAdapter
        viewPager.isVerticalScrollBarEnabled =true

    }

    private fun signOut() {
        if (mGoogleApiClient?.isConnected()!!) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient)
            mGoogleApiClient?.disconnect()
            mGoogleApiClient?.connect()
        }
        mAuth.signOut()
        Toast.makeText(this, "Signed Out!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onStart() {
        super.onStart()
        mGoogleApiClient!!.connect()
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient!!.isConnected) {
            mGoogleApiClient!!.disconnect()
        }
    }



}
