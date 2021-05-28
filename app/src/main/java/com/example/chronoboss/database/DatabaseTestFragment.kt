package com.example.chronoboss.database

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DatabaseTestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DatabaseTestFragment : Fragment() {

    private lateinit var mDayViewModel: DayViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_database_test, container, false)

        mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)

        view.add_btn.setOnClickListener{
            insertDataToDatabase()
        }

        return view



    }

    private fun insertDataToDatabase() {
        val timeWasted = addTimeWasted_et.text
        val timeLimit = addTimeLimit_et.text
        val application = addApplication.text.toString()

        if(inputCheck(application, timeWasted, timeLimit)){
            //Create day object
            val day = Day(0, )
        }
    }

    private fun inputCheck(application: String, timeWasted: Editable, timeLimit: Editable): Boolean{
        return !(TextUtils.isEmpty(application) && timeWasted.isEmpty() && timeLimit.isEmpty())
    }


}

