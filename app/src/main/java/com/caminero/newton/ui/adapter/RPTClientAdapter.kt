package com.caminero.newton.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.core.utils.StringUtils
import com.caminero.newton.core.utils.enums.LoanStatusType
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
        holder.txtBaseMount.text = item.baseMount.round(2).toString()
        holder.txtInterest.text = item.interest.toString()
        holder.txtStatus.text = item.status

        when (holder.txtStatus.text) {
            LoanStatusType.Finalized.code -> holder.cvPrincipal.setBackgroundColor(Color.rgb(125, 255, 145))
            LoanStatusType.InProgress.code -> holder.cvPrincipal.setBackgroundColor(Color.rgb(255, 255, 178))
        }
    }

    inner class RPTClientViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val cvPrincipal: CardView = itemView.cvPrincipal
        val lblId: TextView  = itemView.lblId
        val lblClientFullName: TextView  = itemView.lblClientFullName
        val lblMount: TextView  = itemView.lblMount
        val txtBaseMount: TextView  = itemView.txtBaseMount
        val txtInterest: TextView  = itemView.txtInterest
        val txtStatus: TextView  = itemView.txtStatus
    }
}