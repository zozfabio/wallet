package me.zozfabio.wallet.user.domain.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.UPDATE_USER_TYPE_NAME
import java.time.Instant

@JsonTypeName(UPDATE_USER_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = UpdateUser::class)
data class UpdateUser(var name: String) : UserCommand {
    lateinit var id: String
    override lateinit var occuredAt: Instant
}
