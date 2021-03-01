package com.espol.proyecto.asi_simulation.triage.viewholder

import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.extension.function.setSafeOnClickListener
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import com.espol.proyecto.asi_simulation.triage.model.Parish

class ParishViewHolder(
        itemView: View,
        val adapter: GenericListAdapter<Parish>
) : RecyclerView.ViewHolder(itemView), GenericListAdapter.Binder<Parish> {

    var radioButton : RadioButton = itemView.findViewById(R.id.location_list_item_rbtn)

    init {
        itemView.setSafeOnClickListener {
            adapter.onItemClick(adapterPosition)
        }
    }

    override fun bind(data: Parish, position: Int) {
        radioButton.text = data.name
        radioButton.isChecked = position == adapter.selectedPosition
    }

}