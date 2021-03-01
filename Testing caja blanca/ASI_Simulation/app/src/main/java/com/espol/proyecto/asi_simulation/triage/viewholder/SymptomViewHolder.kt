package com.espol.proyecto.asi_simulation.triage.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter

class SymptomViewHolder(itemView: View, adapter : GenericListAdapter<String>) : RecyclerView.ViewHolder(itemView), GenericListAdapter.Binder<String> {

    var symptomText: TextView = itemView.findViewById(R.id.triage_history_list_subitem_symptom_name_txt)
    var icon: ImageView = itemView.findViewById(R.id.triage_history_list_subitem_symptom_icon_img)

    override fun bind(data: String, position: Int) {
        symptomText.text = data
        //icon.setImageResource(R.drawable.ic_tracking)
    }
}