package org.dronix.knative

import org.dronix.knative.data.action.GitCommandAction
import org.dronix.knative.data.retriever.MockCommandRetriever
import org.dronix.knative.domain.CommandUseCase

fun main() {

    println( " ====== START =====")
    val commandUseCase = CommandUseCase(MockCommandRetriever(), GitCommandAction("origin", "develop"))
    commandUseCase.startReceiveCommand()
}