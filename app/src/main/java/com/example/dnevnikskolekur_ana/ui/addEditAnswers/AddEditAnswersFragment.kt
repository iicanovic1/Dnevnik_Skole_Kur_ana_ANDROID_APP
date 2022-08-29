package com.example.dnevnikskolekur_ana.ui.addEditAnswers

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.adapters.ChapterSpinnerAdapter
import com.example.dnevnikskolekur_ana.adapters.IntegerSpinnerAdapter
import com.example.dnevnikskolekur_ana.adapters.SectionSpinnerAdapter
import com.example.dnevnikskolekur_ana.data.local.entities.*
import com.example.dnevnikskolekur_ana.other.Constants.SENTENCE
import com.example.dnevnikskolekur_ana.other.Constants.SECTION
import com.example.dnevnikskolekur_ana.other.Constants.SECTIONS
import com.example.dnevnikskolekur_ana.other.Constants.Section_NULL
import com.example.dnevnikskolekur_ana.other.Constants.MARKS
import com.example.dnevnikskolekur_ana.other.Constants.CHAPTER
import com.example.dnevnikskolekur_ana.other.Constants.Chapter_NULL
import com.example.dnevnikskolekur_ana.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_edit_answers.*
import java.util.*

@AndroidEntryPoint
class AddEditAnswersFragment : BaseFragment(R.layout.fragment_add_edit_answers)  {

    private val args: AddEditAnswersFragmentArgs by navArgs()
    private val viewModel: AddEditAnswersViewModel by viewModels()

    var sentenceMaxSelectedNumber : Int? = null
    var sentenceMinSelectedNumber : Int? = null

    val marksList = MARKS
    var chapter = Chapter_NULL
    var section = Section_NULL
    var mark = marksList.size

    private var curStudent : Student? = null
    private var oldAnswer : Answer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSectionSpinner()
        setupSurahSpinner()
        setupSentenceSpinners()
        setupMarkSpinner()

