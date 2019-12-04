package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class UserCreated(override val userId: String,
                       val name: String,
                       val email: String,
                       override val occuredAt: Instant) : UserEvent
