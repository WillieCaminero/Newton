package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.StringUtils
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.listeners.ClientListener
import kotlinx.android.synthetic.main.list_item_client.view.*

class ClientAdapter(private val items : List<Client>,
                    private val listener:ClientListener) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_client, parent, false)

        return ClientViewHolder(root)
    }

    override fun getItemCount() : Int = items.size

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val item = items[position]
        val loans = item.loans.filter { it.status == LoanStatusType.InProgress.code }

        holder.lblId.text = item.id
        holder.lblClientFullName.text = StringUtils.getFullName(item.name, item.lastName)
        holder.lblAddress.text = item.address
        holder.lblNumberLoans.text = loans.size.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    inner class ClientViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val lblId: TextView  = itemView.lblId
        val lblClientFullName: TextView  = itemView.lblClientFullName
        val lblAddress: TextView = itemView.lblAddress
        val lblNumberLoans: TextView  = itemView.lblNumberLoans
    }
}