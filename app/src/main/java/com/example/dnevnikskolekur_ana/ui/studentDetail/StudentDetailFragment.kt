package com.example.dnevnikskolekur_ana.ui.studentDetail

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Constants
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Status
import com.example.dnevnikskolekur_ana.ui.dialogs.AddAccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_student_detail.*
import javax.inject.Inject

const val ADD_ACCESS_DIALOG_TAG = "ADD_ACCESS_DIALOG_TAG"
@AndroidEntryPoint
class StudentDetailFragment : BaseFragment(R.layout.fragment_student_detail) {

    private val viewModel: StudentDetailViewModel by viewModels()

    @Inject
    lateinit var  sharedPref: SharedPreferences

    private val args: StudentDetailFragmentArgs by navArgs()

    private var curStudent : Student? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        fabEditStudent.setOnClickListener {
            if(hasEditAccess()){
                findNavController().navigate(
                        StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditStudentFragment(args.id)
                )
            }
        }
        if(savedInstanceState != null){
            val addAccessDialog = parentFragmentManager.findFragmentByTag(ADD_ACCESS_DIALOG_TAG) as AddAccessDialog?
            addAccessDialog?.setPositiveListener {
                addAccessToCurStudent(it)
            }
        }
    }

    private fun hasEditAccess() : Boolean {
        val allAccesses =  curStudent?.accessEmails?.filter { access ->
                    access.email == sharedPref.getString(Constants.KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL
                }
        var _hasEditAccess : Boolean = false
        allAccesses?.forEach { access ->  if(access.edit == true) _hasEditAccess = true}
        return _hasEditAccess
    }

    private fun subscribeToObservers(){
        viewModel.addAccessStatus.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        addAccessProgressBar.visibility = View.GONE
                        showSnackbar(result.data?:"Uspješno dodan pristup studnetu")
                    }
                    Status.ERROR -> {
                        addAccessProgressBar.visibility = View.GONE
                        showSnackbar(result.message?:"Nepoznata greška")
                    }
                    Status.LOADING -> {
                        addAccessProgressBar.visibility = View.VISIBLE
                    }
                }

            }
        })
        viewModel.observeStudentByID(args.id).observe(viewLifecycleOwner, Observer {
            it?.let { student ->
                tvStudentTitle.text = student.name + " " + student.lastName
                tvStudentContent.text = student.content
                curStudent = student
                if(!hasEditAccess()){
                    setHasOptionsMenu(true)
                    fabEditStudent.hide()
                }
            }?: showSnackbar("Student nije pronađen")
        })
    }

    private fun addAccessToCurStudent(access: Access) {
        curStudent?.let { student ->
            viewModel.addAccessToStudent(student.id, access)
        }
    }

    private fun showAddAccessDialog() {
        AddAccessDialog().apply {
            setPositiveListener {
                addAccessToCurStudent(it)
            }
        }.show(parentFragmentManager, ADD_ACCESS_DIALOG_TAG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.student_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miAddAccess -> showAddAccessDialog()
        }
        return super.onOptionsItemSelected(item)
    }
}