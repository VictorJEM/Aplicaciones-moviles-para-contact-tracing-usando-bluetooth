package com.espol.proyecto.asi_simulation.triage.fragment




import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.storage.SecureStorage
import com.espol.proyecto.asi_simulation.triage.TriageLocationActivity
import com.espol.proyecto.asi_simulation.triage.adapter.GenericListAdapter
import com.espol.proyecto.asi_simulation.triage.model.Province
import com.espol.proyecto.asi_simulation.triage.viewholder.ProvinceViewHolder

class TriageProvinceFragment(title: String, subtitle : String) : GenericLocationFragment<Province>(title, subtitle) {

    override fun getListAdapter(): GenericListAdapter<Province> {
        return object : GenericListAdapter<Province>((activity as TriageLocationActivity).getProvinces(), this) {
            override fun getLayoutId(position: Int, obj: Province): Int {
                return R.layout.location_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ProvinceViewHolder(view, this)
            }
        }
    }

    override fun saveSelected() {
        if (selectedItem != null){
            SecureStorage.getInstance(activity).provinceCode = selectedItem!!.code
            SecureStorage.getInstance(activity).provinceName = selectedItem!!.name
            if (selectedItem!!.code != -1){
                (activity as TriageLocationActivity).selectedProvincePos = adapter.selectedPosition
                (activity as TriageLocationActivity).goToCantonLocationFragment()
            }else{
                SecureStorage.getInstance(activity).cantonCode   = selectedItem!!.code
                SecureStorage.getInstance(activity).cantonName   = selectedItem!!.name
                SecureStorage.getInstance(activity).parishCode   = selectedItem!!.code
                SecureStorage.getInstance(activity).parishName   = selectedItem!!.name
                (activity as TriageLocationActivity).endSelection()
            }
        }else{
            Toast.makeText(activity, "Seleccione una Provincia", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(item: Province) {
        selectedItem = item
    }
}