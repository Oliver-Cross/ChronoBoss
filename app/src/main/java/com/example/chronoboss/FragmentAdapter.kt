package com.example.chronoboss

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chronoboss.homeFragment.HomeFragment
import com.example.chronoboss.progressFragment.ProgressFragment
import com.example.chronoboss.settingsFragment.SettingsFragment
import com.example.chronoboss.statsFragment.StatsFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position){
            1 -> return StatsFragment()
            2 -> return ProgressFragment()
            3 -> return SettingsFragment()
            else -> return HomeFragment()
        }
    }

}