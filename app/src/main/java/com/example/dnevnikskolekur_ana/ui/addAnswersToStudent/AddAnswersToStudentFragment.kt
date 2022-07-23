package com.example.dnevnikskolekur_ana.ui.addAnswersToStudent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.other.Constants
import com.example.dnevnikskolekur_ana.other.Constants.SURAHS
import com.example.dnevnikskolekur_ana.ui.MainActivity
import com.example.dnevnikskolekur_ana.ui.studentDetail.StudentDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_answers_to_student.*

@AndroidEntryPoint
class AddAnswersToStudentFragment : BaseFragment(R.layout.fragment_add_answers_to_student)  {

    private val args: AddAnswersToStudentFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val surahList = SURAHS
        val surahAdapter = ArrayAdapter<String>(activity as Context, R.layout.support_simple_spinner_dropdown_item,surahList)
        spSurah.adapter = surahAdapter
        spSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
               Toast.makeText(activity as Context,
                   "Odabrao si suru ${adapterView?.getItemAtPosition(position).toString()}",
                   Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}