package me.zozfabio.wallet.user.domain.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.CONTACT_REMOVED_TYPE_NAME
import java.time.Instant

@JsonTypeName(CONTACT_REMOVED_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = ContactRemoved::class)
data class ContactRemoved(override val userId: String,
                          val contactId: String,
                          override val occuredAt: Instant) : UserEvent
