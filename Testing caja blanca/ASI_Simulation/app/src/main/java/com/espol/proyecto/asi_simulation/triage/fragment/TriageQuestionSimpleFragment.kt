package com.espol.proyecto.asi_simulation.triage.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.persistance.AsiDatabase
import kotlinx.android.synthetic.main.card_triage_question_simple.*
import org.dpppt.android.sdk.internal.logger.Logger

open class TriageQuestionSimpleFragment (
        private val pFragment : TriageFragment,
        private val title : String,
        private val description : String,
        private val positiveButtonText : String,
        private val negativeButtonText : String) : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.card_triage_question_simple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pFragment.setTriageMainSubtitle(R.string.triage_userdata_subtitle)

        triage_question_text.text = title
        triage_question_description_text.text = description
        triage_question_yes_btn.text = positiveButtonText
        triage_question_no_btn.text = negativeButtonText


        triage_question_yes_btn.setOnClickListener{
            Logger.d("Triage", "Pregunta: Si")
            val result = AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().getAllByDate(pFragment.currentDate)

            if (result.isEmpty()){
                pFragment.persistEmptyTriage()
                pFragment.continueToHistory()
            }else{
                showOverwriteSymptomsAlert(DialogInterface.OnClickListener { _, _ ->
                    pFragment.deleteTodaysTriage()
                    pFragment.persistEmptyTriage()
                    pFragment.continueToHistory()
                })
            }
        }

        triage_question_no_btn.setOnClickListener{
            Logger.d("Triage", "Pregunta: No")
            val result = AsiDatabase.getInstance(context?.applicationContext!!).triageDAO().getAllByDate(pFragment.currentDate)

            if (result.isEmpty()){
                pFragment.continueToNextPage()
            }else{
                showOverwriteSymptomsAlert(DialogInterface.OnClickListener { _, _ ->
                    pFragment.deleteTodaysTriage()
                    pFragment.continueToNextPage()
                })
            }

        }
    }

    fun showOverwriteSymptomsAlert(positiveListener : DialogInterface.OnClickListener){
        val dialog = AlertDialog.Builder(pFragment.requireContext(), R.style.NextStep_AlertDialogStyle)
                .setTitle("¡Atención!")
                .setMessage("Ya registraste tus síntomas el día de hoy (${pFragment.currentDate}). ¿Quieres borrar el registro anterior?")
                .setPositiveButton("Si", positiveListener)
                .setNegativeButton("No") { dialog1: DialogInterface, _: Int -> dialog1.dismiss() }
                .create()
        dialog.setOnShowListener { dialog1: DialogInterface? ->
            val titleView = dialog.findViewById<TextView>(R.id.alertTitle)
            titleView!!.maxLines = 5
        }
        dialog.show()
    }


}