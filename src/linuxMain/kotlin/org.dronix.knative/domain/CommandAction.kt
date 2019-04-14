package org.dronix.knative.domain

interface CommandAction {
    fun onPull(): Boolean
    fun onPush(): Boolean
}