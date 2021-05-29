package com.example.chronoboss

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.text.TextUtils.replace
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val icon: ImageView = view.findViewById(R.id.app_icon)
        val chrome: Drawable? = activity?.packageManager?.getApplicationIcon("com.android.chrome")
        icon.setImageDrawable(chrome)

        val progressFragment = ProgressFragment()

        /*
        bottomNavigationViewHome.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.progressFragment -> setCurrentFragment(progressFragment)
            }
            true
        }
        */

        return view
    }


    /*fun getIcon(): Drawable? {
        return activity?.packageManager?.getApplicationIcon("com.android.chrome")
    } */

    /*fun setIcon() {
        val icon = activity?.packageManager?.getApplicationIcon("com.android.chrome")
        val view: ImageView? = activity?.findViewById(R.id.app_icon)
        if (view != null) {
            view.setImageDrawable(icon)
        }
    } */
}


