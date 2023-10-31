package com.example.handybook.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.handybook.ui.CommentsFragment
import com.example.handybook.ui.InfoFragment
import com.example.handybook.ui.QuotesFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            InfoFragment()
        } else if (position == 1) {
            CommentsFragment()
        }else
            QuotesFragment()
    }
}