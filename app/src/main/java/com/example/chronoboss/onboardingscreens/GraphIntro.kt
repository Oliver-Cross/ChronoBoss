package com.example.chronoboss.onboardingscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.chronoboss.R
import kotlinx.android.synthetic.main.fragment_graph_intro.view.*

class GraphIntro : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_graph_intro, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.next_to_bank.setOnClickListener{
            viewPager?.currentItem = 1
        }

        return view
    }

}