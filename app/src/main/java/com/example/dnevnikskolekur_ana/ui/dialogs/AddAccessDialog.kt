package com.example.dnevnikskolekur_ana.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_student_detail.*


class AddAccessDialog : DialogFragment() {

    private var positiveListener: ((Access) -> Unit)? = null

    fun setPositiveListener(listener: (Access) -> Unit){
        positiveListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       val addAccessEditText = LayoutInflater.from(requireContext()).inflate(
           R.layout.edit_text_email,
           clStudentContainer,
           false
       ) as TextInputLayout
        return MaterialAlertDialogBuilder(requireContext())
            .setIcon(R.drawable.ic_add_person)
            .setTitle("Dodaj pristup studentu")
            .setMessage("Unesite E-mail")
            .setView(addAccessEditText)
            .setPositiveButton("Dodaj") { _, _ ->
                val email = addAccessEditText.findViewById<EditText>(R.id.etAddAccessEmail).text.toString()
                val edit = addAccessEditText.findViewById<CheckBox>(R.id.cbEditAccess).isChecked
                positiveListener?.let {yes ->
                    yes(Access(email, edit))
                }
            }
            .setNegativeButton("Odustani") {dialog, _ ->
                dialog.cancel()
            }
            .create()

    }
}