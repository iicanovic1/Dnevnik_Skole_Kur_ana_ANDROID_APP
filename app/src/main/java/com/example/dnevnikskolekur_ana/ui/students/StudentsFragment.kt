package com.example.dnevnikskolekur_ana.ui.students

import android.content.SharedPreferences
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.adapters.StudentAdapter
import com.example.dnevnikskolekur_ana.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.KEY_PASSWORD
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.NO_PASSWORD
import com.example.dnevnikskolekur_ana.other.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_students.*
import javax.inject.Inject

@AndroidEntryPoint
class StudentsFragment : BaseFragment(R.layout.fragment_students) {

    private val viewModel: StudentsViewModel by viewModels()

    @Inject
    lateinit var  sharedPref: SharedPreferences

    private  lateinit var studentAdapter: StudentAdapter

    private val swipingItem =  MutableLiveData(false)

    // FUNKCIJE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = SCREEN_ORIENTATION_USER
        setupRecyclerView()
        subscibeToObservers()
        setupSwipeRefreshLayout()

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
        swipingItem.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isEnabled = !it
        })
    }

    // Recycler view

    private fun setupRecyclerView() = rvStudents.apply {
        studentAdapter = StudentAdapter()
        adapter = studentAdapter
        layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
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

    // swipe refresh

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.syncAllNotes()
        }
    }

    // Swipe opcija za delete

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            if(actionState== ItemTouchHelper.ACTION_STATE_SWIPE){
                swipingItem.postValue(isCurrentlyActive)
            }
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val student = studentAdapter.students[position]
            viewModel.deleteStudent(student.id)
            Snackbar.make(requireView(),"Student je uspješno obrisan", Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Otkaži"){
                    viewModel.insertStudent(student)
                    viewModel.deleteLocallyDeletedStudentID(student.id)
                }
                show()
            }
        }
    }
}