package me.zozfabio.wallet.user.domain.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.USER_UPDATED_TYPE_NAME
import java.time.Instant

@JsonTypeName(USER_UPDATED_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = UserUpdated::class)
data class UserUpdated(override val userId: String,
                       val name: String,
                       override val occuredAt: Instant) : UserEvent
