package com.androiddevs.ktornoteapp.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


// fragment kojeg naslijeđuju svi ostali i služi da napravi funkciju za snackbar da bi
// se izbjeglo dupliciranje koda jer svaki fragment koristi jako puno snackbar-ova
abstract class BaseFragment(layoutId : Int) : Fragment(layoutId) {

    fun showSnackbar(text : String){
        Snackbar.make(
            requireActivity().rootLayout,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }
}