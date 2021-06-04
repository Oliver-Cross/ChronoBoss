package com.example.chronoboss.onboardingscreens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.chronoboss.R
import kotlinx.android.synthetic.main.fragment_bank_intro.view.*
import kotlinx.android.synthetic.main.fragment_graph_intro.view.*

class BankIntro : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank_intro, container, false)


        view.finish_intro_button.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
            onBoardingFinished()
        }


        return view
    }

    private fun onBoardingFinished(){
        val sharedpref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.putBoolean("Tutorial_Finished", true)
        editor.apply()
    }

}