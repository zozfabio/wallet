package me.zozfabio.wallet.user.domain.events

import java.time.Instant

data class ContactAdded(override val userId: String,
                        val contactId: String,
                        val email: String,
                        val name: String,
                        override val occuredAt: Instant,
                        var contactUserId: String? = null) : UserEvent
