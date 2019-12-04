package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class UserUpdated(override val userId: String,
                       val name: String,
                       override val occuredAt: Instant) : UserEvent
