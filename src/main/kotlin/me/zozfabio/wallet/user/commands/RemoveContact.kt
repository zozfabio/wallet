package me.zozfabio.wallet.user.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.REMOVE_CONTACT_TYPE_NAME
import org.bson.types.ObjectId
import java.time.Instant

@JsonTypeName(REMOVE_CONTACT_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = RemoveContact::class)
data class RemoveContact(val userId: ObjectId,
                         val contactId: ObjectId) : UserCommand {
    override lateinit var occuredAt: Instant
}
