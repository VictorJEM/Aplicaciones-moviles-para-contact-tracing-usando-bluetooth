package com.espol.proyecto.asi_simulation.triage.adapter


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.espol.proyecto.asi_simulation.R
import com.espol.proyecto.asi_simulation.triage.fragment.*

class TriageAdapter (val fragment : TriageFragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 8

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return TriageUserDataFragment(fragment)
            1 -> return TriageQuestionSimpleFragment(fragment,
                    "¿Como te sientes hoy?",
                    "Elija una opción",
                    "Me siento bien, no tengo síntomas", "No me siento bien hoy")
            2 -> return TriageQuestionIconFragment(fragment, "¿Tienes una fiebre alta - una temperatura sobre los 38 grados Celsius?",
                    "Síntoma de fiebre pueden incluir mejillas sonrojadas, cansancio y estar caliente.",
                    "Si", "No", R.drawable.ic_tracking, 1)
            3 -> return TriageQuestionIconFragment(fragment, "¿Tienes dificultad respirando?",
                    "Esto puede ser jadeo o un sentimiento de que no puedes llenar tus pulmones.",
                    "Si", "No", R.drawable.ic_tracking, 2)
            4 -> return TriageQuestionIconFragment(fragment, "¿Tienes algún tipo de tos?",
                    "",
                    "Si", "No", R.drawable.ic_tracking, 3)
            5 -> return TriageQuestionIconFragment(fragment, "¿Tienes algún síntoma de gripe o resfriado?",
                    "Por ejemplo, congestión, dolor de garganta, dolor de cabeza.",
                    "Si", "No", R.drawable.ic_tracking, 4)
            6-> return TriageHistoryFragment(fragment)
            7-> return TriageFinishedFragment(fragment)
            else ->{
                throw IllegalArgumentException("There is no fragment for view pager position $position")
            }
        }
    }
}