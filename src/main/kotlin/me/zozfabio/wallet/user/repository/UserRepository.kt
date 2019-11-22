package me.zozfabio.wallet.user.repository

import me.zozfabio.wallet.user.entities.User
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, ObjectId> {

    fun findFirstByEmail(email: String): Mono<User>

    fun findFirstByIsContactsContaining(contactId: ObjectId): Mono<User>
}
