package com.example.dnevnikskolekur_ana.ui.studentDetail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.adapters.AnswerAdapter
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Constants
import com.example.dnevnikskolekur_ana.other.Constants.AJEH
import com.example.dnevnikskolekur_ana.other.Constants.ANSWER_TYPES
import com.example.dnevnikskolekur_ana.other.Constants.JUZ
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.SURAH
import com.example.dnevnikskolekur_ana.other.Constants.TYPE_NULL
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

    private  lateinit var answersAdapter: AnswerAdapter

    private val args: StudentDetailFragmentArgs by navArgs()

    private var curStudent : Student? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        setupRecyclerView()
        setupTypeSpinner()
        fabEditStudent.setOnClickListener {
            if(hasEditAccess()){
                findNavController().navigate(
                        StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditStudentFragment(args.id)
                )
            }
        }
        fabAddAnswersToStudent.setOnClickListener {
            findNavController().navigate(
                StudentDetailFragmentDirections.actionStudentDetailFragmentToAddAnswersToStudentFragment(args.id)
            )
        }
        if(savedInstanceState != null){
            val addAccessDialog = parentFragmentManager.findFragmentByTag(ADD_ACCESS_DIALOG_TAG) as AddAccessDialog?
            addAccessDialog?.setPositiveListener {
                addAccessToCurStudent(it)
            }
        }

        answersAdapter.setOnItemClickListener {
            findNavController().navigate(
                StudentDetailFragmentDirections.actionStudentDetailFragmentToAddAnswersToStudentFragment(curStudent?.id?:"")
            )
        }


    }

    // POSTAVKE SPINNERA
    private fun setupTypeSpinner() {
        // Punjenje spinnera za Tipove
        val typesList = ANSWER_TYPES
        val typesAdapter = ArrayAdapter(activity as Context, R.layout.spinner_item,
            typesList)
        spAnswerType.adapter = typesAdapter

        spAnswerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val type = adapterView?.getItemAtPosition(position) as AnswerType

                curStudent?.let {  student ->
                    when(type){
                        TYPE_NULL -> {
                            answersAdapter.answers = student.answers
                        }
                        JUZ -> {
                            answersAdapter.answers = student.answers.filter { it.type == JUZ }
                        }
                        SURAH -> {
                            answersAdapter.answers = student.answers.filter { it.type == SURAH }
                        }
                        AJEH -> {
                            answersAdapter.answers = student.answers.filter { it.type == AJEH }
                        }

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun hasEditAccess() : Boolean {
        val allAccesses =  curStudent?.accessEmails?.filter { access ->
            access.email == (sharedPref.getString(Constants.KEY_LOGGED_IN_EMAIL, NO_EMAIL)
                ?: NO_EMAIL)
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
                        progressBar.visibility = View.GONE
                        showSnackbar(result.data?:"Uspješno dodan pristup studnetu")
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        showSnackbar(result.message?:"Nepoznata greška")
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
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
                    setHasOptionsMenu(false)
                    fabEditStudent.hide()
                    fabAddAnswersToStudent.hide()
                }
                answersAdapter.answers = student.answers
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

    // Recycler view

    private fun setupRecyclerView() = rvAnswers.apply {
        answersAdapter = AnswerAdapter()
        adapter = answersAdapter
        layoutManager = LinearLayoutManager(requireContext())
        //ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
    }
/*
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
            student.apply { answers = answers - answer}

            Snackbar.make(requireView(),"Odgovor je uspješno obrisan", Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Otkaži"){
                    viewModel.insertStudent(curStudent)
                }
                show()
            }
        }
    }

 */
}