package com.example.dnevnikskolekur_ana.ui.studentDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_student_detail.*

@AndroidEntryPoint
class StudentDetailFragment : BaseFragment(R.layout.fragment_student_detail) {

    private val viewModel: StudentDetailViewModel by viewModels()

    private val args: StudentDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabEditStudent.setOnClickListener {
            findNavController().navigate(
                    StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditStudentFragment(args.id)
            )
        }
    }
}