        if(args.studentID.isNotEmpty()){
            viewModel.getStudentById(args.studentID)
            subscribeToObservers()
        }

    }

    override fun onPause() {
        super.onPause()
        saveAnswer()
    }

    private fun saveAnswer(){
        var answerType : AnswerType = SECTION
        if (chapter != Chapter_NULL){
            if (sentenceMinSelectedNumber == 1 &&
                    sentenceMaxSelectedNumber == chapter.numberOfSentences)
                answerType = CHAPTER
            else
                answerType = SENTENCE
        }
        val revision = swRevision.isChecked
        val id = oldAnswer?.id ?: UUID.randomUUID().toString()
        val date = oldAnswer?.date ?:  System.currentTimeMillis()
        val answer = Answer(answerType, section, chapter, sentenceMinSelectedNumber,
            sentenceMaxSelectedNumber, date = date, mark, id = id, revision)

        if(answer.section == Section_NULL){
            showSnackbar("Odgovor nije spašen, morate odabrati barem cjelinu!")
            return
        }
        addAnswerToCurStudent(answer)
    }

    private fun addAnswerToCurStudent(newAnswer: Answer) {
        // ako smo odabrali da uređujemo postojeći odgovor ali nismo napravili nikakve izmjene
        if(oldAnswer != null && oldAnswer == newAnswer)
            return
        curStudent?.let { student ->
            // dodavanje novog odgovora studentu, označavanje da nije sinhronizovan i ažuriranje datuma zadnje izmjene
            student.apply { answers = answers + newAnswer ; isSynced = false ; date = System.currentTimeMillis()}
            // ako samo mijenjamo odgovor, brišemo stari jer će izmjene biti spašene u novom
            oldAnswer?.let { oldAnswer ->
                viewModel.insertStudent(
                    student.apply {
                        answers = answers - oldAnswer
                        sumOfMarks = sumOfMarks + (newAnswer.mark - oldAnswer.mark) // ažuriranje ocjena
                    }
                )
                // ako je dodavanje odgovora samo uredi ocjene bez brisanja starog jer nije izmjena
            }?: viewModel.insertStudent(student.apply { sumOfMarks += newAnswer.mark })
        }
    }

    private fun subscribeToObservers(){
        viewModel.student.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS -> {
                        val student = result.data!!
                        curStudent = student

                        if(args.answerID.isNotEmpty()){
                            oldAnswer = student.answers.find { answer -> answer.id == args.answerID }
                        }


                        // ako je editovanje odgovora učitaj dzuz postojećeg odgovora
                        oldAnswer?.let { answer ->
                            spSection.setSelection(answer.section.sectionNumber)
                            swRevision.isChecked = answer.revision
                            spMark.setSelection(answer.mark-1)
                        }
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


    // POSTAVKE SPINNERA
    private fun setupSectionSpinner() {
        // Punjenje spinnera za Džuzeve
        val sectionList = SECTIONS
        val sectionAdapter = SectionSpinnerAdapter(activity as Context,sectionList)
        spSection.adapter = sectionAdapter


        // Punjenje spinnera za Sure nakon odabira JUZA
        spSection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                section = adapterView?.getItemAtPosition(position) as Section

                val chapterList : List<Chapter> = listOf(Chapter_NULL) + section.chapters // ako nije odabran JUZ, odabrana sura je SURAH_NULL i odgovor se smatra da je samo za Džuz

                val chapterAdapter = ChapterSpinnerAdapter(activity as Context,chapterList)
                spChapter.adapter = chapterAdapter

                // ako je editovanje odgovora učitaj suru postojećeg odgovora
                oldAnswer?.let { answer ->
                    spChapter.setSelection(chapterList.indexOf(answer.chapter))
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setupSurahSpinner(){
        // Punjenje spinnera za Ajete(min i max) nakon odabira SURE
        spChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                chapter = adapterView?.getItemAtPosition(position) as Chapter
                // ako je odabrana sura SURAH_NULL onda nema ajeta i adapter se puni praznom listom
                val sentenceList = chapter.numberOfSentences?.let { List(it,{1+it}) }
                val sentenceAdapter = IntegerSpinnerAdapter(activity as Context,sentenceList?: emptyList())
                spSentenceMin.adapter = sentenceAdapter
                spSentenceMax.adapter = sentenceAdapter
                oldAnswer?.let { oldAnswer ->
                    if (oldAnswer.type == SENTENCE && oldAnswer.chapter == chapter){
                        spSentenceMin.setSelection(oldAnswer.sentenceMin!!-1)
                        spSentenceMax.setSelection(oldAnswer.sentenceMax!!-1)
                        sentenceMinSelectedNumber = oldAnswer.sentenceMin-1
                        sentenceMaxSelectedNumber = oldAnswer.sentenceMax-1
                        return
                    }
                }
                sentenceList?.let {
                    if(sentenceList.isNotEmpty())
                        sentenceMaxSelectedNumber = sentenceList.size - 1
                        spSentenceMax.setSelection(sentenceMaxSelectedNumber?: return)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setupSentenceSpinners(){
        // Kontrola da li je nakon odabira Min ajeta on manji od max ajeta
        spSentenceMin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // ovaj dio nulira ajehMinSelectedNumber ako nije odabrana sura
                sentenceMinSelectedNumber = adapterView?.getItemAtPosition(position) as Int?
                sentenceMinSelectedNumber?.let {
                    if(sentenceMaxSelectedNumber!! < sentenceMinSelectedNumber!!){
                        spSentenceMax.setSelection(sentenceMinSelectedNumber!! - 1)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        // Kontrola da li je nakon odabira Max ajeta on veći od min ajeta
        spSentenceMax.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // ovaj dio nulira ajehMaxSelectedNumber ako nije odabrana sura
                sentenceMaxSelectedNumber = adapterView?.getItemAtPosition(position) as Int?
                sentenceMaxSelectedNumber?.let {
                    if(sentenceMaxSelectedNumber!! < sentenceMinSelectedNumber!!){
                        spSentenceMin.setSelection(sentenceMaxSelectedNumber!! - 1)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setupMarkSpinner(){
        // Punjenje spinnera za Ocjene
        val marksAdapter = IntegerSpinnerAdapter(activity as Context,marksList)
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

