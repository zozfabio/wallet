package me.zozfabio.wallet.user.events

import org.bson.types.ObjectId
import java.time.Instant

interface UserEvent {
    val userId: ObjectId
    val occuredAt: Instant
}
