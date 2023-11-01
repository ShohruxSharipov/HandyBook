package com.example.handybook.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.handybook.ui.CommentFragment
import com.example.handybook.ui.InfoFragment
import com.example.handybook.ui.QuotesFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, var book_id: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                InfoFragment(book_id)
            }

            1 -> {
                CommentFragment(book_id)
            }

            2 -> QuotesFragment()
            else -> InfoFragment(book_id)
        }
    }

}