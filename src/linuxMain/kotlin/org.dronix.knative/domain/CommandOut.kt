package org.dronix.knative.domain

interface CommandOut {
    fun commandOK()
    fun commandKO()
    fun reset()
}