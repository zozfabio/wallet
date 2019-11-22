package me.zozfabio.wallet.user.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.ADD_CONTACT_TYPE_NAME
import org.bson.types.ObjectId
import java.time.Instant

@JsonTypeName(ADD_CONTACT_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = AddContact::class)
data class AddContact(var name: String,
                      var email: String) : UserCommand {
    lateinit var userId: ObjectId
    override lateinit var occuredAt: Instant
}
