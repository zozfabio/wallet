package me.zozfabio.wallet.user.domain.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.user.domain.USER_CREATED_TYPE_NAME
import java.time.Instant

@JsonTypeName(USER_CREATED_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = UserCreated::class)
data class UserCreated(override val userId: String,
                       val name: String,
                       val email: String,
                       override val occuredAt: Instant) : UserEvent
