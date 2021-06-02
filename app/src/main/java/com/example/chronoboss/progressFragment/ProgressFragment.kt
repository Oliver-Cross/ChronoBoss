package com.example.chronoboss.progressFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.R
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDatabase
import com.example.chronoboss.databinding.FragmentHomeBinding
import com.example.chronoboss.databinding.FragmentProgressBinding
import com.example.chronoboss.homeFragment.HomeViewModel
import com.example.chronoboss.homeFragment.HomeViewModelFactory


class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Binding setup
        binding = DataBindingUtil.inflate<FragmentProgressBinding>(inflater,
            R.layout.fragment_progress,container,false)




        // Room database setup
        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getDatabase(application).dayDao()
        val viewModelFactory = ProgressViewModelFactory(dataSource, application)
        val progressViewModel = ViewModelProvider(this, viewModelFactory).get(ProgressViewModel::class.java)



        binding.progressViewModel = progressViewModel


        
        binding.setLifecycleOwner(this)


        return binding.root
    }


}