package com.apero.task.activity.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AllpagerAdaptorr(fm:FragmentManager): FragmentPagerAdapter(fm) {
    var mNooftabs = 0
    private var mFragmentList: ArrayList<Fragment> = ArrayList()
    private var mFragmentTitleList: ArrayList<String> = ArrayList()


    fun addFragment(
        fragment: Fragment?,
        title: String?
    ) {
        mFragmentList.add(fragment!!)
        mFragmentTitleList.add(title!!)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position];
    }

    override fun getCount() = mFragmentList.size
}