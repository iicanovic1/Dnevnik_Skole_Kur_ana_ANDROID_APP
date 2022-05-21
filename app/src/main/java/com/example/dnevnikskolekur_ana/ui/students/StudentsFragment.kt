package com.example.dnevnikskolekur_ana.ui.students

import android.content.SharedPreferences
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.ktornoteapp.adapters.StudentAdapter
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.KEY_PASSWORD
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.NO_PASSWORD
import com.example.dnevnikskolekur_ana.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_students.*
import javax.inject.Inject

@AndroidEntryPoint
class StudentsFragment : BaseFragment(R.layout.fragment_students) {

    private val viewModel: StudentsViewModel by viewModels()

    @Inject
    lateinit var  sharedPref: SharedPreferences

    private  lateinit var studentAdapter: StudentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = SCREEN_ORIENTATION_USER
        setupRecyclerView()
        subscibeToObservers()

        studentAdapter.setOnItemClickListener {
            findNavController().navigate(
                    StudentsFragmentDirections.actionStudentsFragmentToStudentDetailFragment(it.id)
            )
        }
        fabAddStudent.setOnClickListener {
            findNavController().navigate(StudentsFragmentDirections.actionStudentsFragmentToAddEditStudentFragment(""))
        }
    }

    private fun subscibeToObservers() {
        viewModel.allStudents.observe(viewLifecycleOwner, Observer {
            it?.let { event ->
                val result = event.peekContent()
                when(result.status){
                    Status.SUCCESS -> {
                        studentAdapter.students = result.data!!
                        swipeRefreshLayout.isRefreshing = false
                    }
                    Status.ERROR -> {
                        event.getContentIfNotHandled()?.let{ errorResource ->
                            errorResource.message?.let{ message->
                                showSnackbar(message)
                            }
                        }
                        result.data?.let { students ->
                            studentAdapter.students = students
                        }
                        swipeRefreshLayout.isRefreshing = false
                    }
                    Status.LOADING -> { // prikaži sadržaj svakako iako loadaš jer ima lokalno u bazi dok se čeka
                        result.data?.let { students ->
                            studentAdapter.students = students
                        }
                        swipeRefreshLayout.isRefreshing = true
                    }
                }
            }
        })
    }

    // Recycler view

    private fun setupRecyclerView() = rvStudents.apply {
        studentAdapter = StudentAdapter()
        adapter = studentAdapter
        layoutManager = LinearLayoutManager(requireContext())
        //ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
    }

    // LOGOUT MENI - AKTIVACIJA

    private fun logout() {
        sharedPref.edit().putString(KEY_LOGGED_IN_EMAIL, NO_EMAIL).apply()
        sharedPref.edit().putString(KEY_PASSWORD, NO_PASSWORD).apply()
        val navOptions = NavOptions.Builder()
              .setPopUpTo(R.id.studentsFragment, true)
              .build()
        findNavController().navigate(
              StudentsFragmentDirections.actionStudentsFragmentToAuthFragment(),
              navOptions
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miLogout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_students,menu)
    }
}