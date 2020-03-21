package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
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

        holder.txtStartDate.text = item.startDate
        holder.txtEndDate.text = item.endDate
        holder.txtMount.text = item.mount.toString()
        holder.txtInterest.text = item.interest.toString()
        holder.txtStatus.text = item.status
        holder.txtTotalMount.text = ((item.interest / 100 * item.mount) + item.mount).toString()
        holder.txtCurrentMount.text = "400"
        holder.txtCurrentPayments.text = "10"
        holder.txtlblPendingPayments.text = "30"

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    inner class LoanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtStartDate = itemView.txtPaymentDate
        val txtEndDate  = itemView.txtEndDate
        val txtMount = itemView.txtMount
        val txtInterest = itemView.txtInterest
        val txtStatus = itemView.txtStatus
        val txtTotalMount = itemView.txtTotalMount
        val txtCurrentMount = itemView.txtCurrentMount
        val txtCurrentPayments = itemView.txtCurrentPayments
        val txtlblPendingPayments = itemView.txtlblPendingPayments
    }
}