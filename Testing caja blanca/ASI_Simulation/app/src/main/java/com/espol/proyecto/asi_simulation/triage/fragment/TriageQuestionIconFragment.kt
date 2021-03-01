package com.espol.proyecto.asi_simulation.triage.fragment

import android.os.Bundle
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.espol.proyecto.asi_simulation.R
//import com.espol.proyecto.asi_simulation.triage.persistance.AsiDatabase
//import com.espol.proyecto.asi_simulation.triage.persistance.Triage
import kotlinx.android.synthetic.main.card_triage_question_icon.*
//import java.text.SimpleDateFormat
//import java.util.*

//OJO CON ESTA CLASE, GENEREA DUDAS...

class TriageQuestionIconFragment(
        private val pFragment : TriageFragment,
        private val title : String,
        private val description : String,
        private val positiveButtonText : String,
        private val negativeButtonText : String,
        private val iconSrc : Int,
        private val s_id: Int
) : TriageQuestionSimpleFragment(pFragment, title, description, positiveButtonText, negativeButtonText){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.card_triage_question_icon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pFragment.setTriageMainSubtitle(R.string.triage_userdata_subtitle)

        triage_question_icon_img.setImageResource(iconSrc)

        triage_question_yes_btn.setOnClickListener {
            pFragment.answers[s_id - 1] = s_id

            if (s_id == 4) pFragment.persistTriage()
            //AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().insertAll(Triage(null, s_id, date))
            //Log.d("db", AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().getAll().toString())
            pFragment.continueToNextPage()
        }

        triage_question_no_btn.setOnClickListener {
            pFragment.answers[s_id - 1] = 0
            if (s_id == 4) pFragment.persistTriage()
            //AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().insertAll(Triage(null, null, date))
            pFragment.continueToNextPage()
        }
    }
}