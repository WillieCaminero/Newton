package com.caminero.newton.core.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.*
import kotlin.math.round

fun EditText.setDatePickerDialog(minDate: Date = Date(), maxDate:Date = Date(), applyRange:Boolean = false){
    this.apply {
        this.isClickable = true
        this.isFocusable = false
        this.isLongClickable = false

        this.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this.context!!,
                DatePickerDialog.OnDateSetListener { _, selectedYear, monthOfYear, dayOfMonth ->
                    val date = String.format("%02d-%02d-%02d", dayOfMonth, (monthOfYear + 1), selectedYear)
                    this.setText(date)
                }, year, month, day
            )

            if(applyRange) {
                datePickerDialog.datePicker.minDate = minDate.time
                datePickerDialog.datePicker.maxDate = maxDate.time
            }

            datePickerDialog.show()
        }
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}