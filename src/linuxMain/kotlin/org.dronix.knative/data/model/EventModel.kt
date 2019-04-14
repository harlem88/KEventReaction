package org.dronix.knative.data.model

import kotlinx.serialization.Serializable

@Serializable
class EventModel(
    val switch_1: Int,
    val switch_2: Int,
    val switch_3: Int
)