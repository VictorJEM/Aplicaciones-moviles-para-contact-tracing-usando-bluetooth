package com.espol.proyecto.asi_simulation.triage.fragment

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.storage.SecureStorage
import com.espol.proyecto.asi_simulation.triage.TriageLocationActivity
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import com.espol.proyecto.asi_simulation.triage.model.Canton
import com.espol.proyecto.asi_simulation.triage.viewholder.CantonViewHolder

class TriageCantonFragment (title: String, subtitle : String) : GenericLocationFragment<Canton>(title, subtitle) {

    override fun getListAdapter(): GenericListAdapter<Canton> {
        return object : GenericListAdapter<Canton>((activity as TriageLocationActivity).getCantonsOfSelectedProvince(), this) {
            override fun getLayoutId(position: Int, obj: Canton): Int {
                return R.layout.location_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return CantonViewHolder(view, this)
            }
        }
    }

    override fun saveSelected() {
        if (selectedItem != null){
            SecureStorage.getInstance(activity).cantonCode = selectedItem?.code!!
            SecureStorage.getInstance(activity).cantonName = selectedItem?.name!!
            if (selectedItem!!.code != -1) {
                (activity as TriageLocationActivity).selectedCantonPos = adapter.selectedPosition
                (activity as TriageLocationActivity).goToParishLocationFragment()
            }else{
                SecureStorage.getInstance(activity).parishCode = selectedItem?.code!!
                SecureStorage.getInstance(activity).parishName = selectedItem?.name!!
                (activity as TriageLocationActivity).endSelection()
            }
        }else{
            Toast.makeText(activity, "Seleccione un Cantón", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(item: Canton) {
        selectedItem = item
    }
}