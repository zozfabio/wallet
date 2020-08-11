package me.zozfabio.wallet.user.domain.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.UPDATE_CONTACT_TYPE_NAME
import java.time.Instant

@JsonTypeName(UPDATE_CONTACT_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = UpdateContact::class)
data class UpdateContact(var name: String) : UserCommand {
    lateinit var userId: String
    lateinit var contactId: String
    override lateinit var occuredAt: Instant
}
