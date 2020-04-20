package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.convertStringDateTimeISO8601ToStringDateTime
import com.caminero.newton.core.utils.enums.PaymentStatusType
import com.caminero.newton.core.utils.round
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.listeners.PaymentListener
import kotlinx.android.synthetic.main.list_item_payment.view.*

class PaymentAdapter(private var items : List<Payment>,
                     private val listener: PaymentListener
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_payment, parent, false)

        return PaymentViewHolder(root)
    }

    override fun getItemCount() : Int = items.size

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val item = items[position]

        holder.cbStatus.isChecked = item.status == PaymentStatusType.Paid.code
        holder.txtPaymentDate.text = convertStringDateTimeISO8601ToStringDateTime(item.paymentDate)
        holder.txtMount.text =  "$" + item.mount.round(2).toString()

        holder.cbStatus.setOnClickListener {
            listener.OnCheckedChange(item.paymentId, holder.cbStatus.isChecked)
        }
    }

    fun updateElement(paymentId: String, isChecked: Boolean){
        val status = if(isChecked) PaymentStatusType.Paid.code else PaymentStatusType.InProgress.code
        var data = items
        data.filter { it.paymentId == paymentId }.forEach{ it.status = status}
        items = data
        notifyDataSetChanged()
    }

    inner class PaymentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val cbStatus: CheckBox = itemView.cbStatus
        val txtPaymentDate: TextView = itemView.txtPaymentDate
        val txtMount: TextView = itemView.txtMount
    }
}