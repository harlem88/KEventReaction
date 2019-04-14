package org.dronix.knative.domain

import org.dronix.knative.entities.Command
import org.dronix.knative.entities.EVENT

class CommandUseCase(private val commandRetriever: CommandRetriever, commandAction: CommandAction, commandOut: CommandOut){

    init {
        commandRetriever.setCommandListener { commandListener }
    }

    fun startReceiveCommand(){
        commandRetriever.start {
            when(it){
                EVENT.EV1 -> commandListener.invoke(Command.PULL)
                EVENT.EV2 -> commandListener.invoke(Command.PUSH)
                else -> {

                }
            }
        }
    }

    fun stoptReceiveCommand(){
        commandRetriever.stop()
    }

    private val commandListener: (Command)->Unit = {
        val success = when(it){
            Command.PUSH -> commandAction.onPush()
            Command.PULL -> commandAction.onPull()
        }

        if (success) commandOut.commandOK()
        else commandOut.commandKO()
    }
}