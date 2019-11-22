package me.zozfabio.wallet.user.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.CREATE_USER_TYPE_NAME
import java.time.Instant

@JsonTypeName(CREATE_USER_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = CreateUser::class)
data class CreateUser(var name: String,
                      var email: String) : UserCommand {
    override lateinit var occuredAt: Instant
}
