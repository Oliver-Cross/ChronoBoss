package com.example.chronoboss.onboardingscreens

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chronoboss.R
import kotlinx.coroutines.delay

class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({
            if(isOnBoardingFinished()){
                findNavController().navigate(R.id.action_splashScreen_to_homeFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreen_to_viewPagerFragment)
            }
        }, 2000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun isOnBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Tutorial_Finished", false)

    }
}