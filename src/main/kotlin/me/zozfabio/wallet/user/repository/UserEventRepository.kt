package me.zozfabio.wallet.user.repository

import me.zozfabio.wallet.user.events.UserEvent
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UserEventRepository : ReactiveCrudRepository<UserEvent, ObjectId>
