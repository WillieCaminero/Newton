package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.convertStringDateTimeISO8601ToStringTime
import com.caminero.newton.core.utils.round
import com.caminero.newton.model.entities.Expense
import kotlinx.android.synthetic.main.list_item_expense.view.*

class ExpenseAdapter(private val items : List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_expense, parent, false)

        return ExpenseViewHolder(root)
    }

    override fun getItemCount() : Int = items.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val item = items[position]

        holder.lblDescription.text = item.description
        holder.lblMount.text = item.mount.round(2).toString()
        holder.lblTime.text = convertStringDateTimeISO8601ToStringTime(item.expenseDate)
    }

    inner class ExpenseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val lblDescription: TextView  = itemView.lblDescription
        val lblMount: TextView  = itemView.lblMount
        val lblTime: TextView  = itemView.lblTime
    }
}