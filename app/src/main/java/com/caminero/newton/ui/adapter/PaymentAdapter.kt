package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.listeners.PaymentListener
import kotlinx.android.synthetic.main.list_item_payment.view.*

class PaymentAdapter(private val items : List<Payment>,
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

        holder.txtPaymentDate.text = item.paymentDate
        holder.txtMount.text =  "$" + item.mount.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    inner class PaymentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtPaymentDate: TextView = itemView.txtName
        val txtMount: TextView = itemView.txtMount
    }
}