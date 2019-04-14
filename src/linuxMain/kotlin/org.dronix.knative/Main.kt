package org.dronix.knative

import org.dronix.knative.data.action.GitCommandAction
import org.dronix.knative.data.retriever.MockCommandRetriever
import org.dronix.knative.data.retriever.SerialCommandRetriever
import org.dronix.knative.domain.CommandUseCase
import platform.posix.sleep

fun main(args: Array<String>) {

    val serialCommandRetriever = SerialCommandRetriever("/dev/ttyUSB0")
    println( " ====== START =====")
    val commandUseCase = CommandUseCase(serialCommandRetriever, GitCommandAction("origin", "develop"), serialCommandRetriever)
    commandUseCase.startReceiveCommand()
}