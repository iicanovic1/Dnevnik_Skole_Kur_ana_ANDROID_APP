package com.example.dnevnikskolekur_ana.ui.students

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import kotlinx.android.synthetic.main.fragment_students.*

class StudentsFragment : BaseFragment(R.layout.fragment_students) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAddStudent.setOnClickListener {
            findNavController().navigate(StudentsFragmentDirections.actionStudentsFragmentToAddEditStudentFragment(""))
        }
    }
}