package com.yandey.githubuser.presentation.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yandey.githubuser.FollowersFragment
import com.yandey.githubuser.FollowingFragment

class SectionPagerAdapter(
    activity: AppCompatActivity,
    bundle: Bundle
) : FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = bundle

    init {
        fragmentBundle = bundle
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(
        position: Int
    ): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}