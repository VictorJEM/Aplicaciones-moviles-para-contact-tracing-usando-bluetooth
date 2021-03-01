package com.espol.proyecto.asi_simulation.triage.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.espol.proyecto.asi_simulation.R
import kotlinx.android.synthetic.main.card_triage_finished.*

class TriageFinishedFragment(
        private val pFragment : TriageFragment
) : Fragment(R.layout.card_triage_finished) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        triage_finished_seemore_btn.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.faq_button_url))))
        }

        triage_finished_gotohistory_btn.setOnClickListener{
            pFragment.continueToHistory()
        }
    }

    override fun onResume() {
        super.onResume()
        pFragment.setTriageMainSubtitle("Gracias")
    }

}