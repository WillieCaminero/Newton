package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.StringUtils
import com.caminero.newton.core.utils.round
import com.caminero.newton.model.entities.RPTClient
import kotlinx.android.synthetic.main.list_item_client_rpt.view.*

class RPTClientAdapter(private val items : List<RPTClient>) : RecyclerView.Adapter<RPTClientAdapter.RPTClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RPTClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_client_rpt, parent, false)

        return RPTClientViewHolder(root)
    }

    override fun getItemCount() : Int = items.size

    override fun onBindViewHolder(holder: RPTClientViewHolder, position: Int) {
        val item = items[position]

        holder.lblId.text = item.id
        holder.lblClientFullName.text = StringUtils.getFullName(item.name, item.lastName)
        holder.lblMount.text = item.mount.round(2).toString()
    }

    inner class RPTClientViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val lblId: TextView  = itemView.lblId
        val lblClientFullName: TextView  = itemView.lblClientFullName
        val lblMount: TextView  = itemView.lblMount
    }
}