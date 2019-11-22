package me.zozfabio.wallet.user.commands

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import me.zozfabio.wallet.user.*
import java.time.Instant

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = [
    Type(name = CREATE_USER_TYPE_NAME, value = CreateUser::class),
    Type(name = UPDATE_USER_TYPE_NAME, value = UpdateUser::class),

    Type(name = ADD_CONTACT_TYPE_NAME, value = AddContact::class),
    Type(name = UPDATE_CONTACT_TYPE_NAME, value = UpdateContact::class),
    Type(name = REMOVE_CONTACT_TYPE_NAME, value = RemoveContact::class)
])
interface UserCommand {
    var occuredAt: Instant
}
