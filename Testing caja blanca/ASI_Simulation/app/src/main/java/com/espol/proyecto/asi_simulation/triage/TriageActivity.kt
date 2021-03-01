package com.espol.proyecto.asi_simulation.triage

import android.os.Bundle
import com.espol.proyecto.asi_simulation.BaseActivity
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.fragment.TriageFragment

class TriageActivity : BaseActivity() {

    companion object{
        const val LOCATION_REQUEST_CODE = 593
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triage)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.triage_fragment_container, TriageFragment.newInstance())
                    .commit()
        }
    }
}