package com.espol.proyecto.asi_simulation.triage

import android.app.Activity
import android.os.Bundle
import com.espol.proyecto.asi_simulation.BaseActivity
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.fragment.TriageCantonFragment
import com.espol.proyecto.asi_simulation.triage.fragment.TriageParishFragment
import com.espol.proyecto.asi_simulation.triage.fragment.TriageProvinceFragment
import com.espol.proyecto.asi_simulation.triage.model.Canton
import com.espol.proyecto.asi_simulation.triage.model.Parish
import com.espol.proyecto.asi_simulation.triage.model.Province

class TriageLocationActivity : BaseActivity() {

    private lateinit var locationData : List<Province>

    var selectedProvincePos : Int = -1
    var selectedCantonPos   : Int = -1
    var selectedParishPos   : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triage_location)

        val list by lazy {
            LocationUtils.getProvinceList(this)
        }

        locationData = list

        goToProvinceLocationFragment()

    }

    private fun goToProvinceLocationFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.triage_location_fragment_container, TriageProvinceFragment("¿En qué provincia vives?", "Selecciona la provincia donde vives para ayudar al Ministerio de Salud Pública a que brinde la mejor asistencia posible."))
                .addToBackStack(TriageProvinceFragment::class.java.canonicalName)
                .commit()
    }

    fun goToCantonLocationFragment() {
        if (selectedProvincePos != -1){
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.triage_location_fragment_container, TriageCantonFragment("¿En qué cantón vives?", "Selecciona el cantón donde vives para ayudar al Ministerio de Salud Pública a que brinde la mejor asistencia posible."))
                    .addToBackStack(TriageCantonFragment::class.java.canonicalName)
                    .commit()
        }
    }

    fun goToParishLocationFragment() {
        if (selectedCantonPos != -1) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.triage_location_fragment_container, TriageParishFragment("¿En qué parroquia vives?", "Selecciona la parroquia donde vives para ayudar al Ministerio de Salud Pública a que brinde la mejor asistencia posible."))
                    .addToBackStack(TriageParishFragment::class.java.canonicalName)
                    .commit()
        }
    }


    fun endSelection() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun getProvinces() : List<Province>{
        return locationData
    }

    fun getCantonsOfSelectedProvince() : List<Canton>{
        return locationData[selectedProvincePos].cantons
    }

    fun getParishesOfSelectedCanton() : List<Parish>{
        return locationData[selectedProvincePos].cantons[selectedCantonPos].parishes
    }

}