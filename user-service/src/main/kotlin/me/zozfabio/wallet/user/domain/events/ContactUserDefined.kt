package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class ContactUserDefined(override val userId: String,
                              val contactId: String,
                              val contactUserId: String,
                              override val occuredAt: Instant) : UserEvent
