package org.dronix.knative.data.retriever

import org.dronix.knative.domain.CommandRetriever
import org.dronix.knative.entities.Command
import org.dronix.knative.entities.EVENT

class MockCommandRetriever: CommandRetriever{
    override fun start(eventCallback: (EVENT) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var commandCallback: ((Command)-> Unit) ?= null

    override fun setCommandListener(listener: (Command) -> Unit) {
        commandCallback = listener
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}