package me.zozfabio.wallet.user.domain.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.REMOVE_CONTACT_TYPE_NAME
import java.time.Instant

@JsonTypeName(REMOVE_CONTACT_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = RemoveContact::class)
data class RemoveContact(val userId: String,
                         val contactId: String) : UserCommand {
    override lateinit var occuredAt: Instant
}
