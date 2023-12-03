package br.com.leandro.training.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getString
import br.com.leandro.training.R
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun CharSequence?.isValidEmail() : Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")

    return this?.let { emailRegex.matches(it) } ?: false
}

fun Long.timestampToString(): String {
    val dateFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(this))
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

fun TextInputEditText.validatePassword(
    context: Context,
    editTextToCompare: TextInputEditText
): Boolean {
    val selectedPassword = this.text.toString()
    val otherPassword = editTextToCompare.text.toString()

    return this.text?.let {
        if (it.length >= 8) {
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
    } ?: false
}