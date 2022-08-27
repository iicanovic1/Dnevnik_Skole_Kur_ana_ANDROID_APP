package com.example.dnevnikskolekur_ana.ui.addEditStudent

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Constants.DEFAULT_STUDENT_COLOR
import com.example.dnevnikskolekur_ana.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Status
import com.example.dnevnikskolekur_ana.ui.dialogs.ColorPickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_edit_student.*
import java.util.*
import javax.inject.Inject

const val FRAGMENT_TAG = "AddEditNoteFragment"
@AndroidEntryPoint
class AddEditStudentFragment : BaseFragment(R.layout.fragment_add_edit_student) {

    private val viewModel: AddEditStudentViewModel by viewModels()

    private val args: AddEditStudentFragmentArgs by navArgs()

    private var curStudent: Student? = null
    private var curStudentColor = DEFAULT_STUDENT_COLOR

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id.isNotEmpty()) {
            viewModel.getStudentById(args.id)
            subscribeToObservers()
        }

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_book, null)
        drawable?.let{
            val wrappedDrawable = DrawableCompat.wrap(it)
            val color = Color.parseColor("#78909c")
            DrawableCompat.setTint(wrappedDrawable , color)
            ic_book.background = wrappedDrawable
        }

    }

    private fun subscribeToObservers(){
        viewModel.student.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS -> {
                        val student = result.data!!
                        curStudent = student
                        etStudentName.setText(student.name)
                        etStudentLastName.setText(student.lastName)
                        etStudentContent.setText(student.content)
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

    private fun saveStudnet(){
        val authEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL

        val name = etStudentName.text.toString()
        val lastName = etStudentLastName.text.toString()
        val content = etStudentContent.text.toString()
        if(name.isEmpty() && lastName.isEmpty()){
            return
        }
        val date = System.currentTimeMillis()
        val color = curStudentColor
        val id = curStudent?.id ?: UUID.randomUUID().toString()
        val accessEmails =curStudent?.accessEmails ?: listOf(Access(authEmail,true))
        val answers = curStudent?.answers ?: emptyList()
        val student = Student(name, lastName, content, date, accessEmails, color, answers, id = id )
        viewModel.insertStudent(student)
    }

    override fun onPause() {
        super.onPause()
        saveStudnet()
    }
}