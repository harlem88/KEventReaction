package org.dronix.knative.domain

import org.dronix.knative.entities.Command

interface CommandRetriever {
    fun start()
    fun setCommandListener(listener: (Command)-> Unit)
    fun stop()
}