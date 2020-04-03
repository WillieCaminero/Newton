package com.caminero.newton.core.utils

import android.app.DatePickerDialog
import android.widget.EditText
import java.util.*
import kotlin.math.round

fun EditText.setDatePickerDialog(){
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
                    val date = String.format("%02d-%02d-%02d", selectedYear, (monthOfYear + 1), dayOfMonth)
                    this.setText(date)
                }, year, month, day
            )

            datePickerDialog.show()
        }
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}