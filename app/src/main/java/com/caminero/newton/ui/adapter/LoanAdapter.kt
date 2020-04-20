package com.caminero.newton.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.convertStringDateTimeISO8601ToDate
import com.caminero.newton.core.utils.convertStringDateTimeISO8601ToStringDateTime
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.core.utils.enums.PaymentStatusType
import com.caminero.newton.core.utils.round
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.listeners.LoanListener
import kotlinx.android.synthetic.main.list_item_loan.view.*
import java.util.*

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
        val loan = items[position]
        val payments = loan.payments

        holder.txtStartDate.text = convertStringDateTimeISO8601ToStringDateTime(loan.startDate)
        holder.txtEndDate.text = convertStringDateTimeISO8601ToStringDateTime(loan.endDate)
        holder.txtMount.text = loan.mount.toString()
        holder.txtInterest.text = loan.interest.toString()
        holder.txtStatus.text = loan.status
        val totalMount = (loan.interest.toDouble() / 100 * loan.mount) + loan.mount
        val currentMount = (payments.filter { it.status == PaymentStatusType.Paid.code }.sumByDouble { it.mount.toDouble() }).round(2).toString()
        holder.txtCurrentMount.text = currentMount
        holder.txtTotalMount.text = (totalMount - currentMount.toDouble()).round(2).toString()
        holder.txtCurrentPayments.text = loan.dues.toString()
        holder.txtPendingPayments.text = payments.filter { it.status ==  PaymentStatusType.InProgress.code}.size.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(loan)
        }

        when (holder.txtStatus.text) {
            LoanStatusType.Finalized.code -> holder.cvPrincipal.setBackgroundColor(Color.rgb(125, 255, 145))
            LoanStatusType.InProgress.code -> holder.cvPrincipal.setBackgroundColor(Color.rgb(255, 255, 178))
        }

        if(convertStringDateTimeISO8601ToDate(loan.endDate) <= Date() && loan.status == LoanStatusType.InProgress.code ){
            holder.cvPrincipal.setBackgroundColor(Color.rgb(255, 255, 255))
            holder.ivError.visibility = View.VISIBLE
        }
    }

    inner class LoanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val cvPrincipal: CardView = itemView.cvPrincipal
        val ivError: ImageView = itemView.ivError
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