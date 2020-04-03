package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.enums.PaymentStatusType
import com.caminero.newton.core.utils.round
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.listeners.LoanListener
import kotlinx.android.synthetic.main.list_item_loan.view.*

class LoanAdapter(private val items : List<Loan>,
                  private val listener: LoanListener
) : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_loan, parent, false)

        return LoanViewHolder(root)
    }

    override fun getItemCount() : Int = items.size

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val item = items[position]
        val payments = item.payments.filter { it.status == PaymentStatusType.Active.code }

        holder.txtStartDate.text = item.startDate
        holder.txtEndDate.text = item.endDate
        holder.txtMount.text = item.mount.toString()
        holder.txtInterest.text = item.interest.toString()
        holder.txtStatus.text = item.status
        holder.txtTotalMount.text = ((item.interest.toDouble() / 100 * item.mount) + item.mount).round(2).toString()
        holder.txtCurrentMount.text = (payments.sumBy { it.mount }).toString()
        holder.txtCurrentPayments.text = payments.size.toString()
        holder.txtPendingPayments.text = (item.days - payments.size).toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    inner class LoanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtStartDate: TextView = itemView.txtName
        val txtEndDate: TextView   = itemView.txtEndDate
        val txtMount: TextView  = itemView.txtMount
        val txtInterest: TextView  = itemView.txtInterest
        val txtStatus: TextView  = itemView.txtStatus
        val txtTotalMount: TextView  = itemView.txtTotalMount
        val txtCurrentMount: TextView  = itemView.txtCurrentMount
        val txtCurrentPayments: TextView = itemView.txtCurrentPayments
        val txtPendingPayments: TextView  = itemView.txtlblPendingPayments
    }
}