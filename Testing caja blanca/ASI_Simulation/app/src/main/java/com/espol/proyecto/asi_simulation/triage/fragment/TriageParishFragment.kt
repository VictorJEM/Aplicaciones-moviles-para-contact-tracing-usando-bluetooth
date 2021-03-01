package com.espol.proyecto.asi_simulation.triage.fragment


import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.storage.SecureStorage
import com.espol.proyecto.asi_simulation.triage.TriageLocationActivity
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import com.espol.proyecto.asi_simulation.triage.model.Parish
import com.espol.proyecto.asi_simulation.triage.viewholder.ParishViewHolder

class TriageParishFragment (title: String, subtitle : String) : GenericLocationFragment<Parish>(title, subtitle) {

    override fun getListAdapter(): GenericListAdapter<Parish> {
        return object : GenericListAdapter<Parish>((activity as TriageLocationActivity).getParishesOfSelectedCanton(), this) {
            override fun getLayoutId(position: Int, obj: Parish): Int {
                return R.layout.location_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ParishViewHolder(view, this)
            }
        }
    }

    override fun saveSelected() {
        if (selectedItem != null){
            SecureStorage.getInstance(activity).parishCode = selectedItem!!.code
            SecureStorage.getInstance(activity).parishName = selectedItem!!.name
            (activity as TriageLocationActivity).selectedParishPos = adapter.selectedPosition
            (activity as TriageLocationActivity).endSelection()
        }else{
            Toast.makeText(activity, "Seleccione una Parroquia", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(item: Parish) {
        selectedItem = item
    }
}