package com.caminero.newton.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caminero.newton.R
import com.caminero.newton.model.entities.Client

class ClientsListAdapter(val list : List<Client>) : RecyclerView.Adapter<ClientsListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.list_item_client, parent, false)
        return TaskViewHolder(root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TaskViewHolder(val root : View) : RecyclerView.ViewHolder(root) {

        lateinit var lblId : TextView
        lateinit var lblClientFullName : TextView
        lateinit var lblAddress : TextView
        lateinit var lblTaskTime : TextView

        fun bind (position : Int){

            val client = list[position]

            lblId = root.findViewById(R.id.lblId )
            lblClientFullName = root.findViewById(R.id.lblClientFullName)
            lblAddress  = root.findViewById(R.id.lblAddress)
            lblTaskTime = root.findViewById(R.id.lblTaskTime)

            lblId.text = client.id
        }
    }

}