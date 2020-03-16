package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.model.entities.Client
import kotlinx.android.synthetic.main.list_item_client.view.*

class ClientAdapter(val items : List<Client>) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_client, parent, false)

        return ClientViewHolder(root)
    }

    override fun getItemCount() : Int = items.size

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val item = items[position]

        holder.lblId.text = item.id
        holder.lblClientFullName.text = item.name
        holder.lblAddress.text = item.address
        holder.lblTaskTime.text = item.createdDate
    }

    inner class ClientViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val lblId = itemView.lblId
        val lblClientFullName = itemView.lblClientFullName
        val lblAddress  = itemView.lblAddress
        val lblTaskTime = itemView.lblTaskTime
    }
}