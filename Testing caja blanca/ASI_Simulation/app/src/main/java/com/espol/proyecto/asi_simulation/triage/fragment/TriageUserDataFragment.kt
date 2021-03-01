package com.espol.proyecto.asi_simulation.triage.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.storage.SecureStorage
import kotlinx.android.synthetic.main.card_triage_userdata.*

class TriageUserDataFragment (private val pFragment : TriageFragment) : Fragment(R.layout.card_triage_userdata) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedGenreIndex = SecureStorage.getInstance(pFragment.context).userGenre
        val savedAgeRangeIndex = SecureStorage.getInstance(pFragment.context).userAgeRange

        if (savedGenreIndex != -1 && savedAgeRangeIndex != -1){
            pFragment.continueToNextPage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pFragment.setTriageMainSubtitle(R.string.triage_userdata_subtitle)

        val ageRangesLabels = resources.getStringArray(R.array.triage_age_spinner_labels)

        val adapter = ArrayAdapter<String>(pFragment.requireContext(), R.layout.support_simple_spinner_dropdown_item, ageRangesLabels)

        triage_userdata_age_spinner.adapter = adapter

        triage_userdata_age_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val index: Int? = parent?.selectedItemPosition
                triage_userdata_age_selector_description_text.text = ageRangesLabels[index!!]
            }

        }

        triage_userdata_age_selector_container.setOnClickListener {
            triage_userdata_age_spinner.performClick()
        }

        triage_userdata_continue_btn.setOnClickListener {
            val checkedIndex = triage_userdata_genre_rgroup.indexOfChild(view.findViewById(triage_userdata_genre_rgroup.checkedRadioButtonId))
            SecureStorage.getInstance(pFragment.context).userGenre = checkedIndex
            SecureStorage.getInstance(pFragment.context).userAgeRange = triage_userdata_age_spinner.selectedItemPosition
            (pFragment).continueToNextPage()
        }

    }
}