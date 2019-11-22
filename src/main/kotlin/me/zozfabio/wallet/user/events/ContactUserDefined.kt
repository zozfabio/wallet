package me.zozfabio.wallet.user.events

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
@TypeAlias("ContactUserDefined")
data class ContactUserDefined(@Id val id: ObjectId,
                              override val userId: ObjectId,
                              val contactId: ObjectId,
                              val contactUserId: ObjectId,
                              override val occuredAt: Instant) : UserEvent
