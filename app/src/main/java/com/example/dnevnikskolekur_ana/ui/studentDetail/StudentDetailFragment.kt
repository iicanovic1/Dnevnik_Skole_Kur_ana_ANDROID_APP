package com.example.dnevnikskolekur_ana.ui.studentDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_student_detail.*

@AndroidEntryPoint
class StudentDetailFragment : BaseFragment(R.layout.fragment_student_detail) {

    private val viewModel: StudentDetailViewModel by viewModels()

    private val args: StudentDetailFragmentArgs by navArgs()

    private var curStudent : Student? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        fabEditStudent.setOnClickListener {
            findNavController().navigate(
                    StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditStudentFragment(args.id)
            )
        }
    }

    private fun subscribeToObservers(){
        viewModel.observeStudentByID(args.id).observe(viewLifecycleOwner, Observer {
            it?.let { student ->
                tvStudentTitle.text = student.name + " " + student.lastName
                tvStudentContent.text = student.content
                curStudent = student
            }?: showSnackbar("Student nije pronađen")
        })
    }
}