package com.example.dnevnikskolekur_ana.ui.addAnswersToStudent

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.*
import com.example.dnevnikskolekur_ana.other.Constants.AJEH
import com.example.dnevnikskolekur_ana.other.Constants.JUZ
import com.example.dnevnikskolekur_ana.other.Constants.JUZES
import com.example.dnevnikskolekur_ana.other.Constants.JUZ_NULL
import com.example.dnevnikskolekur_ana.other.Constants.MARKS
import com.example.dnevnikskolekur_ana.other.Constants.SURAH
import com.example.dnevnikskolekur_ana.other.Constants.SURAH_NULL
import com.example.dnevnikskolekur_ana.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_answers_to_student.*

@AndroidEntryPoint
class AddAnswersToStudentFragment : BaseFragment(R.layout.fragment_add_answers_to_student)  {

    private val args: AddAnswersToStudentFragmentArgs by navArgs()
    private val viewModel: AddAnswersToStudentViewModel by viewModels()

    var ajehMaxSelectedNumber : Int? = null
    var ajehMinSelectedNumber : Int? = null

    val marksList = MARKS
    var surah = SURAH_NULL
    var juz = JUZ_NULL
    var mark = marksList.size

    private var curStudent : Student? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(args.id.isNotEmpty()){
            viewModel.getStudentById(args.id)
            subscribeToObservers()
        }

        setupJuzSpinner()
        setupSurahSpinner()
        setupAjehSpinners()
        setupMarkSpinner()

        btSaveAnswer.setOnClickListener {
            saveAnswer()
            redirect()
        }

    }

    private fun saveAnswer(){
        var answerType : AnswerType = JUZ
        if (surah != SURAH_NULL){
            if (ajehMinSelectedNumber == 1 && ajehMaxSelectedNumber == surah.numberOfAjat)
                answerType = SURAH
            else
                answerType = AJEH
        }
        val answer = Answer(answerType,juz,surah,ajehMinSelectedNumber,
            ajehMaxSelectedNumber,System.currentTimeMillis(),mark)

        if(answer.juzNumber == JUZ_NULL){
            showSnackbar("Morate odabrati barem džuz!")
            return
        }
        addAnswerToCurStudent(answer)
    }

    private fun addAnswerToCurStudent(answer: Answer) {
        curStudent?.let { student ->
            curStudent?.apply { answers = answers + answer }
                ?.let { viewModel.addAnswerToCurStudent(it,answer) }
        }
    }

    private fun subscribeToObservers(){
        viewModel.student.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS -> {
                        val student = result.data!!
                        curStudent = student
                    }
                    Status.ERROR -> {
                        showSnackbar(result.message ?: "Student nije pronađen")
                    }
                    Status.LOADING -> {
                        /* NO-OP*/ //ovdje se samo učitava iz baze
                    }
                }
            }
        })
    }

    private fun redirect(){
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.studentDetailFragment,true)
            .build()  // onemogućavanje povratka na dodavanje odgovora kroz dugme nazad
        findNavController().navigate(
            AddAnswersToStudentFragmentDirections.actionAddAnswersToStudentFragmentToStudentDetailFragment(curStudent?.id ?: "" ),
            navOptions
        )
    }


    // POSTAVKE SPINNERA
    private fun setupJuzSpinner() {
        // Punjenje spinnera za Džuzeve
        val juzesList = JUZES
        val juzesAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,juzesList)
        spJuz.adapter = juzesAdapter

        // Punjenje spinnera za Sure nakon odabira JUZA
        spJuz.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                juz = adapterView?.getItemAtPosition(position) as Juz

                val surahList : List<Surah> = listOf(SURAH_NULL) + juz.surahs // ako nije odabran JUZ, odabrana sura je SURAH_NULL i odgovor se smatra da je samo za Džuz

                val surahAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,surahList)
                spSurah.adapter = surahAdapter
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setupSurahSpinner(){
        // Punjenje spinnera za Ajete(min i max) nakon odabira SURE
        spSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                surah = adapterView?.getItemAtPosition(position) as Surah
                // ako je odabrana sura SURAH_NULL onda nema ajeta i adapter se puni praznom listom
                val ajehList = surah.numberOfAjat?.let { List(it,{1+it}) }
                val ajehAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,ajehList?: emptyList())
                spAjehMin.adapter = ajehAdapter
                spAjehMax.adapter = ajehAdapter
                ajehList?.let {
                    if(ajehList.size > 0)
                        ajehMaxSelectedNumber = ajehList?.get(ajehList.size - 1)
                }
                spAjehMax.setSelection(ajehMaxSelectedNumber?.minus(1) ?: return)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun setupAjehSpinners(){
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
    }

    private fun setupMarkSpinner(){
        // Punjenje spinnera za Ocjene
        val marksAdapter = ArrayAdapter(activity as Context, R.layout.support_simple_spinner_dropdown_item,marksList)
        spMark.adapter = marksAdapter
        spMark.setSelection(marksList.size-1)

        spMark.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // ovaj dio nulira ajehMinSelectedNumber ako nije odabrana sura
                mark = adapterView?.getItemAtPosition(position) as Int
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}

