package org.dronix.knative.data.action

import org.dronix.knative.domain.CommandAction

class GitCommandAction(private val origin: String, private val branch: String): CommandAction{
    override fun onPull(): Boolean{
        println("platform.posix.system(\"git pull $origin $branch\")")
        return true
    }

    override fun onPush(): Boolean{
        println("platform.posix.system(\"git push $origin $branch\")")
        return true
    }
}