package com.caminero.newton.model.listeners

import com.caminero.newton.model.entities.Client

interface ClientListener {

    fun onItemClick(client:Client)
}