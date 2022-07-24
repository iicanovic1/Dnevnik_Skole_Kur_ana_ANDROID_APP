package com.example.dnevnikskolekur_ana.ui.addAnswersToStudent

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Juz
import com.example.dnevnikskolekur_ana.data.local.entities.Juz.*
import com.example.dnevnikskolekur_ana.data.local.entities.Surah
import com.example.dnevnikskolekur_ana.data.local.entities.Surah.*
import com.example.dnevnikskolekur_ana.other.Constants.JUZES
import com.example.dnevnikskolekur_ana.other.Constants.MARKS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_answers_to_student.*

@AndroidEntryPoint
class AddAnswersToStudentFragment : BaseFragment(R.layout.fragment_add_answers_to_student)  {

    private val args: AddAnswersToStudentFragmentArgs by navArgs()

    var ajehMaxSelectedNumber : Int? = null
    var ajehMinSelectedNumber : Int? = null

    val marksList = MARKS

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Punjenje spinnera za Džuzeve
        val juzesList = JUZES
        val juzesAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,juzesList)
        spJuz.adapter = juzesAdapter

        // Punjenje spinnera za Sure nakon odabira JUZA
        spJuz.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val juz = adapterView?.getItemAtPosition(position) as Juz

                val surahList = listOf(SURAH_NULL) + juz.surahs // ako nije odabran JUZ, odabrana sura je SURAH_NULL i odgovor se smatra da je samo za Džuz

                val surahAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,surahList)
                spSurah.adapter = surahAdapter
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        // Punjenje spinnera za Ajete(min i max) nakon odabira SURE
        spSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val surah = adapterView?.getItemAtPosition(position) as Surah
                // ako je odabrana sura SURAH_NULL onda nema ajeta i adapter se puni praznom listom
                val ajehList = surah.numberOfAjat?.let { List(it,{1+it}) }
                val ajehAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,ajehList?: emptyList())
                spAjehMin.adapter = ajehAdapter
                spAjehMax.adapter = ajehAdapter
                ajehMaxSelectedNumber = ajehList?.get(ajehList.size - 1)
                spAjehMax.setSelection(ajehMaxSelectedNumber?.minus(1) ?: return)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        // Kontrola da li je nakon odabira Min ajeta on manji od max ajeta
        spAjehMin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // ovaj dio nulira ajehMinSelectedNumber ako nije odabrana sura
                ajehMinSelectedNumber = adapterView?.getItemAtPosition(position) as Int?
                ajehMinSelectedNumber?.let {
                    if(ajehMaxSelectedNumber!! < ajehMinSelectedNumber!!){
                        spAjehMax.setSelection(ajehMinSelectedNumber!! - 1)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        // Kontrola da li je nakon odabira Max ajeta on veći od min ajeta
        spAjehMax.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // ovaj dio nulira ajehMaxSelectedNumber ako nije odabrana sura
                ajehMaxSelectedNumber = adapterView?.getItemAtPosition(position) as Int?
                ajehMaxSelectedNumber?.let {
                    if(ajehMaxSelectedNumber!! < ajehMinSelectedNumber!!){
                        spAjehMin.setSelection(ajehMaxSelectedNumber!! - 1)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        // Punjenje spinnera za Ocjene
        val marksAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,marksList)
        spMark.adapter = marksAdapter
        spMark.setSelection(marksList.size-1)
    }
}