package me.zozfabio.wallet.userview.repository

import me.zozfabio.wallet.userview.entities.UserView
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UserViewRepository : ReactiveCrudRepository<UserView, ObjectId>
