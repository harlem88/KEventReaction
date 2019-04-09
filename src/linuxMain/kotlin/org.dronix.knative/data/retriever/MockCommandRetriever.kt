package org.dronix.knative.data.retriever

import org.dronix.knative.domain.CommandRetriever
import org.dronix.knative.entities.Command

class MockCommandRetriever: CommandRetriever{
    private var commandCallback: ((Command)-> Unit) ?= null

    override fun setCommandListener(listener: (Command) -> Unit) {
        commandCallback = listener
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}