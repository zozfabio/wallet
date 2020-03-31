package me.zozfabio.wallet

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.SubscribableChannel

const val USER_EVENTS = "user-events"

interface Channels : Source {

    @Input(USER_EVENTS)
    fun userEvents(): SubscribableChannel
}
