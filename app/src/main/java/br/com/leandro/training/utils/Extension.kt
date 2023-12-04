package br.com.leandro.training.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import br.com.leandro.training.R
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.timestampToString(): String {
    val dateFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(this))
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

@Suppress("DEPRECATION")
fun Context.vibrate(milliseconds: Long) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    } else {
        vibrator.vibrate(milliseconds)
    }
}

fun TextInputEditText.validateEmail(): Boolean {
    val email = this.text.toString()
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")

    return if (!emailRegex.matches(email)) {
        this.error = getString(context, R.string.invalid_email)
        false
    } else true
}

fun TextInputEditText.validateForm(): Boolean {
    val name = this.text.toString()

    return if (name.isEmpty()) {
        this.error = getString(context, R.string.must_be_filled)
        false
    } else true
}

fun TextInputEditText.validatePassword(): Boolean {
    val password = this.text.toString()

    return if (password.length >= 8) {
        this.error = null
        true
    } else {
        this.error = getString(context, R.string.password_requirements)
        false
    }
}

fun TextInputEditText.validatePassword(
    editTextToCompare: TextInputEditText
): Boolean {
    val selectedPassword = this.text.toString()
    val otherPassword = editTextToCompare.text.toString()

    return if (selectedPassword.length >= 8) {
        if (selectedPassword == otherPassword) {
            this.error = null
            editTextToCompare.error = null
            true
        } else if (selectedPassword.isNotEmpty() && otherPassword.isNotEmpty()) {
            this.error = getString(context, R.string.passwords_do_not_match)
            false
        } else false
    } else {
        this.error = getString(context, R.string.password_requirements)
        false
    }
}