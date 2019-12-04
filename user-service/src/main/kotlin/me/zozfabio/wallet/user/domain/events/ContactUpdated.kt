package me.zozfabio.wallet.user.domain.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.CONTACT_UPDATED_TYPE_NAME
import java.time.Instant

@JsonTypeName(CONTACT_UPDATED_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = ContactUpdated::class)
data class ContactUpdated(override val userId: String,
                          val contactId: String,
                          val name: String,
                          override val occuredAt: Instant) : UserEvent
