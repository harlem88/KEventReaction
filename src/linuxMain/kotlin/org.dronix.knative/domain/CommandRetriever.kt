package org.dronix.knative.domain

import org.dronix.knative.entities.Command
import org.dronix.knative.entities.EVENT

interface CommandRetriever {
    fun start(eventCallback: (EVENT)->Unit)
    fun setCommandListener(listener: (Command)-> Unit)
    fun stop()
}