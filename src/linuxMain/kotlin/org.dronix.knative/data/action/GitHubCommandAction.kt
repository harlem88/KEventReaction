package org.dronix.knative.data.action

import org.dronix.knative.domain.CommandAction

class GitHubCommandAction(private val origin: String, private val branch: String): CommandAction{
    override fun onPull() {
        platform.posix.system("git pull $origin $branch")
    }

    override fun onPush() {
        platform.posix.system("git push $origin $branch")
    }
}