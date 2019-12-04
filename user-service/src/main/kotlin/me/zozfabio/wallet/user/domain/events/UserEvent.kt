package me.zozfabio.wallet.user.domain.events

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import me.zozfabio.wallet.user.domain.*
import java.time.Instant

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = [
    JsonSubTypes.Type(name = USER_CREATED_TYPE_NAME, value = UserCreated::class),
    JsonSubTypes.Type(name = USER_UPDATED_TYPE_NAME, value = UserUpdated::class),

    JsonSubTypes.Type(name = CONTACT_ADDED_TYPE_NAME,        value = ContactAdded::class),
    JsonSubTypes.Type(name = CONTACT_UPDATED_TYPE_NAME,      value = ContactUpdated::class),
    JsonSubTypes.Type(name = CONTACT_USER_DEFINED_TYPE_NAME, value = ContactUserDefined::class),
    JsonSubTypes.Type(name = CONTACT_REMOVED_TYPE_NAME,      value = ContactRemoved::class)
])
interface UserEvent {
    val userId: String
    val occuredAt: Instant
}
