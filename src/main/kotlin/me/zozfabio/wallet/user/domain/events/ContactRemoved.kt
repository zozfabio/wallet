package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class ContactRemoved(override val userId: String,
                          val contactId: String,
                          override val occuredAt: Instant) : UserEvent
