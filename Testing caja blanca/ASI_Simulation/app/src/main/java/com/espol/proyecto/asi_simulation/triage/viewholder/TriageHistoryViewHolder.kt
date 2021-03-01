package com.espol.proyecto.asi_simulation.triage.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.adapter.GenericClickListener
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import com.espol.proyecto.asi_simulation.triage.model.TriageHistoryLog
//import com.espol.proyecto.asi_simulation.triage.persistance.Triage

class TriageHistoryViewHolder(itemView: View, adapter : GenericListAdapter<TriageHistoryLog>) : RecyclerView.ViewHolder(itemView), GenericListAdapter.Binder<TriageHistoryLog> {

    var totalText: TextView = itemView.findViewById(R.id.triage_history_list_item_total_text)
    var symptomText: TextView = itemView.findViewById(R.id.triage_history_list_item_symptoms_txt)
    var dateText: TextView = itemView.findViewById(R.id.triage_history_list_item_date_txt)
    var icon: ImageView = itemView.findViewById(R.id.triage_history_list_item_icon_img)
    var symptomsRecycler : RecyclerView = itemView.findViewById(R.id.triage_history_subitem_list_rclv)


    override fun bind(data: TriageHistoryLog, position : Int) {
        totalText.text = data.symptoms.count().toString()
        symptomText.text = when (data.symptoms.count()){
            1 -> "Síntoma"
            else -> "Síntomas"
        }
        dateText.text = data.triageDate
        if (data.symptoms.count() > 0){
            icon.setImageResource(R.drawable.ic_warning_red)
        }

        val click = object : GenericClickListener<String> {
            override fun onClick(item: String) {}
        }

        val subAdapter = object : GenericListAdapter<String>(data.symptoms, click) {
            override fun getLayoutId(position: Int, obj: String): Int {
                return R.layout.triage_history_list_subitem
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return SymptomViewHolder(view, this)
            }

        }

        symptomsRecycler.adapter = subAdapter
    }
}