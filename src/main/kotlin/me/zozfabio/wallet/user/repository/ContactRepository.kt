package me.zozfabio.wallet.user.repository

import me.zozfabio.wallet.user.entities.Contact
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ContactRepository : ReactiveCrudRepository<Contact, ObjectId> {

    fun findAllByEmail(email: String): Flux<Contact>
}
