package com.example.dnevnikskolekur_ana.ui.studentDetail

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.adapters.AnswerAdapter
import com.example.dnevnikskolekur_ana.adapters.AnswerTypeSpinnerAdapter
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Constants
import com.example.dnevnikskolekur_ana.other.Constants.SENTENCE
import com.example.dnevnikskolekur_ana.other.Constants.ANSWER_TYPES
import com.example.dnevnikskolekur_ana.other.Constants.SECTION
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.CHAPTER
import com.example.dnevnikskolekur_ana.other.Constants.TYPE_NULL
import com.example.dnevnikskolekur_ana.other.Status
import com.example.dnevnikskolekur_ana.ui.dialogs.AddAccessDialog
import com.google.android.material.snackbar.Snackbar
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

    private val swipingItem =  MutableLiveData(false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        setupRecyclerView()
        setupTypeSpinner()

        fabAddAnswersToStudent.setOnClickListener {
            findNavController().navigate(
                StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditAnswersFragment(args.id,"")
            )
        }

        if(savedInstanceState != null){
            val addAccessDialog = parentFragmentManager.findFragmentByTag(ADD_ACCESS_DIALOG_TAG) as AddAccessDialog?
            addAccessDialog?.setPositiveListener {
                addAccessToCurStudent(it)
            }
        }

        answersAdapter.setOnItemClickListener {
            if (hasEditAccess()){
                findNavController().navigate(
                    StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditAnswersFragment(args.id,it.id)
                )
            } else {
                showSnackbar("Nemate pravo da uređujete profil ovoga učenika!")
            }
        }


    }

    private fun navigateToEditStudentFragment(){
        if(hasEditAccess()){
            findNavController().navigate(
                StudentDetailFragmentDirections.actionStudentDetailFragmentToAddEditStudentFragment(args.id)
            )
        } else {
            showSnackbar("Nemate pravo da uređujete profil ovoga učenika!")
        }
    }

    // POSTAVKE SPINNERA
    private fun setupTypeSpinner() {
        // Punjenje spinnera za Tipove
        val typesList = ANSWER_TYPES
        val typesAdapter = AnswerTypeSpinnerAdapter(activity as Context, typesList)
        spAnswerType.adapter = typesAdapter

        spAnswerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val type = adapterView?.getItemAtPosition(position) as AnswerType

                curStudent?.let {  student ->
                    when(type){
                        TYPE_NULL -> {
                            answersAdapter.answers = student.answers
                        }
                        SECTION -> {
                            answersAdapter.answers = student.answers.filter { it.type == SECTION }
                        }
                        CHAPTER -> {
                            answersAdapter.answers = student.answers.filter { it.type == CHAPTER }
                        }
                        SENTENCE -> {
                            answersAdapter.answers = student.answers.filter { it.type == SENTENCE }
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
                        showSnackbar(result.data?:"Uspješno dodan pristup učeniku")
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
                    //setHasOptionsMenu(false)
                    fabAddAnswersToStudent.hide()
                }
                answersAdapter.answers = student.answers
            }?: showSnackbar("Učenik nije pronađen")
        })
    }

    private fun addAccessToCurStudent(access: Access) {
        if(hasEditAccess() || access.edit == false){
            curStudent?.let { student ->
                viewModel.addAccessToStudent(student.id, access)
            }
        } else {
            showSnackbar("Nemate pravo izvršenja za ovu radnju!")
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
            R.id.miEditStudent -> navigateToEditStudentFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    // Recycler view

    private fun setupRecyclerView() = rvAnswers.apply {
        answersAdapter = AnswerAdapter()
        adapter = answersAdapter
        layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                swipingItem.postValue(isCurrentlyActive)
            }
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val answer = answersAdapter.answers[position]
            curStudent?.let {  student ->
                student.apply { answers = answers - answer ; isSynced = false}
                viewModel.insertStudent(student.apply { sumOfMarks -= answer.mark })
                Snackbar.make(requireView(),"Odgovor je uspješno obrisan", Snackbar.LENGTH_INDEFINITE).apply {
                    setAction("Otkaži"){
                        student.apply { sumOfMarks += answer.mark }
                        viewModel.insertStudent(student.apply { answers = answers + answer ; isSynced = false } )
                    }
                    show()
                }
            }
        }
    }

}