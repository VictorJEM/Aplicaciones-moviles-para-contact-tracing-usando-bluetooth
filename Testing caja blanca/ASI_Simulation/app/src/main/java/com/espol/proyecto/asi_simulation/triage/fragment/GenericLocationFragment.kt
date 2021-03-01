package com.espol.proyecto.asi_simulation.triage.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.adapter.GenericClickListener
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import kotlinx.android.synthetic.main.fragment_triage_location.*

abstract class GenericLocationFragment<T>(
        private val title: String,
        private val subtitle: String) : Fragment(R.layout.fragment_triage_location), GenericClickListener<T> {

    lateinit var adapter : GenericListAdapter<T>
    var selectedItem : T? = null


    protected val TAG: String
        get() = this::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //Log.d(TAG, parentFragmentManager.backStackEntryCount.toString())
                if (parentFragmentManager.backStackEntryCount == 1){
                    activity?.finish()
                }else{
                    parentFragmentManager.popBackStack()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onboarding_location_title_text.text = title
        onboarding_location_subtitle_text.text = subtitle
        adapter = getListAdapter()
        onboarding_location_recyclerview.adapter = adapter

        onboarding_location_next_button.setOnClickListener{
            saveSelected()
            Log.d(TAG, parentFragmentManager.backStackEntryCount.toString())
        }
    }

    abstract fun getListAdapter() : GenericListAdapter<T>

    abstract fun saveSelected()
}