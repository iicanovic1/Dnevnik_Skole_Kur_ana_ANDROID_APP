package com.example.dnevnikskolekur_ana.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorPickerDialogFragment : DialogFragment() {

    private var positiveListener: ((String) -> Unit)? = null

    fun setPositiveListener(listener: (String) -> Unit){
        positiveListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ColorPickerDialog.Builder(requireContext())
            .setTitle("Odaberite boju")
            .setPositiveButton("OK", object : ColorEnvelopeListener{
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    positiveListener?.let { yes ->
                        envelope?.let {
                            yes(it.hexCode)
                        }
                    }
                }
            }).setNegativeButton("Odustani") {  dialogInterface , _ ->
                dialogInterface.cancel()
            }
            .setBottomSpace(12)
            .attachAlphaSlideBar(true)
            .attachBrightnessSlideBar(true)
            .create()
    }
}