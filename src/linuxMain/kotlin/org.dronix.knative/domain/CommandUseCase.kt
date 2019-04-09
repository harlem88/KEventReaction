package org.dronix.knative.domain

import org.dronix.knative.entities.Command

class CommandUseCase(private val commandRetriever: CommandRetriever, commandAction: CommandAction){

    init {
        commandRetriever.setCommandListener { commandListener }
    }

    fun startReceiveCommand(){
        commandRetriever.start()
    }

    fun stoptReceiveCommand(){
        commandRetriever.stop()
    }

    private val commandListener: (Command)->Unit = {
        when(it){
            Command.PUSH -> commandAction.onPush()
            Command.PULL -> commandAction.onPull()
        }
    }
}