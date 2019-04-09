package org.dronix.knative.domain

interface CommandAction {
    fun onPull()
    fun onPush()
}