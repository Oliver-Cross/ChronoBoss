package com.example.chronoboss.database

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.R
import kotlinx.android.synthetic.main.fragment_database_test.*
import kotlinx.android.synthetic.main.fragment_database_test.view.*

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
        val dayId = addDayId_et.text
        val timeWasted = addTimeWasted_et.text
        val timeLimit = addTimeLimit_et.text
        val application = addApplication_et.text.toString()

        if(inputCheck(dayId, timeWasted, timeLimit, application)){
            //Create day object
            val day = Day(0, 20, 40, "Chrome")
            // Add data to database
            mDayViewModel.addDay(day)
            Toast.makeText(requireContext(), "Successfuly added!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(dayId: Editable, timeWasted: Editable, timeLimit: Editable, application: String): Boolean{
        return !(TextUtils.isEmpty(application) && timeWasted.isEmpty() && timeLimit.isEmpty() && dayId.isEmpty())
    }


}

