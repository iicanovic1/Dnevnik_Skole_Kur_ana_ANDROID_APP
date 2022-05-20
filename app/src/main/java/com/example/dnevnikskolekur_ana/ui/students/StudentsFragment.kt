package com.example.dnevnikskolekur_ana.ui.students

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.KEY_PASSWORD
import com.example.dnevnikskolekur_ana.other.Constants.NO_EMAIL
import com.example.dnevnikskolekur_ana.other.Constants.NO_PASSWORD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_students.*
import javax.inject.Inject

@AndroidEntryPoint
class StudentsFragment : BaseFragment(R.layout.fragment_students) {

    @Inject
    lateinit var  sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAddStudent.setOnClickListener {
            findNavController().navigate(StudentsFragmentDirections.actionStudentsFragmentToAddEditStudentFragment(""))
        }
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