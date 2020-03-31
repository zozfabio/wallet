package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class NullUserEvent(override val userId: String,
                         override val occuredAt: Instant) : UserEvent
