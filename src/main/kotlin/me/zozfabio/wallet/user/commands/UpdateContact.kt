package me.zozfabio.wallet.user.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.UPDATE_CONTACT_TYPE_NAME
import org.bson.types.ObjectId
import java.time.Instant

@JsonTypeName(UPDATE_CONTACT_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = UpdateContact::class)
data class UpdateContact(var name: String) : UserCommand {
    lateinit var userId: ObjectId
    lateinit var contactId: ObjectId
    override lateinit var occuredAt: Instant
}
