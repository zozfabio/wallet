package me.zozfabio.wallet.user.domain.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.CONTACT_ADDED_TYPE_NAME
import java.time.Instant

@JsonTypeName(CONTACT_ADDED_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = ContactAdded::class)
data class ContactAdded(override val userId: String,
                        val contactId: String,
                        val email: String,
                        val name: String,
                        override val occuredAt: Instant,
                        var contactUserId: String? = null) : UserEvent
