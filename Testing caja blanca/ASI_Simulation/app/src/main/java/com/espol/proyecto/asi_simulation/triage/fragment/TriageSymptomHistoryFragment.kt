package com.espol.proyecto.asi_simulation.triage.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.adapter.GenericClickListener
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import com.espol.proyecto.asi_simulation.triage.model.TriageHistoryLog
import com.espol.proyecto.asi_simulation.triage.persistance.AsiDatabase
import com.espol.proyecto.asi_simulation.triage.viewholder.TriageHistoryViewHolder
import kotlinx.android.synthetic.main.card_triage_history_list.*

class TriageHistoryFragment(
        private val pFragment : TriageFragment
) : Fragment(R.layout.card_triage_history_list) {

    lateinit var adapter: GenericListAdapter<TriageHistoryLog>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        triage_history_continue_btn.setOnClickListener{
            pFragment.continueToThanks()
        }
    }

    override fun onResume() {
        super.onResume()

        pFragment.setTriageMainSubtitle("Tu historial de síntomas")

        val triageHistoryLogList = mutableListOf<TriageHistoryLog>()
        val listDates = AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().getAll()
        val symptomsArray = resources.getStringArray(R.array.triage_symptoms)

        listDates.forEach {
            val listSymptoms = mutableListOf<String>()
            if (it.s1_index!! > 0) listSymptoms.add(symptomsArray[it.s1_index])
            if (it.s2_index!! > 0) listSymptoms.add(symptomsArray[it.s2_index])
            if (it.s3_index!! > 0) listSymptoms.add(symptomsArray[it.s3_index])
            if (it.s4_index!! > 0) listSymptoms.add(symptomsArray[it.s4_index])
            triageHistoryLogList.add(TriageHistoryLog(it.date!!, listSymptoms))
        }

        if (triageHistoryLogList.size == 0){
            triage_history_empty_text.visibility = View.VISIBLE
        }

        Log.d("db", triageHistoryLogList.toString())

        val click = object : GenericClickListener<TriageHistoryLog> {
            override fun onClick(item: TriageHistoryLog) {}
        }

        adapter = object: GenericListAdapter<TriageHistoryLog>(triageHistoryLogList, click){
            override fun getLayoutId(position: Int, obj: TriageHistoryLog): Int {
                return R.layout.triage_history_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return TriageHistoryViewHolder(view, this)
            }
        }

        triage_history_list_rclv.adapter = adapter

    }

}