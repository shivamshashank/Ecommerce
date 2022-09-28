package com.example.ecommerceapp.dialogs

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

@SuppressLint("InflateParams")
fun Fragment.setUpBottomSheetDialog(
    onSendClick: (String) -> Unit,
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)

    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val editTextEmailResetPassword = view.findViewById<EditText>(R.id.editTextEmailResetPassword)
    val buttonCancelResetPassword = view.findViewById<Button>(R.id.buttonCancelResetPassword)
    val buttonSendResetPassword = view.findViewById<Button>(R.id.buttonSendResetPassword)

    buttonCancelResetPassword.setOnClickListener {
        dialog.dismiss()
    }

    buttonSendResetPassword.setOnClickListener {
        val email = editTextEmailResetPassword.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }
}