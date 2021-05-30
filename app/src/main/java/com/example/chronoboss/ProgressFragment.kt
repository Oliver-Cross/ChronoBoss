package com.example.chronoboss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.database.DayViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ProgressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProgressFragment : Fragment() {

    private lateinit var mDayViewModel: DayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)


        mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)

        mDayViewModel.readAllData.observe(viewLifecycleOwner, Observer{ day ->
            adapter.setData(day)
        })


    }


}