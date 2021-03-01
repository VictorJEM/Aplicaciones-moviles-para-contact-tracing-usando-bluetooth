package com.espol.proyecto.asi_simulation.triage.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.TriageActivity
import com.espol.proyecto.asi_simulation.triage.TriageLocationActivity
import com.espol.proyecto.asi_simulation.triage.adapter.TriageAdapter
import com.espol.proyecto.asi_simulation.triage.persistance.AsiDatabase
import com.espol.proyecto.asi_simulation.triage.persistance.Triage
import kotlinx.android.synthetic.main.fragment_triage_main.*
import java.text.SimpleDateFormat
import java.util.*


class TriageFragment : Fragment() {

    private lateinit var layout : View
    private lateinit var adapter : TriageAdapter

    lateinit var currentDate : String
    var answers = intArrayOf(0, 0, 0, 0)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_triage_main, container, false)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        currentDate = simpleDateFormat.format(Date())

        triage_toolbar.setNavigationOnClickListener {
            activity?.finish()
        }

        adapter = TriageAdapter(this)
        triage_pager.isUserInputEnabled = false
        triage_pager.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() : TriageFragment {
            return TriageFragment()
        }
    }

    fun setTriageMainSubtitle(id : Int){
        triage_main_subtitle.text = context?.getString(id)
    }

    fun setTriageMainSubtitle(text : String){
        triage_main_subtitle.text = text
    }

    fun continueToNextPage() {
        val currentItem: Int = triage_pager.currentItem
        if (currentItem < adapter.itemCount - 1) {
            triage_pager.setCurrentItem(currentItem + 1, true)
        } else {
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
    }

    fun continueToThanks(){
        triage_pager.setCurrentItem(7, true)
    }

    fun continueToHistory(){
        triage_pager.setCurrentItem(6, true)
    }

    fun goToLocationSelector() {

        startActivityForResult(Intent(activity, TriageLocationActivity::class.java), TriageActivity.LOCATION_REQUEST_CODE)

    }

    fun persistEmptyTriage(){
        AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().insertAll(
                Triage(null,
                        currentDate,
                        0,
                        0,
                        0,
                        0))
    }

    fun persistTriage(){
        AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().insertAll(
                Triage(null,
                        currentDate,
                        answers[0],
                        answers[1],
                        answers[2],
                        answers[3]))
    }

    fun deleteTodaysTriage(){
        AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().deleteByDate(currentDate)
    }

}