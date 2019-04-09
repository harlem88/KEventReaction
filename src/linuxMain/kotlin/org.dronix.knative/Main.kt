package org.dronix.knative

import org.dronix.knative.data.action.GitHubCommandAction
import org.dronix.knative.data.retriever.MockCommandRetriever
import org.dronix.knative.domain.CommandUseCase

fun main() {

    println( " ====== START =====")
    val commandUseCase = CommandUseCase(MockCommandRetriever(), GitHubCommandAction("origin", "develop"))
    commandUseCase.startReceiveCommand()
}