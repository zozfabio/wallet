package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class ContactUpdated(override val userId: String,
                          val contactId: String,
                          val name: String,
                          override val occuredAt: Instant) : UserEvent
