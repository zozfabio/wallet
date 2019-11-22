package me.zozfabio.wallet.user.events

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
@TypeAlias("UserCreated")
data class UserCreated(override val userId: ObjectId,
                       val name: String,
                       val email: String,
                       override val occuredAt: Instant) : UserEvent {
    @Id
    lateinit var id: ObjectId
}
